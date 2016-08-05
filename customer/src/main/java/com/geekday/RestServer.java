package com.geekday;

import com.geekday.accounting.customer.domain.CustomerRepository;
import com.geekday.accounting.customer.web.CustomerResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


public class RestServer {

    public static void main(String[] args) throws Exception {
        initializeApplication();
        startWebServer();
    }

    private static void initializeApplication() {
        CustomerRepository.initialize();
    }

    private static void startWebServer() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8082);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                CustomerResource.class.getCanonicalName());

        try {
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
