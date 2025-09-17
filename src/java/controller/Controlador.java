/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author prampero
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {
    private static final long serialVersionUID = 1L;
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
        request.setCharacterEncoding("UTF-8");
        String botao;
        PrintWriter out = response.getWriter();

        try {
            botao = request.getParameter("b1").trim().toLowerCase();
            switch (botao) {
                case "gravar":
                    // encaminha a requisição para o servletScadastar
                    request.getRequestDispatcher("Gravar").forward(request, response);
                    break;
                case "listar":

                    // encaminha a requisição para o servletScadastar
                    request.getRequestDispatcher("Listar").forward(request, response);
                    break;
                case "alterar":

                    // encaminha a requisição para o servletScadastar
                    request.getRequestDispatcher("Alterar").forward(request, response);
                    break;
                case "remover":

                    // encaminha a requisição para o servletScadastar
                    request.getRequestDispatcher("Remover").forward(request, response);
                    break;
                case "listarcarrinho":
                    request.getRequestDispatcher("ListarCarrinho").forward(request, response);
                    break;
                case "adicionaraocarrinho":
                    request.getRequestDispatcher("AdicionarAoCarrinho").forward(request, response);
                    break;
                case "removercarrinho":
                    request.getRequestDispatcher("RemoverCarrinho").forward(request, response);
                    break;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Evento: " + botao + " não tratado!</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title> Erro no Controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Erro: " + ex.getMessage() + "</h1>");
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
