package demo;

import entities.Pessoa;
import entities.Produto;
import models.PessoaModel;
import models.ProdutoModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AdministrativoApp {

    public static void main(String[] args) {
        ProdutoModel produtoModel = new ProdutoModel();

        Produto p1 = new Produto();
        p1.setNome("TV");
        p1.setPreco(300.0);
        p1.setQuantidade(100);
        p1.setStatus(true);

        Produto p2 = new Produto();
        p2.setNome("PlayStation");
        p2.setPreco(4200.0);
        p2.setQuantidade(40);
        p2.setStatus(true);

        // 1) Criando um produto
        produtoModel.create(p1);
        produtoModel.create(p2);

        //2) Buscando todos os produtos na base de dados
        List<Produto> produtos = produtoModel.findAll();
        System.out.println("Qtde de produtos encontrados : " + produtos.size());

        // TODO - Testar os demais metódos das classes: ProdutoModel e PessoaModel
        Produto produto = produtos.stream().findFirst().orElse(null);
        if(Objects.nonNull(produto)) {
            produto.setPreco(produto.getPreco() * 1.2);
            produtoModel.update(produto);
            Produto produtoAlterado = produtoModel.findById(produto);
            System.out.printf("O novo valor do produto com id %d é R$ %.2f",
                    produtoAlterado.getId(), produtoAlterado.getPreco());
        }
        produto = produtos.stream().findAny().orElse(null);
        produtoModel.delete(produto);
        produtoModel.findById(produto);
        testaPessoa();
    }

    private static void testaPessoa() {

        PessoaModel pessoaModel = new PessoaModel();

        Pessoa p1 = new Pessoa();
        p1.setCpf(12345678901L);
        p1.setEmail("fulano@prove.dor");
        p1.setDataDeNascimento(LocalDate.of(1983, 7, 8));
        p1.setNome("Fulano de Andrade");
        p1.setIdade(42);

        Pessoa p2 = new Pessoa();
        p2.setCpf(11234567890L);
        p2.setEmail("beltrano@prove.dor");
        p2.setDataDeNascimento(LocalDate.of(1984, 8, 10));
        p2.setNome("Beltrano de Andrade");
        p2.setIdade(41);

        Pessoa p3 = new Pessoa();
        p3.setCpf(90112345678L);
        p3.setEmail("sicrana@prove.dor");
        p3.setDataDeNascimento(LocalDate.of(1984, 8, 28));
        p3.setNome("Sicrana Lima de Andrade");
        p3.setIdade(36);

        // 1 - Criando pessoas
        pessoaModel.create(p1);
        pessoaModel.create(p2);
        pessoaModel.create(p3);

        // 2 - Buscando todas as pessoas na base
        List<Pessoa> pessoas = pessoaModel.findAll();
        pessoas.forEach(System.out::println);
        System.out.println("Qtde de pessoas encontradas : " + pessoas.size());

        // 3 - Buscando uma pessoa
        Pessoa pessoa = pessoas.stream().findFirst().orElse(null);
        Pessoa pessoaPesquisada = pessoaModel.findById(pessoa);

        // 4 - Atualizando dado da pessoa
        if(Objects.nonNull(pessoaPesquisada)) {
            pessoaPesquisada.setIdade(pessoaPesquisada.getIdade() + 5);
            pessoaModel.update(pessoaPesquisada);
            Pessoa pessoaAtualizada = pessoaModel.findById(pessoaPesquisada);
            System.out.printf("A nova idade da pessoa id [%d] é %d%n",
                    pessoaAtualizada.getId(), pessoaAtualizada.getIdade());
        }

        // 5 - Remoção da pessoa da base
        pessoaModel.delete(pessoaPesquisada);
        pessoaModel.findById(pessoaPesquisada);
    }
}
