package br.com.escola.repository;

import br.com.escola.model.Curso;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CursoRepository {
    private final EntityManager em;

    public CursoRepository() {
        this.em = br.com.escola.repository.FabricaConexao.getEntityManager();
    }

    public void salvar(Curso curso) {
        em.getTransaction().begin();
        em.persist(curso);
        em.getTransaction().commit();
    }

    public List<Curso> listarTodos() {
        return em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
    }

    public Curso buscarPorId(Long id) {
        return em.find(Curso.class, id);
    }
}