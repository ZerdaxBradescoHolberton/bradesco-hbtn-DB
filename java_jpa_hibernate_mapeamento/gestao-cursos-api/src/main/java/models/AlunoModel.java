package models;

import entities.Aluno;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlunoModel implements Persistable<Aluno> {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
    private EntityManager em = emf.createEntityManager();

    @Override
    public void create(Aluno aluno) {
        if(!em.isOpen()) em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();
            System.out.println("Aluno criado ou atualizado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar ou atualizar um aluno !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    @Override
    public void update(Aluno aluno) {
        if(!em.isOpen()) em = emf.createEntityManager();
        System.out.printf("Atualizando dados do aluno [%s] com id [%d]", aluno.getNomeCompleto(), aluno.getId());
        Aluno alunoAtualizar = em.merge(aluno);
        create(alunoAtualizar);
        System.out.println("Dados de aluno atualizados com sucesso.");
    }

    @Override
    public void delete(Aluno aluno) {
        if(!em.isOpen()) em = emf.createEntityManager();
        Aluno alunoRemover = em.merge(aluno);
        if(Objects.nonNull(alunoRemover)) {
            try {
                System.out.println("Iniciando a transação");
                em.getTransaction().begin();
                em.remove(alunoRemover);
                em.getTransaction().commit();
                System.out.printf("Aluno %s com id [%d] removido com sucesso.%n",
                        alunoRemover.getNomeCompleto(), alunoRemover.getId());
            } catch (Exception e) {
                em.close();
                throw new RuntimeException(e);
            } finally {
                em.close();
                System.out.println("Finalizando a transação");
            }
        }
    }

    @Override
    public Aluno findById(Aluno aluno) {
        return findById(aluno.getId());
    }

    @Override
    public Aluno findById(Long id) {
        if(!em.isOpen()) em = emf.createEntityManager();
        Aluno aluno = null;
        try {
            System.out.printf("Iniciando a consulta pelo id [%d]%n", id);
            aluno = em.find(Aluno.class, id);
        } catch (Exception e) {
            em.close();
            throw new RuntimeException(e);
        } finally {
            em.close();
            System.out.println("Finalizando a consulta por id");
        }
        return aluno;
    }

    @Override
    public List<Aluno> findAll() {
        if(!em.isOpen()) em = emf.createEntityManager();
        List<Aluno> alunos = new ArrayList<>();
        try {
            System.out.println("Iniciando a consulta de todos os alunos");
            alunos.addAll(em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList());
        } catch (Exception e) {
            em.close();
            throw new RuntimeException(e);
        } finally {
            em.close();
            System.out.println("Finalizando a consulta de todos os alunos");
        }
        return alunos;
    }
}
