package pl.polsl.michal.smaluch.cipher.caesar.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet responsible for filling up the history table.
 * @author Michal Smaluch
 */
@WebServlet(name = "History", urlPatterns = {"/History"})
public class HistoryServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        try ( PrintWriter out = response.getWriter()) {
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
            if (session != null) {
                Integer count = (Integer) session.getAttribute("count");
                if (count != null) {
                    for (int i = 1; i <= count; i++) {
                        String entries = (String) session.getAttribute(i + "entry");
                        if (entries != null) {
                            String[] entry = entries.split("-");
                            if (entry.length != 0) {
                                out.println("<tr>");
                                out.println("<td>" + entry[0] + "</td>");
                                out.println("<td>" + entry[1] + "</td>");
                                out.println("<td>" + entry[2] + "</td>");
                                out.println("<td>" + entry[3] + "</td>");
                                out.println("</tr>");
                            }
                        }
                    }
                }
            }
            out.print("""     
                          </table>
                            <button onclick="location.href='/CeasarCipherWeb/'">Go Back</button>
                            </body>
                    </html> 
                      """);
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
