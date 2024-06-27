package br.edu.up.view;
import java.util.Scanner;
import br.edu.up.controllers.AlunoController;


public class Menu {

    private static AlunoController AlunoController = new AlunoController();

    
    public void menu(){

        Scanner s = new Scanner(System.in);
        
        System.out.println("Digite 1 para ver a média dos alunos");

        int n = s.nextInt();

        if(n == 1){
            verMedia();
        } else {
            System.out.println("Opção inválida");
        }

        

    }
    
    public void verMedia(){
        AlunoController.calcularMedia();
    }

}
