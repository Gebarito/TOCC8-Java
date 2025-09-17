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
import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author joaop
 */
@WebServlet(name = "AdicionarAoCarrinho", urlPatterns = {"/AdicionarAoCarrinho"})
public class AdicionarAoCarrinho extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        DAOJPA dao;
        Produto produto;
        int qtde = 0;
        PrintWriter out = response.getWriter();
        try {
            produto = new Produto();
            produto.setCodigo(Integer.valueOf(request.getParameter("txtCodigo")));
            produto.setDescricao(request.getParameter("txtDescricao"));
            produto.setPreco(Double.valueOf(request.getParameter("txtPreco")));
            produto.setQtde(Integer.valueOf(request.getParameter("txtQtde")));
            
            HttpSession session = request.getSession();
            List<Produto> carrinho = (List<Produto>) session.getAttribute("carrinho");
            
            if (carrinho == null) {
                carrinho = new ArrayList<>();
                session.setAttribute("carrinho", carrinho);
            }
            
            if (produto.getQtde() <= 0) {
                out.println("<h1> Item não disponivel no estoque </h1>");
                return;
            }
            
            dao = new DAOJPA();
            dao.adicionarAoCarrinho(produto, carrinho);

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Adicionar ao Carrinho</title>");
            out.println("</head>");
            out.println("<body>");
            if (produto.getCodigo() > 0) {
                out.println("<h1> "+ produto.getDescricao() + " Salvo com sucesso no carrinho. </h1>");
                // Redireciona para tela inicial
                out.println("<script>");
                out.println("setTimeout(function() {");
                out.println("  window.location.href = 'index.html';");
                out.println("}, 500);");
                out.println("</script>");
            } else {
                out.println("<h1> Nada foi salvo no carrinho. </h1>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Adicionar ao carrinho</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Erro ao Adicionar ao carrinho: " + ex.getMessage() + "</h1>");
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
