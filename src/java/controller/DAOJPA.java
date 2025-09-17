/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.util.List;
import java.util.Objects;
import model.Banco;
import model.Produto;

/**
 *
 * @author prampero
 */
public class DAOJPA {

    public void gravar(Object obj) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            banco.sessao.getTransaction().begin();
            banco.sessao.persist(obj);
            banco.sessao.getTransaction().commit();
            Banco.conexao.close();
        } catch (Exception ex) {
            throw new Exception("Erro ao gravar: " + ex.getMessage());
        }
    }

    public void alterar(Object obj) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            banco.sessao.getTransaction().begin();
            banco.sessao.merge(obj);
            banco.sessao.getTransaction().commit();
            Banco.conexao.close();
       } catch (Exception ex) {
            throw new Exception("Erro ao alterar: " + ex.getMessage());
        } 
    }

    public void remover( int id) throws Exception {
        Banco banco;
        Object obj;
        try {
            banco = new Banco();
            banco.sessao.getTransaction().begin();
            obj = banco.sessao.find(Produto.class, id);
            if (obj != null) {
                banco.sessao.remove(obj);
            }
            banco.sessao.getTransaction().commit();
            Banco.conexao.close();
        } catch (Exception ex) {
            throw new Exception("Erro ao remover: " + ex.getMessage());
        } 
    }

    public Object getById(final int id) throws Exception {
        Banco banco;
        Object obj;
        try {
            banco = new Banco();
            obj = banco.sessao.find(Produto.class, id);
            Banco.conexao.close();
            return obj;
        } catch (Exception ex) {
            throw new Exception("Erro ao getById: " + ex.getMessage());
        } finally {

        }

    }
    
   
 public List<Produto> listar(String parte) throws Exception {
        Banco banco;
        List<Produto> lista;
        try {
            banco = new Banco();
            lista = banco.sessao.createQuery("select p from Produto p where p.descricao like :n order by p.descricao")
                .setParameter("n", "%" + parte + "%")
                .getResultList();
            Banco.conexao.close();
            return lista;
        } catch (Exception ex) {
            return (null);
        }
    }
 
    public List<Produto> listarTodos() throws Exception {
        Banco banco;
        List<Produto> lista;
        try {
            banco = new Banco();
            lista = banco.sessao.createNamedQuery("Produto.findAll", Produto.class).getResultList();
            Banco.conexao.close();
            return lista;
        } catch (Exception ex) {
            throw new Exception("Erro ao listarTodos: " + ex.getMessage());
        }
    }
    
    public void adicionarAoCarrinho(Produto produtoEmEstoque, List<Produto> carrinho) throws Exception {
        try{
            if (produtoEmEstoque.getQtde() > 0){
                boolean pExiste = false;
                for (Produto p : carrinho){
                    if (Objects.equals(produtoEmEstoque.getCodigo(), p.getCodigo())){
                        p.setQtde(p.getQtde() + 1);
                        
                        produtoEmEstoque.setQtde(produtoEmEstoque.getQtde() - 1);
                        this.alterar(produtoEmEstoque);
                        
                        pExiste = true;
                    }
                }
                
                if (!pExiste){
                    Produto novoProduto = new Produto();
                    novoProduto.setCodigo(produtoEmEstoque.getCodigo());
                    novoProduto.setDescricao(produtoEmEstoque.getDescricao());
                    novoProduto.setPreco(produtoEmEstoque.getPreco());
                    novoProduto.setQtde(1);
                    carrinho.add(novoProduto);

                    produtoEmEstoque.setQtde(produtoEmEstoque.getQtde() - 1);           
                    this.alterar(produtoEmEstoque);
                }
            }
        }catch(Exception ex){
            throw new Exception("Erro ao adicionar no carrinho: " + ex.getMessage());
        }
    }
    
    public void removerCarrinho(Produto produto, List<Produto> carrinho) throws Exception{
        try{
            List<Produto> estoque = this.listarTodos();

            if (carrinho != null && produto != null){
                carrinho.remove(produto);
            }

            for(Produto p : estoque){
                if (Objects.equals(p.getCodigo(), produto.getCodigo())){
                    p.setQtde(produto.getQtde());
                }
            }
        }catch(Exception ex){
            throw new Exception("Erro ao remover do carrinho: " + ex.getMessage());
        }
    }
