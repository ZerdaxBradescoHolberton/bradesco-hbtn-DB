package demo;

import entities.Aluno;
import entities.Curso;
import entities.Endereco;
import entities.MaterialCurso;
import entities.Professor;
import entities.Telefone;
import models.AlunoModel;
import models.CursoModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GestaoCursosMain {

    private static final AlunoModel alunoModel = new AlunoModel();
    private static final CursoModel cursoModel = new CursoModel();

    private static Aluno a1;
    private static Aluno a2;

    private static Curso c1;
    private static Curso c2;


    public static void main(String[] args) {
        configuracaoInicial();
        testeAluno();
        testeCurso();
    }

    private static void configuracaoInicial() {
        a1 = new Aluno();
        a1.setNomeCompleto("Fulano da Silva");
        a1.setEmail("fulano@prove.dor");
        a1.setMatricula("001");
        a1.setNascimento(LocalDate.of(1995, 1, 31));
        Telefone a1t1 = new Telefone();
        a1t1.setDDD("11");
        a1t1.setNumero("987654321");
        a1.setTelefones(Set.of(a1t1));
        Endereco a1e1 = new Endereco();
        a1e1.setLogradouro("Rua dos Pigmeus");
        a1e1.setNumero("12");
        a1e1.setBairro("Campo dos Nativos");
        a1e1.setCidade("Bem Distante");
        a1e1.setCep(15555666);
        a1e1.setEstado("AM");
        a1e1.setEndereco(String.format("%s %s %s", a1e1.getLogradouro(), a1e1.getNumero(), a1e1.getCidade()));
        a1.setEnderecos(Set.of(a1e1));

        a2 = new Aluno();
        a2.setNomeCompleto("Beltrano da Silva");
        a2.setEmail("beltrano@prove.dor");
        a2.setMatricula("002");
        a2.setNascimento(LocalDate.of(1999, 6, 13));
        Telefone a2t1 = new Telefone();
        a2t1.setDDD("11");
        a2t1.setNumero("918765432");
        a2.setTelefones(Set.of(a2t1));
        Endereco a2e1 = new Endereco();
        a2e1.setLogradouro("Rua dos Pigmeus Albinos");
        a2e1.setNumero("43");
        a2e1.setBairro("Campo dos Inativos");
        a2e1.setCidade("Bem Mais Distante");
        a2e1.setCep(15666555);
        a2e1.setEstado("AP");
        a2e1.setEndereco(String.format("%s %s %s", a2e1.getLogradouro(), a2e1.getNumero(), a2e1.getCidade()));
        a2.setEnderecos(Set.of(a2e1));

        Professor p1 = new Professor();
        p1.setNomeCompleto("Proton da Silva");
        p1.setEmail("proton@prove.dor");
        p1.setMatricula("001");

        Professor p2 = new Professor();
        p2.setNomeCompleto("Pardal da Silva");
        p2.setEmail("pardal@prove.dor");
        p2.setMatricula("002");

        MaterialCurso mc1 = new MaterialCurso();
        mc1.setUrl("https://www.google.com");

        MaterialCurso mc2 = new MaterialCurso();
        mc2.setUrl("https://www.stackoverflow.com");

        c1 = new Curso();
        c1.setMaterialCurso(mc1);
        c1.setProfessor(p1);
        c1.setNome("Tudologia");
        c1.setSigla("TDLG");

        c2 = new Curso();
        c2.setMaterialCurso(mc2);
        c2.setProfessor(p2);
        c2.setNome("Pilhologia");
        c2.setSigla("PLLG");

    }

    private static void testeAluno() {

        // 1 - Cria alunos
        alunoModel.create(a1);
        alunoModel.create(a2);

        // 2 - Obtém lista de todos os alunos
        List<Aluno> alunos = alunoModel.findAll();
        if (alunos.isEmpty()) throw new RuntimeException("Não existem alunos na base.");
        alunos.forEach(System.out::println);
        System.out.printf("Quantidade de alunos cadastrados: %d%n", alunos.size());

        // 3 - Pesquisa por um aluno pelo id
        Aluno aluno = alunos.stream().findFirst().orElse(null);
        if (Objects.isNull(aluno)) throw new RuntimeException("Não existe aluno na base.");
        Aluno alunoPesquisar = alunoModel.findById(aluno.getId());
        System.out.printf("Encontrado o aluno [%s] com o id [%d]%n",
                alunoPesquisar.getNomeCompleto(), alunoPesquisar.getId());
        
        // 4 - Atualiza os dados do aluno
        aluno.setEmail("email.modificado@prove.dor");
        alunoModel.update(aluno);

        // 5 - Deleta um aluno
        alunoModel.delete(aluno);
    }

    private static void testeCurso() {
        // 1 - Cria cursos
        cursoModel.create(c1);
        cursoModel.create(c2);

        // 2 - Obtém lista de todos os cursos
        List<Curso> cursos = cursoModel.findAll();
        if (cursos.isEmpty()) throw new RuntimeException("Não existem cursos na base.");
        cursos.forEach(System.out::println);
        System.out.printf("Quantidade de cursos cadastrados: %d%n", cursos.size());

        // 3 - Pesquisa por um curso pelo id
        Curso curso = cursos.stream().findFirst().orElse(null);
        if (Objects.isNull(curso)) throw new RuntimeException("Não existe curso na base.");
        Curso cursoPesquisar = cursoModel.findById(curso.getId());
        System.out.printf("Encontrado o curso [%s] com o id [%d]%n",
                cursoPesquisar.getNome(), cursoPesquisar.getId());

        // 4 - Atualiza os dados do curso
        Set<Aluno> alunos = Set.of(a1, a2);
        if(Objects.isNull(curso.getAlunos()))
            curso.setAlunos(alunos);
        else
            curso.getAlunos().addAll(alunos);
        cursoModel.update(curso);
        Curso cursoAtualizado = cursoModel.findById(curso);
        System.out.printf("Curso Atualizado: %s", cursoAtualizado);

        // 5 - Deleta um curso
        cursoModel.delete(curso);
    }
}
