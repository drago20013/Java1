package pl.polsl.michal.smaluch.cipher.caesar.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.HistoryEntity;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;

/**
 * Main Servlet used as a Controller part of MVC pattern. Communicates with
 * model, passes all parameters. Includes History Servlet for displaying
 * history. Uses cookies for keeping track of errors and sessions for history of
 * operations.
 *
 * @author Michal Smaluch
 * @version 1.0
 */
@WebServlet(name = "Convert", urlPatterns = {"/Convert"})
public class ConvertServlet extends HttpServlet {

    private CipherAppModel cipherAppModel;

    /**
     * Default constructor of main servlet. Creates model object.
     */
    public ConvertServlet() {
        cipherAppModel = new CipherAppModel();
    }

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

        try ( PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();

            String message = request.getParameter("message");
            String key = request.getParameter("key");
            String operation = request.getParameter("option");

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

            Integer count = (Integer) session.getAttribute("count");
            if (count == null) // Initialize the counter
            {
                count = 1;
            } else // Increment the counter
            {
                count = count + 1;
            }

            session.setAttribute("count", count);

            try {
                try {
                    cipherAppModel.setMessage(message);

                    cipherAppModel.setKey(key);
                    try {
                        if (operation.equals("encrypt")) {
                            cipherAppModel.setEncryptFlag();
                        } else {
                            cipherAppModel.setDecryptFlag();
                        }
                    } catch (NullPointerException e) {
                        cipherAppModel.setEncryptFlag();
                    }

                    cipherAppModel.shiftMessage();

                    //At this point we have working entry, save it to local history (session), and database
                    EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");

                    String entryMsg = cipherAppModel.getMessage();
                    String entryProcMsg = cipherAppModel.getProcessedMessage();
                    String operationType = (cipherAppModel.getEncryptFlag() ? "Encryption " : "Decryption ");

                    if (emf != null && emf.isOpen()) {
                        EntityManager em = emf.createEntityManager();
                        HistoryEntity dbEntity = new HistoryEntity();
                        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("Europe/Paris"));
                        dbEntity.setDate(todayLocalDate);
                        dbEntity.setMessage(entryMsg);
                        dbEntity.setProcessedMessage(entryProcMsg);
                        dbEntity.setShiftKey(Integer.parseInt(key));
                        dbEntity.setOperationType(operationType);
                        em.getTransaction().begin();
                        try {
                            em.persist(dbEntity);
                            em.getTransaction().commit();
                            System.out.println("Object persisted");
                        } catch (PersistenceException e) {
                            System.out.println("Rollback");
                            em.getTransaction().rollback();
                        } finally {
                            em.close();
                        }
                    }

                    String entry = operationType + "-" + entryMsg + "-" + key + "-" + entryProcMsg;

                    session.setAttribute(count.toString() + "entry", entry);

                } catch (NumberFormatException e) {
                    errorCount++;
                    Cookie cookie = new Cookie("errorCount", errorCount.toString());
                    response.addCookie(cookie);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Key has to be positive integer value.");
                }
            } catch (InvalidMessageException e) {
                errorCount++;
                Cookie cookie = new Cookie("errorCount", errorCount.toString());
                response.addCookie(cookie);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Message can cantain only English alphabet characters and cannot be null!");
            }

            out.print("""
                    <!DOCTYPE html>
                        <html>
                              <head>
                                <title>Convert</title>
                              </head>
                              <body>
                      """);
            out.println("<h3>Total errors: " + errorCount + "</h3>");

            getServletContext().getRequestDispatcher("/History").include(request, response);
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
