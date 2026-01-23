package kr.astar.wfliv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kr.astar.wfliv.data.alert.Donation;
import kr.astar.wfliv.data.alert.DonationData;
import kr.astar.wfliv.data.alert.PlatformData;
import kr.astar.wfliv.data.alert.User;
import kr.astar.wfliv.data.streamer.StreamerData;
import kr.astar.wfliv.enums.DonationType;
import kr.astar.wfliv.enums.MessageID;
import kr.astar.wfliv.listener.WeflabListener;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.*;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Weflab extends WebSocketListener {
    private final String key;
    private final List<WeflabListener> listeners;
    private final boolean enableDebug;

    @Getter
    private String idx;

    private OkHttpClient client;
    private WebSocket socket;

    private boolean closed=true;
    private String pageid;

    @Getter
    private StreamerData streamerData;

    public Weflab(WeflabBuilder weflabBuilder) {
        this.key = weflabBuilder.getKey();
        this.listeners= weflabBuilder.getListeners();
        this.enableDebug = weflabBuilder.isEnableDebug();

        if (this.key==null || this.key.isEmpty()) {
            throw new NullPointerException("Page Key is EMPTY");
        }

        try {
            Document document= Jsoup
                    .connect("https://weflab.com/page/"+ key)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:146.0) Gecko/20100101 Firefox/146.0")
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .cookies(Map.of())
                    .get();

            Elements element = document.getElementsByTag("script");
            String script = element.stream()
                    .filter(e->
                            !e.hasAttr("src")
                            && e.data().contains("loginData = {")
                    )
                    .map(Element::toString)
                    .collect(Collectors.joining());

            JsonObject loginDataJson= parseLoginData(script);
            if (loginDataJson==null) return;

            this.pageid= loginDataJson.get("pageid").getAsString();

//            System.out.println(loginDataJson.toString());

            String idx1 = loginDataJson.get("idx").getAsString();
            String streamerName= loginDataJson.get("user").getAsJsonObject().get("name").getAsString();
            String streamerEmail= loginDataJson.get("user").getAsJsonObject().get("email").getAsString();

            if (idx1==null) throw new Exception("Idx not found");
            this.idx = idx1;

            this.streamerData= new StreamerData(
                    loginDataJson.get("login").getAsString(),
                    idx1,
                    loginDataJson.get("userid").getAsString(),
                    streamerName, streamerEmail,
                    loginDataJson.get("mobile").getAsBoolean(),
                    loginDataJson.get("ios").getAsBoolean()
            );

            this.client=new OkHttpClient().newBuilder()
                    .pingInterval(12, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .build();

            connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JsonObject parseLoginData(String script) {
        Pattern p = Pattern.compile("loginData\\s*=\\s*(\\{.*?})\\s*;", Pattern.DOTALL);
        Matcher m = p.matcher(script);

        if (!m.find())
            throw new IllegalStateException("loginData not found");

        String json = m.group(1);
//        System.out.println(json);
        return new Gson().fromJson(json, JsonObject.class);
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        for (WeflabListener listener: listeners)
            listener.onConnect(response);
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        if (enableDebug) System.out.println("\u001B[33m"+text + "\u001B[0m");
        if (text.equals(MessageID.PING.getNumber())) { // Websocket Client Ping for checking Connected
            webSocket.send("3");
            return;
        }

        if (text.startsWith(MessageID.REGISTER.getNumber())) { // Websocket Register
            // text: 40{"sid":"RKvSWkbKA8timc0JCl4G"}
            webSocket.send("42[\"msg\",{\"type\":\"join\",\"page\":\"page\",\"idx\":\""
                    + this.idx + "\",\"pageid\":\""
                    + this.pageid + "\",\"preset\":\"0\"}]");
            return;
        }

        if (text.startsWith(MessageID.MESSAGE.getNumber())) {
            // text
            String rawJson = text.startsWith(MessageID.MESSAGE.getNumber())
                    ? text.substring(2)
                    : text;
            try {
                JsonArray json = JsonParser.parseString(rawJson).getAsJsonArray();
                String receive = json.get(0).getAsString();
                JsonObject receiveData = json.get(1).getAsJsonObject();

                if (Objects.equals(receive, "pong")) {
                    String platform= receiveData.get("platform").getAsString();
                    return;
                }

                if (Objects.equals(receive, "msg")) {
                    String type= receiveData.get("type").getAsString(); // test_donation

                    if (type.equals(DonationType.LOAD.getType())) {
                        // Donation alert page reloaded caused by Weflab Setting Changed
                        reconnect();
                        return;
                    }

                    JsonObject data= receiveData.get("data").getAsJsonObject();
                    boolean isTestDonation= Objects.equals(type, "test_donation");

                    String page= receiveData.get("page").getAsString();
                    String idx= receiveData.get("idx").getAsString();
                    String pageid= receiveData.get("pageid").getAsString();
                    String preset= receiveData.get("preset").getAsString();

                    DonationData donationData= new DonationData(
                            page, idx,
                            pageid, preset
                    );

                    Donation donation= new Donation(
                            new User(
                                    data.get("uname").getAsString(),
                                    data.get("uid").getAsString()
                            ),
                            data.get("msg").getAsString(),
                            data.get("value").getAsLong(),
                            isTestDonation,
                            data.get("time").getAsLong(),
                            donationData,
                            new PlatformData(
                                    data.get("platform").getAsString(),
                                    data.get("type").getAsString()
                            )
                    );

                    for (WeflabListener listener: listeners)
                        listener.onDonation(donation);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public synchronized void XHRRequestListener() {
//        Playwright pw = Playwright.create();
//        Browser browser = pw.chromium().launch();
//        BrowserContext ctx = browser.newContext();
//        Page page = ctx.newPage();
//
//        page.onRequest(req -> {
//            if (req.resourceType().equals("image") || Objects.equals(req.resourceType(), "font")) return;
//            System.out.println("ResourceType: " + req.resourceType());
//            System.out.println("URL: " + req.url());
//            System.out.println("METHOD: " + req.method());
//            System.out.println("POST: " + req.postData());
//        });
//
//        page.navigate(alertPageURL);
//
//    }

    private void reconnect() {
        close();
        connect();
    }

    private void connect() {
        Request request=new Request.Builder()
                .url("wss://ssmain.weflab.com/socket.io/?idx="
                        + idx + "&type=page&page="
                        + this.pageid + "&EIO=4&transport=websocket"
                )
                .build();
        this.socket = client.newWebSocket(request, this);
        this.socket.send("40");
//        XHRRequestListener();
    }

    public boolean close() {
        try {
            if (socket != null) {
                boolean suc= socket.close(1000, "Client closing");
                socket = null;
                return suc;
            }
            closed=true;
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
        for (WeflabListener listener: listeners)
            listener.onDisconnect(code, reason);
    }

    @Override
    public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
        if (closed) return;
        webSocket.close(1000, "Error occurred");
        for (WeflabListener listener: listeners)
            listener.onFail(t, response);
    }
}
