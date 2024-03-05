
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
import modelo.Veiculo;
import util.Dao;

public class EditarVeiculoController {@FXML
    private TextField campoMarca; 
    
    @FXML
    private TextField campoModelo; 
    
    @FXML
    private TextField campoPlaca; 
    
    @FXML
    private ComboBox<Veiculo> comboVeiculo;
    
    private ObservableList<Veiculo> listaOb;
    private List<Veiculo> lista;
    private Dao<Veiculo> dao;
    
    @FXML
    private void initialize(){
        dao = new Dao(Veiculo.class);
        lista = dao.listarTodos();
        listaOb = FXCollections.observableArrayList(lista);
        comboVeiculo.setItems(listaOb);
        
        comboVeiculo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            exibirDetalhesVeiculo(newValue);
        });
    }
    
    private void exibirDetalhesVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            campoMarca.setText(veiculo.getMarca());
            campoModelo.setText(veiculo.getModelo());
            campoPlaca.setText(veiculo.getPlaca());
        } else {
            campoMarca.clear();
            campoModelo.clear();
            campoPlaca.clear();
        }
    }
    
    @FXML
    private void editarVeiculo(){ 
        Veiculo temp = comboVeiculo.getSelectionModel().getSelectedItem();
        if (temp != null) {
            temp.setMarca(campoMarca.getText());
            temp.setModelo(campoModelo.getText());
            temp.setPlaca(campoPlaca.getText());
        }
        
        dao.alterar(temp);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("As informações do Veiculo foram alteradas");
        alert.show();
        
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






