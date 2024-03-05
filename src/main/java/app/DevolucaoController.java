/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import modelo.UsoVeiculo;
import util.Dao;
import javafx.fxml.Initializable;
import java.util.stream.Collectors;


public class DevolucaoController {
    
    @FXML
    private ComboBox<UsoVeiculo> boxUsoVeiculo;
    
    private List<UsoVeiculo> usoVeiculo;
    private ObservableList<UsoVeiculo> listUsoVeiculo;
    
    
    @FXML
    private DatePicker dataPicker;
    
    @FXML
    private void initialize() {
        Dao<UsoVeiculo> daoUsoVeiculo = new Dao(UsoVeiculo.class);
        
        usoVeiculo = daoUsoVeiculo.listarTodos().stream()
            .filter(u -> u.getDevolucao() == null)
            .collect(Collectors.toList());
        listUsoVeiculo = FXCollections.observableArrayList(usoVeiculo);
        boxUsoVeiculo.getItems().addAll(listUsoVeiculo);
        
        dataPicker.setValue(LocalDate.now());
    }
    
    @FXML
    private void devolucao() {
        UsoVeiculo op = boxUsoVeiculo.getValue();
        
        Dao<UsoVeiculo> dao = new Dao(UsoVeiculo.class);
        op.setDevolucao(dataPicker.getValue());
        dao.alterar(op);
        
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("");
        alerta.setContentText("Veiculo devolvido com sucesso");
        alerta.showAndWait();
        
        try {
             voltarAoMenu();
        } catch (IOException e) {
             e.printStackTrace(); 
        }
    }
    
     @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }
    
}



