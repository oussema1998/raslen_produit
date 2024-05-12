package View;

import Entities.Produit;
import Services.IService;
import Services.ProduitService;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexProduitController {

    @FXML
    private TableView<Produit> produitTable;

    @FXML
    private TableColumn<Produit, Integer> idColumn;

    @FXML
    private TableColumn<Produit, String> designationColumn;

    @FXML
    private TableColumn<Produit, Double> prixAchatColumn;

    @FXML
    private TableColumn<Produit, Double> prixVenteColumn;

    @FXML
    private TableColumn<Produit, Integer> stockColumn;

    @FXML
    private Label labelID;

    @FXML
    private Label labelDesignation;

    @FXML
    private Label labelPrixAchat;

    @FXML
    private Label labelPrixVente;

    @FXML
    private Label labelStock;

    private ObservableList<Produit> produitService = FXCollections.observableArrayList();
    private IService<Produit> produitIService = new ProduitService();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        designationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesignation()));
        prixAchatColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix_achat()).asObject());
        prixVenteColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrix_vente()).asObject());
        stockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());

        showProduitDetails(null);

        try {
            produitService.addAll(produitIService.readAll());
            produitTable.setItems(produitService);
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Failed to load produits", e.getMessage());
        }

        produitTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProduitDetails(newValue));
    }

    private void showProduitDetails(Produit produit) {
        if (produit != null) {
            labelID.setText(String.valueOf(produit.getId()));
            labelDesignation.setText(produit.getDesignation());
            labelPrixAchat.setText(String.valueOf(produit.getPrix_achat()));
            labelPrixVente.setText(String.valueOf(produit.getPrix_vente()));
            labelStock.setText(String.valueOf(produit.getStock()));
        } else {
            labelID.setText("");
            labelDesignation.setText("");
            labelPrixAchat.setText("");
            labelPrixVente.setText("");
            labelStock.setText("");
        }
    }



    @FXML
    public void refreshTableView() {
        try {
            produitService.clear();
            produitService.addAll(produitIService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
            showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to fetch produits", e.getMessage());
        }
    }
    @FXML
    private void createProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-produit.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Add Produit");
            stage.setScene(scene);

            View.AddProduitController controller = loader.getController();
            controller.setIndexProduitController(this);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateProduit(ActionEvent event) {
        Produit selectedProduit = produitTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/update-produit.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);

                Stage stage = new Stage();
                stage.setTitle("Update Produit");
                stage.setScene(scene);

                UpdateProduitController controller = loader.getController();
                controller.setIndexProduitController(this);
                controller.setSelectedProduit(selectedProduit);

                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Produit Selected!", "Please select a produit in the table!");
        }
    }


    @FXML
    private void handleDeleteProduit() {
        Produit selectedProduit = produitTable.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                produitIService.delete(selectedProduit.getId());
                refreshTableView();
            } catch (Exception e) {
                e.printStackTrace();
                showAlertDialog(Alert.AlertType.ERROR, "Database Error", "Failed to delete produit", e.getMessage());
            }
        } else {
            showAlertDialog(Alert.AlertType.WARNING, "No Selection", "No Produit Selected!", "Please select a produit in the table!");
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
