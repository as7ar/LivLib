//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.LoadState;
//
//import java.util.concurrent.CountDownLatch;
//
//public class NetworkAllInOne {
//    public static void main(String[] args) {
//        try (Playwright playwright = Playwright.create()) {
//
//            Browser browser = playwright.chromium().launch(
//                    new BrowserType.LaunchOptions()
//                            .setHeadless(true)
//            );
//
//            BrowserContext context = browser.newContext();
//            Page page = context.newPage();
//            CountDownLatch latch = new CountDownLatch(1);
//
//            /* =========================
//               XHR / FETCH 요청 감지
//               ========================= */
//            page.onRequest(req -> {
//                String type = req.resourceType();
//                if (type.equals("xhr") || type.equals("fetch")) {
//                    System.out.println("[XHR REQ] "
//                            + req.method() + " "
//                            + req.url());
//                }
//            });
//
//            /* =========================
//               XHR / FETCH 응답 감지
//               ========================= */
//            page.onResponse(res -> {
//                Request req = res.request();
//                String type = req.resourceType();
//                if (type.equals("xhr") || type.equals("fetch")) {
//                    try {
//                        System.out.println("[XHR RES] "
//                                + res.status() + " "
//                                + req.url());
//                        System.out.println(res.text());
//                    } catch (Exception ignored) {}
//                }
//            });
//
//            /* =========================
//               WebSocket 감지
//               ========================= */
//            page.onWebSocket(ws -> {
//                System.out.println("[WS OPEN] " + ws.url());
//
//                ws.onFrameSent(frame -> {
//                    System.out.println("[WS SEND] " + frame.text());
//                });
//
//                ws.onFrameReceived(frame -> {
//                    System.out.println("[WS RECV] " + frame.text());
//                });
//
//                ws.onClose(frame -> {
//                    System.out.println("[WS CLOSE]" + frame.isClosed());
//                    latch.countDown();
//                });
//            });
//
//            /* =========================
//               페이지 접속
//               ========================= */
//            page.navigate("https://weflab.com/page/guPK2ODAm2ltaXE");
//            page.waitForLoadState(LoadState.NETWORKIDLE);
//
//            latch.await();
////            browser.close();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
