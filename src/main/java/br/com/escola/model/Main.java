package br.com.escola.main;

import br.com.escola.model.Aluno;
import br.com.escola.repository.AlunoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlunoRepository alunoRepo = new AlunoRepository();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- SISTEMA ESCOLAR ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do teclado

            switch (opcao) {
                case 1:
                    System.out.print("Nome do aluno: ");
                    String nome = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Data de Nascimento (dd/mm/aaaa): ");
                    String dataStr = scanner.nextLine();
                    LocalDate dataNascimento = LocalDate.parse(dataStr, formatter);

                    Aluno novoAluno = new Aluno(nome, email, dataNascimento);
                    try {
                        alunoRepo.salvar(novoAluno);
                        System.out.println("✅ Aluno cadastrado com sucesso!");
                    } catch (Exception e) {
                        System.err.println("❌ Erro ao salvar: Email já cadastrado ou dados inválidos.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE ALUNOS ---");
                    alunoRepo.listarTodos().forEach(a ->
                            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome() + " | Email: " + a.getEmail())
                    );
                    break;

                case 0:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;

                default:
                    System.out.println("⚠️ Opção inválida!");
            }
        }
        scanner.close();
    }
}