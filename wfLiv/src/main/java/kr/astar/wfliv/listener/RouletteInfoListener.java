package kr.astar.wfliv.listener;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefRequestHandlerAdapter;
import org.cef.handler.CefResourceRequestHandler;
import org.cef.handler.CefResourceRequestHandlerAdapter;
import org.cef.misc.BoolRef;
import org.cef.network.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RouletteInfoListener extends CefRequestHandlerAdapter {
    @Override
    public CefResourceRequestHandler getResourceRequestHandler(
            CefBrowser browser,
            CefFrame frame,
            CefRequest request,
            boolean isNavigation,
            boolean isDownload,
            String requestInitiator,
            BoolRef disableDefaultHandling
    ) {

        return new CefResourceRequestHandlerAdapter() {

            @Override
            public boolean onBeforeResourceLoad(CefBrowser browser, CefFrame frame, CefRequest request) {
                System.out.println(request.getMethod() + " " + request.getURL());

                if (!request.getURL().contains("weflab.com")) return false;

                String method = request.getMethod();
                String url = request.getURL();

                System.out.println("REQUEST " + method + " " + url);

                Map<String, String> params = new HashMap<>();

                // GET 파라미터
                int qIndex = url.indexOf('?');
                if (qIndex != -1) {
                    parseParams(url.substring(qIndex + 1), params);
                }

                // POST 파라미터 (XHR / form)
                CefPostData postData = request.getPostData();
                if (postData != null) {

                    Vector<CefPostDataElement> elements = new Vector<>();
                    postData.getElements(elements);

                    for (CefPostDataElement element : elements) {

                        if (element.getType() != CefPostDataElement.Type.PDE_TYPE_BYTES) continue;

                        byte[] bytes = new byte[element.getBytesCount()];
                        element.getBytes(bytes.length, bytes);

                        String body = new String(bytes, StandardCharsets.UTF_8);

                        System.out.println("BODY: " + body);

                        if (body.startsWith("{")) {
                            System.out.println("JSON BODY: " + body);
                        } else {
                            parseParams(body, params);
                        }
                    }
                }

                if ("roulette_result".equals(params.get("type"))) {

                    String encoded = params.get("roulette");
                    if (encoded != null) {

                        String json = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
                        System.out.println("룰렛 확률 = " + json);
                    }
                }

                return false;
            }

            private void parseParams(String raw, Map<String, String> map) {

                for (String param : raw.split("&")) {

                    int idx = param.indexOf('=');
                    if (idx == -1) continue;

                    String key = param.substring(0, idx);
                    String value = param.substring(idx + 1);

                    key = URLDecoder.decode(key, StandardCharsets.UTF_8);
                    value = URLDecoder.decode(value, StandardCharsets.UTF_8);

                    map.put(key, value);
                }
            }


        };
    }


}

