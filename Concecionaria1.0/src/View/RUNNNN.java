/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.GenericDao;
import Model.Funcionario;
import java.util.ArrayList;

/**
 *
 * @author Pedro Manjate
 */
public class RUNNNN {
    public static void main(String[] args) {
          GenericDao<Funcionario> dao =  new GenericDao<>(Funcionario.class);
                boolean name = false;
                ArrayList<Funcionario> all = dao.getAll();
                        for (Funcionario funcionario : all) {
                            if(funcionario.getEspecialidade().equalsIgnoreCase("vendedor")){
                                System.out.println(" email : "+ funcionario.geteMail()+" Senha: "+funcionario.getSenha());
                            }
                    
                }
    }
    
}
