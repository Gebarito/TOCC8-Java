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
import model.Produto;

/**
 *
 * @author joaop
 */
@WebServlet(name = "ListarCarrinho", urlPatterns = {"/ListarCarrinho"})
public class ListarCarrinho extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        List<Produto> lista;
        PrintWriter out = response.getWriter();
        
        try {
            
            HttpSession session = request.getSession();
            lista = (List<Produto>) session.getAttribute("carrinho");
             /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Carrinho</title>");
            out.println("</head>");
            out.println("<body>");
            if (lista != null) {
                out.println("<h1>Carrinho: </h1>");
                for (Produto p : lista) {
                    out.println("<form method=\"POST\" action=\"RemoverCarrinho\">");

                    out.println("<input type=\"hidden\" name=\"txtCodigo\" value=\"" + p.getCodigo() + "\" />");
                    out.println("<input type=\"hidden\" name=\"txtDescricao\" value=\"" + p.getDescricao() + "\" />");
                    out.println("<input type=\"hidden\" name=\"txtPreco\" value=\"" + p.getPreco() + "\" />");
                    out.println("<input type=\"hidden\" name=\"txtQtde\" value=\"" + p.getQtde() + "\" />");

                    out.println("<h2>Codigo: " + p.getCodigo() + " | Nome: " + p.getDescricao() +
                            " | Preço: " + p.getPreco() + " | Quantidade: " + p.getQtde() +
                            " <input type=\"submit\" name=\"b1\" value=\"AdicionarAoCarrinho\" />" +
                            "</h2>");

                    out.println("</form>");
                }
            }
            else{
                out.println("<h1>Carrinho Vazio!</h1>");
            }
            out.println("</body>");
            out.println("</html>");
         } catch (Exception ex) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Listar Carrinho</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Erro ao Listar Carrinho: " + ex.getMessage() + "</h1>");
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
