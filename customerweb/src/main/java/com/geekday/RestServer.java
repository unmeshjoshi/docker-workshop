package com.geekday;

import com.geekday.accounting.web.CustomerResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Map;


public class RestServer {

    public static void main(String[] args) throws Exception {
        startWebServer();
    }

    private static void startWebServer() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8085);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                CustomerResource.class.getCanonicalName());

        try {
            Map<String, String> env = System.getenv();
            for (String key : env.keySet()) {
                System.out.println(key + " = " + env.get(key));
            }
            System.out.println("jerseyServlet = " + jerseyServlet);
            jettyServer.start();
            jettyServer.join();
        } catch(Throwable t) {
            t.printStackTrace();
        }finally {
            jettyServer.destroy();
        }
    }
}
