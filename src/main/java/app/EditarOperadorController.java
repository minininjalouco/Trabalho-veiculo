/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelo.Operador;
import util.Dao;

public class EditarOperadorController {
    
    @FXML
    private TextField campoNome; 
    
    @FXML
    private TextField campoEndereco;  
    
    @FXML
    private TextField campoLogin;
    
    @FXML
    private TextField campoSenha;
        
    @FXML
    private ComboBox<Operador> comboOperador;
    
    private ObservableList<Operador> listaOb;
    private List<Operador> lista;
    private Dao<Operador> dao;
    
    @FXML
    private void initialize(){
        dao = new Dao(Operador.class);
        lista = dao.listarTodos();
        listaOb = FXCollections.observableArrayList(lista);
        comboOperador.setItems(listaOb);
        
        comboOperador.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            exibirDetalhesOperador(newValue);
        });
    }
    
    private void exibirDetalhesOperador(Operador operador) {
        if (operador != null) {
            campoNome.setText(operador.getNome());
            campoEndereco.setText(operador.getEndereco());
            campoLogin.setText(operador.getLogin());
            campoSenha.setText(operador.getSenha());
        } else {
            campoNome.clear();
            campoEndereco.clear();
            campoLogin.clear();
            campoSenha.clear();
        }
    }
    
    private boolean verificarCredenciais(String login, String senha) {
        List<Operador> operadores = dao.listarTodos();

        for (Operador operador : operadores) {
               if (operador.getLogin().equals(login) && operador.getSenha().equals(senha)) {
                return true; 
            }
        }
        return false; 
    }
    
    @FXML
    private void editarOperador() {
        Operador temp = comboOperador.getSelectionModel().getSelectedItem();
        if (temp != null) {
            try {
                String nome = campoNome.getText();
                String endereco = campoEndereco.getText();
                String login = campoLogin.getText();
                String senha = campoSenha.getText();

            
                if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {
                    throw new IllegalArgumentException("Nome, Login ou Senha não podem estar vazios");
                }

                temp.setNome(nome);
                temp.setEndereco(endereco);
                temp.setLogin(login);
                temp.setSenha(senha);

                dao.alterar(temp);

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setContentText("As informações do operador foram alteradas");
                alert.show();

            } catch (IllegalArgumentException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        
            try {
                voltarAoMenu();
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }  
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }
    
}







