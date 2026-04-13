package br.com.escola.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FabricaConexao {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("escolaPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}