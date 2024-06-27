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
        List<Aluno> aprovados = new ArrayList<>();
        List<Aluno> reprovados = new ArrayList<>();

        for (Aluno aluno : alunos) {
            if (aluno.getNota() >= media) {
                aprovados.add(aluno);
            } else {
                reprovados.add(aluno);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(localArquivoResumo))) {
            writer.println("Aprovados:");
            for (Aluno aluno : aprovados) {
                writer.printf("%d;%s;%.1f\n", aluno.getMatricula(), aluno.getNome(), aluno.getNota());
            }

            writer.println("Reprovados:");
            for (Aluno aluno : reprovados) {
                writer.printf("%d;%s;%.1f\n", aluno.getMatricula(), aluno.getNome(), aluno.getNota());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
