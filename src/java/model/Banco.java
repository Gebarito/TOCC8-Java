/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




public class Banco {

    public static EntityManagerFactory conexao = null;
    public EntityManager sessao;
    private final String nomeArqPersistence = "TOCC8_JAVAPU";// nome da unidade de persistência

    public Banco() throws Exception {
        try {
            if ((conexao == null) || (!conexao.isOpen())) {
                conexao = Persistence.createEntityManagerFactory(nomeArqPersistence);
            }
            sessao = conexao.createEntityManager();
            sessao.setProperty("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); //sem cache local
        } catch (Exception ex) {
            throw new Exception("Erro de conexão: " + ex.getMessage());
        }
    }
}
