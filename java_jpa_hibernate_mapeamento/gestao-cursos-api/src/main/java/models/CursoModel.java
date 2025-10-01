package models;

import entities.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CursoModel implements Persistable<Curso> {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestao-cursos-jpa");
    private EntityManager em = emf.createEntityManager();

    @Override
    public void create(Curso curso) {
        if(!em.isOpen()) em = emf.createEntityManager();
        try {
            System.out.println("Iniciando a transação");
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            System.out.println("Curso criado ou atualizado com sucesso !!!");
        } catch (Exception e) {
            em.close();
            System.err.println("Erro ao criar ou atualizar um curso !!!" + e.getMessage());
        } finally {
            em.close();
            System.out.println("Finalizando a transação");
        }
    }

    @Override
    public void update(Curso curso) {
        if(!em.isOpen()) em = emf.createEntityManager();
        System.out.printf("Atualizando dados do curso [%s] com id [%d]", curso.getNome(), curso.getId());
        Curso cursoAtualizar = em.merge(curso);
//        Set<Aluno> alunos = new HashSet<>();
//        curso.getAlunos().forEach(aluno -> alunos.add(em.merge(aluno)));
//        curso.setAlunos(alunos);
        create(cursoAtualizar);
        System.out.println("Dados de curso atualizados com sucesso.");
    }

    @Override
    public void delete(Curso curso) {
        if(!em.isOpen()) em = emf.createEntityManager();
        Curso cursoRemover = em.merge(curso);
        if(Objects.nonNull(cursoRemover)) {
            try {
                System.out.println("Iniciando a transação");
                em.getTransaction().begin();
                em.remove(cursoRemover);
                em.getTransaction().commit();
                System.out.printf("Curso [%s] com id [%d] removido com sucesso.%n",
                        cursoRemover.getNome(), cursoRemover.getId());
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
    public Curso findById(Curso curso) {
        return findById(curso.getId());
    }

    @Override
    public Curso findById(Long id) {
        if(!em.isOpen()) em = emf.createEntityManager();
        Curso curso = null;
        try {
            System.out.printf("Iniciando a consulta do curso pelo id [%d]%n", id);
            curso = em.find(Curso.class, id);
        } catch (Exception e) {
            em.close();
            throw new RuntimeException(e);
        } finally {
            em.close();
            System.out.println("Finalizando a consulta do curso por id");
        }
        return curso;
    }

    @Override
    public List<Curso> findAll() {
        if(!em.isOpen()) em = emf.createEntityManager();
        List<Curso> cursos = new ArrayList<>();
        try {
            System.out.println("Consultando a lista de todos os cursos.");
            cursos.addAll(em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList());
        } catch (Exception e) {
            em.close();
            throw new RuntimeException(e);
        } finally {
            em.close();
            System.out.println("Consulta de cursos finalizada.");
        }
        return cursos;
    }
}
