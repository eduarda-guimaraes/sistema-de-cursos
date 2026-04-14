package main;

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
        AlunoRepository alunoRepo = new AlunoRepository();
        CursoRepository cursoRepo = new CursoRepository();
        MatriculaRepository matriculaRepo = new MatriculaRepository();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- 🎓 SISTEMA DE GESTÃO ESCOLAR ---");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Listar Alunos");
            System.out.println("3. Cadastrar Curso");
            System.out.println("4. Listar Cursos");
            System.out.println("5. Realizar Matrícula");
            System.out.println("6. Relatório de Matrículas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Data de Nascimento (dd/MM/yyyy): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine(), formatter);
                    alunoRepo.salvar(new Aluno(nome, email, data));
                    System.out.println("✅ Aluno salvo!");
                    break;

                case 2:
                    System.out.println("\n--- LISTA DE ALUNOS ---");
                    alunoRepo.listarTodos().forEach(a ->
                            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome()));
                    break;

                case 3:
                    System.out.print("Nome do Curso: ");
                    String nomeC = scanner.nextLine();
                    System.out.print("Carga Horária: ");
                    int carga = scanner.nextInt();
                    scanner.nextLine();
                    cursoRepo.salvar(new Curso(nomeC, "Descrição padrão", carga));
                    System.out.println("✅ Curso salvo!");
                    break;

                case 4:
                    System.out.println("\n--- LISTA DE CURSOS ---");
                    cursoRepo.listarTodos().forEach(c ->
                            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " (" + c.getCargaHoraria() + "h)"));
                    break;

                case 5:
                    System.out.print("ID do Aluno: ");
                    Long idA = scanner.nextLong();
                    System.out.print("ID do Curso: ");
                    Long idC = scanner.nextLong();

                    Aluno al = alunoRepo.buscarPorId(idA);
                    Curso cu = cursoRepo.buscarPorId(idC);

                    if (al != null && cu != null) {
                        matriculaRepo.salvar(new Matricula(al, cu, LocalDate.now()));
                        System.out.println("✅ Matrícula confirmada!");
                    } else {
                        System.out.println("❌ Aluno ou Curso não encontrado.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- MATRÍCULAS ATIVAS ---");
                    matriculaRepo.listarTodas().forEach(m ->
                            System.out.println("Aluno: " + m.getAluno().getNome() + " -> Curso: " + m.getCurso().getNome()));
                    break;

                case 0:
                    System.out.println("Encerrando... Até mais!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }
}