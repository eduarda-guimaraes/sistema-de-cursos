package br.com.escola.repository;

import br.com.escola.model.Matricula;
import jakarta.persistence.EntityManager;
import java.util.List;

public class MatriculaRepository {
    private final EntityManager em;

    public MatriculaRepository() {
        this.em = br.com.escola.repository.FabricaConexao.getEntityManager();
    }

    public void salvar(Matricula matricula) {
        em.getTransaction().begin();
        em.persist(matricula);
        em.getTransaction().commit();
    }

    public List<Matricula> listarTodas() {
        // Usamos o JOIN FETCH para carregar os dados do aluno e curso junto com a matrícula
        return em.createQuery("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.curso", Matricula.class)
                .getResultList();
    }

    public List<Matricula> listarPorCurso(Long cursoId) {
        return em.createQuery(
                        "SELECT m FROM Matricula m JOIN FETCH m.aluno WHERE m.curso.id = :cursoId",
                        Matricula.class)
                .setParameter("cursoId", cursoId)
                .getResultList();
    }
}