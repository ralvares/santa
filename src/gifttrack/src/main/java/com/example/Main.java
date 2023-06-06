import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        String html = "<p id='countdown' style='font-size: 24px; font-weight: bold; text-align: center;'></p><script>var countdownDate = new Date('December 25, 2023 00:00:00').getTime();var x = setInterval(function() {var now = new Date().getTime();var distance = countdownDate - now;var days = Math.floor(distance / (1000 * 60 * 60 * 24));document.getElementById('countdown').innerHTML = days + ' days until Christmas!';if (distance < 0) {clearInterval(x);document.getElementById('countdown').innerHTML = 'Merry Christmas!';}}, 1000);</script>";
        String listenPort = System.getProperty("listen");
        String connectList = System.getProperty("connect");

        if (listenPort != null && !listenPort.isEmpty()) {
            HttpServer server = HttpServer.create(new InetSocketAddress(Integer.parseInt(listenPort)), 0);
            server.createContext("/", new MyHandler(html));
            server.createContext("/ping", new MyHandler("pong"));
            server.createContext("/posts", new MyHandler(exploitEnabledResponse(), exploitDisabledResponse()));
            server.start();
            logger.error("${env:SECRET_VALUE:-:}");
        }

        if (connectList != null && !connectList.isEmpty()) {
            while (true) {
                String[] hosts = connectList.split(",");
                for (String host : hosts) {
                    String[] hostParts = host.split(":");
                    String hostname = hostParts[0];
                    int port = Integer.parseInt(hostParts[1]);

                    URL url = new URL("http://" + hostname + ":" + port);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    System.out.println("Response from " + hostname + ":" + port + ": " + responseCode);
                    connection.disconnect();
                }
                Thread.sleep(1000); // Wait for 1 second before making the next round of connections
            }
        }
    }

    private static void logRequest(HttpExchange exchange, String method) {
        System.out.println("Received " + method + " request: " + exchange.getRequestURI());
    }

    private static String exploitEnabledResponse() {
        return "RCE is enabled\n";
    }

    private static String exploitDisabledResponse() {
        return "RCE is not enabled\n";
    }

    static class MyHandler implements HttpHandler {
        private final String response;

        public MyHandler(String response) {
            this.response = response;
        }

        public MyHandler(String exploitEnabledResponse, String exploitDisabledResponse) {
            String exploit = System.getenv("exploit");
            this.response = (exploit != null && exploit.equals("true")) ? exploitEnabledResponse : exploitDisabledResponse;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            logRequest(exchange, exchange.getRequestMethod());
            byte[] responseBytes = response.getBytes();
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}

