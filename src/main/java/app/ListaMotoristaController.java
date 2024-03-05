
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
import modelo.Motorista;
import javafx.fxml.Initializable;
import util.Dao;

public class ListaMotoristaController implements Initializable{
    
    @FXML
    private TableView<Motorista> tabela;
    
    @FXML
    private TableColumn<Motorista, String> listarNome;
    
    @FXML
    private TableColumn<Motorista, String> listarEndereco;
    
    @FXML
    private TableColumn<Motorista, String> listarSetor;
    
    @FXML
    private TableColumn<Motorista, String> listarCategoria;
    
    @FXML
    private TableColumn<Motorista, Long> listarCNH;

    private List<Motorista> motorista;
    private ObservableList<Motorista> listarMotorista;
    
    
    public void initialize(URL location, ResourceBundle resources){
        Dao<Motorista> daoMotorista = new Dao(Motorista.class);
        
        motorista = daoMotorista.listarTodos();
        listarMotorista = FXCollections.observableArrayList(motorista);
        
        listarNome.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        listarEndereco.setCellValueFactory(
                new PropertyValueFactory<>("endereco"));
        listarSetor.setCellValueFactory(
                new PropertyValueFactory<>("setor"));
        listarCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria"));
        listarCNH.setCellValueFactory(
                new PropertyValueFactory<>("cnh"));
        tabela.setItems(listarMotorista);
        
        tabela.sort();
        
    }
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menu");
    }
    
}





