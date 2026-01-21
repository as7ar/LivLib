package kr.astar.wfliv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kr.astar.wfliv.listener.WeflabListener;
import lombok.Getter;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Weflab extends WebSocketListener {
    private String key;
    private final List<WeflabListener> listeners;

    private String idx;
    private WebSocket socket;

    @Getter
    private String StreamerName;
    @Getter
    private String StreamerEmail;

    public Weflab(WeflabBuilder weflabBuilder) {
        this.key = weflabBuilder.getKey();
        this.listeners= weflabBuilder.getListeners();

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

            System.out.println(loginDataJson.toString()); // todo: TEST

            String idx1 = loginDataJson.get("idx").getAsString();
            String streamerName= loginDataJson.get("user").getAsJsonObject().get("name").getAsString();
            String streamerEmail= loginDataJson.get("user").getAsJsonObject().get("email").getAsString();

            if (idx1==null) throw new Exception("Idx not found");

            this.StreamerName = streamerName;
            this.StreamerEmail = streamerEmail;
            this.idx = idx1;

            OkHttpClient client=new OkHttpClient().newBuilder()
                    .pingInterval(12, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .build();
            Request request=new Request.Builder()
                    .url(
                            "wss://ssmain.weflab.com/socket.io/?idx=" + idx
                                    + "&type=page&page="
                                    + "chat"
                                    + "&EIO=4&transport=websocket"
                    )
                    .build();
            socket =client.newWebSocket(request, this);
            socket.send("40");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JsonObject parseLoginData(String script) {
        String jsonPart = script.split(" = ")[1];
        return new Gson().fromJson(jsonPart, JsonObject.class);
    }

    @Override
    public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
        for (WeflabListener listener: listeners)
            listener.onConnect();
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        if (text.equals("2")) { // Websocket Client Ping for checking Connected
            webSocket.send("3");
            return;
        }

        if (text.startsWith("40")) { // Websocket Register
            // text: 40{"sid":"RKvSWkbKA8timc0JCl4G"}
            webSocket.send("42[\"msg\",{\"type\":\"join\",\"page\":\"page\",\"idx\":\""
                    + this.idx + "\",\"pageid\":\"chat\",\"preset\":\"0\"}]");
            return;
        }

        if (text.startsWith("42")) {
            // text
            // 42["msg",{"type":"test_donation","data":{"platform":"youtube","time":1768971964473,"type":"superchat","msg":"TEST MESSAGE","uid":"TEST","uname":"TEST","value":"1000"},"page":"setup","idx":"mNzL0s3DwWlxZXJZ1pWenJqD","pageid":"chat","preset":"0"}]
            try {
                JsonArray json = new Gson().fromJson(text.substring(1), JsonArray.class);

                //todo: YEAH
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
