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
import modelo.Veiculo;
import modelo.Motorista;
import modelo.UsoVeiculo; 
import util.Dao;

public class RetiradaController {

    @FXML
    private ComboBox<Veiculo> boxVeiculo;

    @FXML
    private ComboBox<Motorista> boxMotorista;

    @FXML
    private DatePicker pickerData;

    private ObservableList<Veiculo> listaVeiculos;
    private ObservableList<Motorista> listaMotoristas;
    private Dao<UsoVeiculo> usoVeiculoDao; 

    @FXML
    private void initialize() {
        Dao<Veiculo> veiculoDao = new Dao<>(Veiculo.class);
        Dao<Motorista> motoristaDao = new Dao<>(Motorista.class);

        List<Veiculo> veiculos = veiculoDao.listarTodos();
        listaVeiculos = FXCollections.observableArrayList(veiculos);
        if (!listaVeiculos.isEmpty()) {
            boxVeiculo.setItems(listaVeiculos);
            boxVeiculo.setValue(listaVeiculos.get(0));
        }

        List<Motorista> motoristas = motoristaDao.listarTodos();
        listaMotoristas = FXCollections.observableArrayList(motoristas);
        if (!listaMotoristas.isEmpty()) {
            boxMotorista.setItems(listaMotoristas);
            boxMotorista.setValue(listaMotoristas.get(0));
        }

        pickerData.setValue(LocalDate.now());

        usoVeiculoDao = new Dao<>(UsoVeiculo.class);
    }

    @FXML
    private void retirarVeiculo() {
        Veiculo veiculoSelecionado = boxVeiculo.getValue();
        Motorista motoristaSelecionado = boxMotorista.getValue();
        LocalDate dataRetirada = pickerData.getValue();

        if (veiculoSelecionado == null || motoristaSelecionado == null || dataRetirada == null) {
            exibirAlertaErro("Erro", "Por favor, selecione um veículo, um motorista e uma data.");
            return;
        }

        if (veiculoEstaEmUso(veiculoSelecionado)) {
            exibirAlertaErro("Erro", "Este veículo já está em uso e não pode ser retirado novamente.");
            return;
        }

        UsoVeiculo usoVeiculo = new UsoVeiculo(veiculoSelecionado, motoristaSelecionado, dataRetirada, null);
        usoVeiculoDao.inserir(usoVeiculo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText("Retirada de veículo registrada com sucesso.");
        alert.show();
    
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

    private void exibirAlertaErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
