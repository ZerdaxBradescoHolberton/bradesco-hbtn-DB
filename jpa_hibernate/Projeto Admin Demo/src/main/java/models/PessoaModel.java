package models;

import entities.Pessoa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PessoaModel {

    public void create(Pessoa p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println("Pessoa criada com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar a Pessoa !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void update(Pessoa p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            Pessoa pessoaAtualizar = em.merge(p);
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(pessoaAtualizar);
            em.getTransaction().commit();
            System.out.println("Pessoa atualizada com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao atualizar a Pessoa !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void delete(Pessoa p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            Pessoa pessoaRemover = em.merge(p);
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.remove(pessoaRemover);
            em.getTransaction().commit();
            System.out.println("Pessoa removida com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao remover a Pessoa !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Pessoa findById(Pessoa p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        Pessoa result = null;
        try {
            result = em.find(Pessoa.class, p.getId());
            if(Objects.isNull(result)) {
                System.out.printf("Não foi possível encontrar a pessoa [%s] com id [%d]%n", p.getNome(), p.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return result;
    }

    public List<Pessoa> findAll() {
        List<Pessoa> Pessoas = new ArrayList<Pessoa>();
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            Pessoas.addAll(em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return Pessoas;
    }
}