/*
    public List<Cliente> listarTodosSemCache() throws Exception {
        Banco banco;
        Query consulta;
        List<Cliente> lista;
        try {
            banco = new Banco();
            consulta = banco.sessao.createNamedQuery("Cliente.findAll", Cliente.class);
            consulta.setHint("r01", CacheRetrieveMode.BYPASS);  //não utiliza o cache
            lista = consulta.getResultList();
            Banco.conexao.close();
            return lista;
        } catch (Exception ex) {
            throw new Exception("Erro ao listarTodos: " + ex.getMessage());
        }
    }

   

    public List<Cliente> listarSemCache() throws Exception {
        Banco banco;
        List<Cliente> lista;
        try {
            banco = new Banco();
            lista = banco.sessao.createQuery("select c from Cliente c order by c.nome")
                    .setHint("n1", CacheRetrieveMode.BYPASS) //não utiliza o cache
                    .getResultList();
            Banco.conexao.close();
            return (lista);

        } catch (Exception ex) {
            return (null);
        }
    }

    public List<Object> listaMedia() throws Exception {
        Banco banco;
        List<Object> lista;
        try {
            banco = new Banco();
            lista = banco.sessao.createQuery("SELECT a.nome, avg(n.valor) FROM Nota n inner join n.codaluno a group by a.codigo,a.nome")
                    .getResultList();
            Banco.conexao.close();
            return lista;
        } catch (Exception ex) {
            throw new Exception(" Erro no listar média: " + ex.getMessage());
        }
    }

    public List<Object> listaAlunoMaiorNota() throws Exception {
        Banco banco;
        List<Object> lista;
        try {
            banco = new Banco();
            lista = banco.sessao.createQuery("select distinct a.nome, n.valor from Nota n inner join n.codaluno a "
                    + " where n.valor in (select max(n1.valor) from Nota n1) ")
                    .getResultList();
            Banco.conexao.close();
            return lista;
        } catch (Exception ex) {
            throw new Exception(" Erro no listar maior nota: " + ex.getMessage());
        }
    }

    
    public List<Usuario> listarTodosUsuarios() throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return banco.sessao.createNamedQuery("Usuario.findAll" , Usuario.class).getResultList();
        } catch (Exception ex) {
            throw new Exception("Erro ao listarTodosUsuarios: " + ex.getMessage());
        }
    }
    
    public List<Object> mensagens0(Integer codigo) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return  banco.sessao.createQuery("SELECT  m FROM Mensagem m  where m.codigoremetente in (select u from Usuario u where u.codigo=:cod) or m.codigoremetente in "
          + " (select a.codusuario from Amigo a, Usuario u where a.codamigo= u.codigo and u.codigo=:cod) order by m.datahora desc")
                    .setParameter("cod", codigo)
                    .getResultList();
        } catch (Exception ex) {
            throw new Exception("Erro ao buscar Mensagens: " + ex.getMessage());
        }
    }
    //Retorna a lista de mensagem que o usuario com codigo passado pode receber.
     public List<Object> mensagens(Integer codigo) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return banco.sessao.createQuery("SELECT m FROM Mensagem m inner join m.codigoremetente u where  u.codigo=:cod or m.codigoremetente in "
          + " (select a.codusuario from Amigo a inner join a.codamigo u where u.codigo=:cod) order by m.datahora desc")
          .setParameter("cod", codigo)
          .getResultList();
        } catch (Exception ex) {
            throw new Exception("Erro ao buscar Mensagens: " + ex.getMessage());
        }
    }
    
    public Object login(String n, String s) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return   banco.sessao.createQuery("SELECT u FROM Usuario u where u.nome=:nome and u.senha=:senha")
                    .setParameter("nome",n)
                    .setParameter("senha", s).getSingleResult();
        } catch (Exception ex) {
            return(null);
        }
    }
    
    
    // retorna a lista de usuarios que receberam a declaracao de amizade do codigo recebido
    public List<Usuario> listarAmigos(Integer codigo) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return  banco.sessao.createQuery("SELECT a.codamigo FROM Amigo a inner join a.codusuario u where u.codigo=:cod")
                    .setParameter("cod", codigo)
                    .getResultList();
        } catch (Exception ex) {
            throw new Exception("Erro ao buscar Amigos: " + ex.getMessage());
        }
    }
    
    // retorna a lista de usuarios que receberam a declaracao de amizade do codigo recebido
    public List<Usuario> listarUsuariosDeclaramAmizade(Integer codigo) throws Exception {
        Banco banco;
        try {
            banco = new Banco();
            return  banco.sessao.createQuery("SELECT a.codusuario FROM Amigo a inner join a.codamigo u where u.codigo=:cod")
                    .setParameter("cod", codigo)
                    .getResultList();
        } catch (Exception ex) {
            throw new Exception("Erro ao buscar quem declarou amizade: " + ex.getMessage());
        }
    }
     */
}
