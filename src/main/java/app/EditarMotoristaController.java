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
import modelo.Motorista;
import util.Dao;

public class EditarMotoristaController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEndereco;

    @FXML
    private TextField campoCnh;

    @FXML
    private TextField campoCategoria;

    @FXML
    private TextField campoSetor;

    @FXML
    private ComboBox<Motorista> comboMotoristas;

    private ObservableList<Motorista> listaOb;
    private List<Motorista> lista;
    private Dao<Motorista> dao;

    @FXML
    private void initialize() {
        dao = new Dao<>(Motorista.class);
        lista = dao.listarTodos();
        listaOb = FXCollections.observableArrayList(lista);
        comboMotoristas.setItems(listaOb);

        comboMotoristas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            exibirDetalhesMotorista(newValue);
        });
    }

    private void exibirDetalhesMotorista(Motorista motorista) {
        if (motorista != null) {
            campoNome.setText(motorista.getNome());
            campoEndereco.setText(motorista.getEndereco());
            campoCnh.setText(String.valueOf(motorista.getCnh()));
            campoCategoria.setText(motorista.getCategoria());
            campoSetor.setText(motorista.getSetor());
        } else {
            
            campoNome.clear();
            campoEndereco.clear();
            campoCnh.clear();
            campoCategoria.clear();
            campoSetor.clear();
        }
    }

    @FXML
    private void editarMotorista() {
        Motorista temp = comboMotoristas.getSelectionModel().getSelectedItem();
        if (temp != null) {
            temp.setNome(campoNome.getText());
            temp.setEndereco(campoEndereco.getText());
            try {
                temp.setCnh(Long.parseLong(campoCnh.getText()));
            } catch (NumberFormatException e) {
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("O número da CNH deve ser um valor numérico válido.");
                alert.show();
                return; 
            }
            temp.setCategoria(campoCategoria.getText());
            temp.setSetor(campoSetor.getText());

            dao.alterar(temp);
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("As informações do motorista foram alteradas");
            alert.show();
            
            try {
                voltarAoMenu();
            } catch (IOException e) {
                e.printStackTrace(); 
            }   
        }
    }

    @FXML
    private void voltarAoMenu() throws IOException {
        App.setRoot("menu");
    }
}



