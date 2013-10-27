package mpi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class EmbeddedServer {

    private Server server;

    public EmbeddedServer(Integer port) {

        try{
            Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
            
            properties.put("jdbc/TechnologiesDS", "new://Resource?type=DataSource");
            properties.put("jdbc/TechnologiesDS.JdbcDriver", "org.h2.Driver");
            properties.put("jdbc/TechnologiesDS.JdbcUrl", "jdbc:h2:mem:TechnologiesDB");
            properties.put("jdbc/TechnologiesDS.UserName", "sa");
            properties.put("jdbc/TechnologiesDS.Password", "");
            properties.put("jdbc/TechnologiesDS.JtaManaged", "true");
            
            InitialContext ctx = new InitialContext(properties);
                        
            NamingEnumeration<NameClassPair> ejbs = ctx.list("");
            while(ejbs.hasMore()){
                NameClassPair pair = ejbs.next();
                System.err.println(pair.getName());
            }
            
            TechnologiesBean technologies = (TechnologiesBean) ctx.lookup("TechnologiesEJBLocal");
            technologies.save(new Technology("Jetty"));
            technologies.save(new Technology("EJB"));
            technologies.save(new Technology("JPA"));
            technologies.save(new Technology("H2"));
            
            server = new Server(port);
            WebAppContext context = new WebAppContext();
            context.setResourceBase("./src/main/webapp");
            context.setDescriptor("./WEB-INF/web.xml");
            server.setHandler(context);

        } catch(NamingException e){
            throw new IllegalStateException("Could not start establish EJB context", e);
        }
    }

    public void start() {
        
        System.err.println("-------------- Starting Embedded Server for Integration Tests...");
        
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void awaitShutdown() {
        try {
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {

        System.err.println("-------------- Stopping Embedded Server for Integration Tests...");
        
        try {
            server.stop();
            awaitShutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        
        EmbeddedServer server = new EmbeddedServer(Integer.valueOf(envOr("PORT", "8080")));
        
        server.start();
        server.awaitShutdown();
    }
    
    private static String envOr(String port, String defaultValue) {
        String variable = System.getenv(port);
        if (variable == null) {
            return defaultValue;
        }
        return variable;
    }    
}
