package br.edu.up.controllers;

import br.edu.up.daos.GerenciadorGeral;

public class AlunoController {
    
    public void calcularMedia() {
        GerenciadorGeral gerenciador = new GerenciadorGeral();
        if (!gerenciador.verSeTemResumo()) {
            gerenciador.calcularMedia();
        } else {
            System.out.println("Resumo já existe.");
        }
    }
}
