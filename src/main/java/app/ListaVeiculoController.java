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
import javafx.fxml.Initializable;
import modelo.Veiculo;
import util.Dao;


public class ListaVeiculoController implements Initializable {
    
    @FXML
    private TableView<Veiculo> tabela;
    
     @FXML
    private TableColumn<Veiculo, String> colunaMarca;
    
    @FXML
    private TableColumn<Veiculo, String> colunaModelo;
    
    @FXML
    private TableColumn<Veiculo, String> colunaPlaca;
    
    
    private List<Veiculo> veiculo;
    private ObservableList<Veiculo> listarVeiculo;
    
    
    public void initialize(URL location, ResourceBundle resources){
        Dao<Veiculo> daoVeiculo = new Dao( Veiculo.class);
        
        veiculo = daoVeiculo.listarTodos();
        listarVeiculo = FXCollections.observableArrayList(veiculo);
        
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));        
        colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        
        tabela.setItems(listarVeiculo);
        
        tabela.sort();
    }
        
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }
    
}

