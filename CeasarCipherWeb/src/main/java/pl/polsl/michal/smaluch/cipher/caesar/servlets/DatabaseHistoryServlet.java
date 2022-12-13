package pl.polsl.michal.smaluch.cipher.caesar.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.michal.smaluch.cipher.caesar.model.HistoryEntity;

/**
 *
 * @author Michal Smaluch
 * @version 1.0
 */
@WebServlet(name = "DatabaseHistory", urlPatterns = {"/DatabaseHistory"})
public class DatabaseHistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");

        Cookie[] cookies = request.getCookies();
        Integer errorCount = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("errorCount")) {
                    errorCount = Integer.valueOf(cookie.getValue());
                    break;
                }
            }
        }

        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DatabaseHistoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Total errors: " + errorCount + "</h3>");
            out.print("""
                             <style>
                             table {
                               font-family: arial, sans-serif;
                               border-collapse: collapse;
                               width: 100%;
                             }
                     
                             td, th {
                               border: 1px solid #dddddd;
                               text-align: left;
                               padding: 8px;
                             }
                     
                             tr:nth-child(even) {
                               background-color: #dddddd;
                             }
                             </style>
                             <table>
                               <tr>
                                 <th>Operation</th>
                                 <th>Message</th>
                                 <th>Key</th>
                                 <th>Output</th>
                               </tr>
                       """);
            //------------
            //(DONE) TODO: GET DATA FROM DB
            if (emf != null && emf.isOpen()) {
                EntityManager em = emf.createEntityManager();
                em.getTransaction().begin();
                try {
                    Query query = em.createQuery("SELECT h FROM HistoryEntity h");
                    List<HistoryEntity> historyEntries = query.getResultList();
                    for (HistoryEntity entry : historyEntries) {
                        System.out.println("Found object: " + entry.getId());

                        out.println("<tr>");
                        out.println("<td>" + entry.getOperationType() + "</td>");
                        out.println("<td>" + entry.getMessage() + "</td>");
                        out.println("<td>" + entry.getShiftKey() + "</td>");
                        out.println("<td>" + entry.getProcessedMessage() + "</td>");
                        out.println("</tr>");

                    }
                } catch (PersistenceException | IllegalArgumentException e) {
                    System.out.println("Rollback");
                    em.getTransaction().rollback();
                } finally {
                    em.close();
                }
            }
            out.print(
                    """     
                          </table>
                            <button onclick="location.href='/CeasarCipherWeb/'">Go Back</button>
                            </body>
                    </html> 
                      """);
            out.println(
                    "</body>");
            out.println(
                    "</html>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
