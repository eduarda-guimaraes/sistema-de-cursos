package br.com.escola.repository;
import br.com.escola.model.Aluno;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AlunoRepository {
    private EntityManager em;

    public AlunoRepository() {
        this.em = br.com.escola.repository.FabricaConexao.getEntityManager();
    }

    public void salvar(Aluno aluno) {
        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
    }

    public List<Aluno> listarTodos() {
        return em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
    }

    public Aluno buscarPorEmail(String email) {
        try {
            return em.createQuery("SELECT a FROM Aluno a WHERE a.email = :email", Aluno.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Caso não encontre o aluno
        }
    }

    public Aluno buscarPorId(Long id) {
        return em.find(Aluno.class, id);
    }
}