package models;

import entities.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProdutoModel {

    public void create(Produto p) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            System.out.println("Produto criado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar o Produto !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void update(Produto p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            Produto produtoAtualizar = em.merge(p);
            em.getTransaction().begin();
            em.persist(produtoAtualizar);
            em.getTransaction().commit();
            System.out.println("Produto atualizado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao atualizar o Produto !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public void delete(Produto p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            Produto produtoRemover = em.merge(p);
            em.getTransaction().begin();
            em.remove(produtoRemover);
            em.getTransaction().commit();
            System.out.println("Produto removido com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao remover o Produto !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    public Produto findById(Produto p) {
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();
        Produto result = null;
        try {
            result = em.find(Produto.class, p.getId());
            if(Objects.isNull(result)) {
                System.out.printf("Não foi possível encontrar o produto [%s] com id [%d]%n", p.getNome(), p.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return result;
    }

    public List<Produto> findAll() {
        List<Produto> Produtos = new ArrayList<Produto>();
        //TODO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("admin-jpa");
        EntityManager em = emf.createEntityManager();

        try {
            Produtos.addAll(em.createQuery("SELECT p FROM Produto p", Produto.class).getResultList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return Produtos;
    }

}
