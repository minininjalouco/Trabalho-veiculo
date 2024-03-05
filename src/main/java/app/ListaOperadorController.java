/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Operador;
import javafx.fxml.Initializable;
import util.Dao;


public class ListaOperadorController implements Initializable {
    
    @FXML
    private TableView<Operador> tabela;
    
     @FXML
    private TableColumn<Operador, String> colunaNome;
    
    @FXML
    private TableColumn<Operador, String> colunaEndereco;
    
    @FXML
    private TableColumn<Operador, String> colunaLogin;
    
    @FXML
    private TableColumn<Operador, String> colunaSenha;

    private List<Operador> operador;
    private ObservableList<Operador> listarOperador;
    
    
    public void initialize(URL location, ResourceBundle resources){
        Dao<Operador> daoOperador = new Dao(Operador.class);
        
        operador = daoOperador.listarTodos();
        listarOperador = FXCollections.observableArrayList(operador);
        
        colunaNome.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        colunaEndereco.setCellValueFactory(
                new PropertyValueFactory<>("endereco"));        
        colunaLogin.setCellValueFactory(
                new PropertyValueFactory<>("login"));
        colunaSenha.setCellValueFactory(
                new PropertyValueFactory<>("senha"));
        tabela.setItems(listarOperador);
        
        tabela.sort();
    }
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }
    
}

