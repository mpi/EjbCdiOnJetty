package mpi;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet{

    private static final long serialVersionUID = -1854471508806183614L;
    
    private TechnologiesBean technologiesEJB;

    @Override
    public void init() throws ServletException {
        
        super.init();

        try {
            
            InitialContext ctx = new InitialContext();
            technologiesEJB = (TechnologiesBean) ctx.lookup("java:openejb/ejb/TechnologiesEJBLocal");

        } catch (NamingException e) {
            throw new ServletException("Could not locate TechnologiesEJB!", e); 
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        PrintWriter writer = resp.getWriter();
        writer.println("<html><body>");
        writer.println("<ul>");
        
        for (Technology tech : technologiesEJB.list()) {
            writer.println("<li>" + tech.name() + "</li>");
        }
        
        writer.println("</ul>");
        writer.println("</body></html>");
        
    }
    
}
