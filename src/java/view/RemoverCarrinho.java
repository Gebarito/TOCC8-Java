/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package view;

import controller.DAOJPA;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import model.Produto;

/**
 *
 * @author joaop
 */
@WebServlet(name = "RemoverCarrinho", urlPatterns = {"/RemoverCarrinho"})
public class RemoverCarrinho extends HttpServlet {

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
        List<Produto> carrinho;
        DAOJPA dao;
        Produto produto;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try{
                produto = new Produto();
                produto.setCodigo(Integer.valueOf(request.getParameter("txtCodigo")));
                produto.setDescricao(request.getParameter("txtDescricao"));
                produto.setPreco(Double.valueOf(request.getParameter("txtPreco")));
                produto.setQtde(Integer.valueOf(request.getParameter("txtQtde")));

                dao = new DAOJPA();

                HttpSession session = request.getSession();
                carrinho = (List<Produto>) session.getAttribute("carrinho");

                for (Produto p : carrinho) {
                    if (Objects.equals(p.getCodigo(), produto.getCodigo())){
                        produto.setQtde(produto.getQtde() + p.getQtde());
                        dao.alterar(produto);
                        carrinho.remove(p);
                    }
                }

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet RemoverCarrinho</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet RemoverCarrinho at " + request.getContextPath() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            } catch(Exception ex) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet Remover do Carrinho</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Erro ao Remover do carrinho: " + ex.getMessage() + "</h1>");
                out.println("</body>");
                out.println("</html>");
            }
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
