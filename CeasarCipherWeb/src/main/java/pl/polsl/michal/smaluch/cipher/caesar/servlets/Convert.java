/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.michal.smaluch.cipher.caesar.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.michal.smaluch.cipher.caesar.model.CipherAppModel;
import pl.polsl.michal.smaluch.cipher.caesar.model.InvalidMessageException;

/**
 *
 * @author drago
 */
@WebServlet(name = "Convert", urlPatterns = {"/Convert"})
public class Convert extends HttpServlet {

    private CipherAppModel cipherAppModel;

    public Convert() {
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Convert</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("message: " + cipherAppModel.getMessage() + "<br>");
            out.println("encryption key :" + cipherAppModel.getEncryptKey() + "<br>");
            out.println("Operation: " + (cipherAppModel.getEncryptFlag() ? "Encryption" : "Decryption") + "<br>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            cipherAppModel.setMessage(request.getParameter("message"));

        } catch (InvalidMessageException e) {
        }
        
        try{
            cipherAppModel.setKey(request.getParameter("key"));
        }catch (NumberFormatException e){}
        
        cipherAppModel.setEncryptFlag();
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
