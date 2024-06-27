package br.edu.up.daos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import br.edu.up.models.Aluno;

public class GerenciadorGeral {

    String csvDirectory = "/Users/pedrohbbier/Documents/faculdade/provaEngDeSoftwareDevDeSoftware/provaEngDeSoftwareDevDeSoftware/Prova/src/br/edu/up/daos/csvs/";

    String localArquivoAlunos = csvDirectory + "alunos.csv";

    String localArquivoResumo = csvDirectory + "resumo.csv";

    public boolean verSeTemResumo() {
        File file = new File(localArquivoResumo);
        try (Scanner scanner = new Scanner(file)) {
            return scanner.hasNextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void calcularMedia() {
        List<Aluno> alunos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(localArquivoAlunos))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                if (data.length == 3) {
                    int matricula = Integer.parseInt(data[0].trim());
                    String nome = data[1].trim();
                    float nota = Float.parseFloat(data[2].trim().replace(",", "."));
                    alunos.add(new Aluno(matricula, nome, nota));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        

        float media = 6.0f;
        float somaNotas = 0.0f;
        float maiorNota = Float.MIN_VALUE;
        float menorNota = Float.MAX_VALUE;
        List<Aluno> aprovados = new ArrayList<>();
        List<Aluno> reprovados = new ArrayList<>();
        int aprovadosCount = 0;
        int reprovadosCount = 0;

        for (Aluno aluno : alunos) {

            float nota = aluno.getNota();
            somaNotas += nota;

            if (nota > maiorNota) {
                maiorNota = nota;
            }

            if (nota < menorNota) {
                menorNota = nota;
            }

            if (aluno.getNota() >= media) {
                aprovadosCount++;
                aprovados.add(aluno);
            } else {
                reprovados.add(aluno);
                reprovadosCount++;
            }

        }

        float mediaGeral = somaNotas / alunos.size();

        int totalAlunos = alunos.size();

        try (PrintWriter writer = new PrintWriter(new FileWriter(localArquivoResumo))) {
            writer.println("Aprovados:");
            for (Aluno aluno : aprovados) {
                writer.printf("%d;%s;%.1f\n", aluno.getMatricula(), aluno.getNome(), aluno.getNota());
            }

            writer.println("Reprovados:");
            for (Aluno aluno : reprovados) {
                writer.printf("%d;%s;%.1f\n", aluno.getMatricula(), aluno.getNome(), aluno.getNota());
            }
            writer.printf("Total de alunos: %d\n", totalAlunos);
            writer.printf("Total de aprovados com nota igual ou superior a 6: %d\n", aprovadosCount);
            writer.printf("Total de reprovados: %d\n", reprovadosCount);
            writer.printf("Maior nota: %.1f\n", maiorNota);
            writer.printf("Menor nota: %.1f\n", menorNota);
            writer.printf("Média geral da turma: %.1f\n", mediaGeral);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
