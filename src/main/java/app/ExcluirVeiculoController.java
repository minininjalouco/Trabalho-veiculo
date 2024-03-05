package app;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import modelo.Veiculo;
import modelo.UsoVeiculo;
import util.Dao;
import util.ExclusaoException;

public class ExcluirVeiculoController {
    
    @FXML
    private ComboBox<Veiculo> comboVeiculo;

    private ObservableList<Veiculo> listaOb;
    private List<Veiculo> lista;
    private Dao<Veiculo> dao;
    private Dao<UsoVeiculo> usoVeiculoDao;

    @FXML
    private void initialize() {
        dao = new Dao<>(Veiculo.class);
        lista = dao.listarTodos();
        listaOb = FXCollections.observableArrayList(lista);
        comboVeiculo.setItems(listaOb);
        
        usoVeiculoDao = new Dao<>(UsoVeiculo.class);
    }

    @FXML
    private void excluirVeiculo() {
        Veiculo temp = comboVeiculo.getSelectionModel().getSelectedItem();
        
        if (temp != null) { 
            if (!veiculoEstaEmUso(temp)) {
                try {
                    dao.excluir(temp); 
                    
                    
                    listaOb.remove(temp);
                    comboVeiculo.setItems(listaOb);
                } catch (ExclusaoException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veículo não pode ser excluído");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veículo está em uso e não pode ser excluído");
                alert.show();
            }  
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nenhum veículo selecionado");
            alert.show();
        }
        
        try {
            voltarAoMenu();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    private boolean veiculoEstaEmUso(Veiculo veiculo) {
        List<UsoVeiculo> registros = usoVeiculoDao.listarTodos();
        for (UsoVeiculo usoVeiculo : registros) {
            if (usoVeiculo.getVeiculo().equals(veiculo) && usoVeiculo.getDevolucao() == null) {
                return true;
            }
        }
        return false; 
    }

    @FXML
    private void voltarAoMenu() throws IOException {
        App.setRoot("menu");
    }
}
