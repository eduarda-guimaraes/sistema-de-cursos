package br.com.escola.main;

import br.com.escola.model.Aluno;
import br.com.escola.model.Curso;
import br.com.escola.model.Matricula;
import br.com.escola.repository.AlunoRepository;
import br.com.escola.repository.CursoRepository;
import br.com.escola.repository.MatriculaRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AlunoRepository alunoRepo = new AlunoRepository();
        CursoRepository cursoRepo = new CursoRepository();
        MatriculaRepository matriculaRepo = new MatriculaRepository();

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- 🎓 SISTEMA DE GESTÃO ESCOLAR ---");
            System.out.println("1. Gerenciar Alunos (Cadastro/Lista)");
            System.out.println("2. Gerenciar Cursos (Cadastro/Lista)");
            System.out.println("3. Realizar Matrícula");
            System.out.println("4. Relatório Geral de Matrículas");
            System.out.println("5. Remover Aluno");
            System.out.println("6. Editar Nome de Aluno");
            System.out.println("9. 🔍 CONSULTAS E BUSCAS"); // Submenu aqui
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("⚠️ Digite um número válido!");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRO DE ALUNO ---");
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    LocalDate dataNascimento = null;
                    while (dataNascimento == null) {
                        System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                        String dataStr = scanner.nextLine();
                        try {
                            dataNascimento = LocalDate.parse(dataStr, formatter);
                        } catch (Exception e) {
                            System.out.println("⚠️ Formato inválido! Use dd/MM/yyyy.");
                        }
                    }

                    try {
                        alunoRepo.salvar(new Aluno(nome, email, dataNascimento));
                        System.out.println("✅ Aluno cadastrado!");
                    } catch (Exception e) {
                        System.err.println("❌ Erro: E-mail já cadastrado.");
                    }
                    break;

                case 2:
                    System.out.print("Nome do Curso: ");
                    String nomeCurso = scanner.nextLine();
                    System.out.print("Carga Horária: ");
                    int carga = scanner.nextInt();
                    scanner.nextLine();
                    cursoRepo.salvar(new Curso(nomeCurso, "Descrição padrão", carga));
                    System.out.println("✅ Curso cadastrado!");
                    break;

                case 3:
                    System.out.print("ID do Aluno: ");
                    Long idA = scanner.nextLong();
                    System.out.print("ID do Curso: ");
                    Long idC = scanner.nextLong();
                    Aluno al = alunoRepo.buscarPorId(idA);
                    Curso cu = cursoRepo.buscarPorId(idC);
                    if (al != null && cu != null) {
                        matriculaRepo.salvar(new Matricula(al, cu, LocalDate.now()));
                        System.out.println("✅ Matrícula realizada!");
                    } else {
                        System.out.println("❌ Aluno ou Curso não encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- RELATÓRIO DE MATRÍCULAS ---");
                    matriculaRepo.listarTodas().forEach(m ->
                            System.out.printf("%-20s | %-15s | %-10s%n",
                                    m.getAluno().getNome(), m.getCurso().getNome(), m.getDataFormatada()));
                    break;

                case 5:
                    System.out.print("ID do Aluno para REMOVER: ");
                    Long idRemover = scanner.nextLong();
                    try {
                        alunoRepo.excluir(idRemover);
                        System.out.println("🗑️ Aluno removido!");
                    } catch (Exception e) {
                        System.err.println("❌ Erro: Aluno possui matrículas ativas.");
                    }
                    break;

                case 6:
                    System.out.print("ID do Aluno para EDITAR: ");
                    Long idEditar = scanner.nextLong();
                    scanner.nextLine();
                    Aluno alunoEdit = alunoRepo.buscarPorId(idEditar);
                    if (alunoEdit != null) {
                        System.out.print("Novo nome: ");
                        alunoEdit.setNome(scanner.nextLine());
                        alunoRepo.atualizar(alunoEdit);
                        System.out.println("✅ Nome atualizado!");
                    }
                    break;

                case 9:
                    // --- INÍCIO DO SUBMENU DE BUSCAS ---
                    int subOpcao = -1;
                    while (subOpcao != 0) {
                        System.out.println("\n--- 🔍 MENU DE BUSCAS ---");
                        System.out.println("1. Buscar Aluno por Nome");
                        System.out.println("2. Buscar Aluno por E-mail");
                        System.out.println("3. Listar Alunos por Curso");
                        System.out.println("4. Buscar Curso por Nome");
                        System.out.println("0. Voltar ao Menu Principal");
                        System.out.print("Escolha uma busca: ");

                        subOpcao = scanner.nextInt();
                        scanner.nextLine();

                        switch (subOpcao) {
                            case 1:
                                System.out.print("Nome do aluno: ");
                                String nb = scanner.nextLine();
                                alunoRepo.buscarPorNome(nb).forEach(a ->
                                        System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                                break;
                            case 2:
                                System.out.print("E-mail exato: ");
                                Aluno ae = alunoRepo.buscarPorEmail(scanner.nextLine());
                                System.out.println(ae != null ? "Encontrado: " + ae.getNome() : "Não encontrado.");
                                break;
                            case 3:
                                System.out.print("ID do Curso: ");
                                Long idCf = scanner.nextLong();
                                var mats = matriculaRepo.listarPorCurso(idCf);
                                if (!mats.isEmpty()) {
                                    System.out.println("Curso: " + mats.get(0).getCurso().getNome());
                                    mats.forEach(m -> System.out.println("- " + m.getAluno().getNome()));
                                } else {
                                    System.out.println("Nenhum aluno neste curso.");
                                }
                                break;
                            case 4:
                                System.out.print("Nome do curso: ");
                                cursoRepo.buscarPorNome(scanner.nextLine()).forEach(c ->
                                        System.out.println("ID: " + c.getId() + " | " + c.getNome()));
                                break;
                        }
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;
            }
        }
        scanner.close();
    }
}