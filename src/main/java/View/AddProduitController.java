package View;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Entities.Produit;
import Services.ProduitService;
import Services.IService;

public class AddProduitController {
    @FXML
    private TextField designationTextField;

    @FXML
    private TextField prixAchatTextField;

    @FXML
    private TextField prixVenteTextField;

    @FXML
    private TextField stockTextField;

    private IService<Produit> produitService = new ProduitService();

    private IndexProduitController indexProduitController;

    public void setIndexProduitController(IndexProduitController controller) {
        this.indexProduitController = controller;
    }

    @FXML
    private void initialize() {
        setIndexProduitController(indexProduitController);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (designationTextField.getText() == null || designationTextField.getText().isEmpty()) {
            errorMessage += "Designation field is empty!\n";
        }

        try {
            double prixAchat = Double.parseDouble(prixAchatTextField.getText());
            if (prixAchat <= 0) {
                errorMessage += "Prix d'achat must be greater than 0!\n";
            }
        } catch (NumberFormatException e) {
            errorMessage += "Prix d'achat must be a valid number!\n";
        }

        try {
            double prixVente = Double.parseDouble(prixVenteTextField.getText());
            if (prixVente <= 0) {
                errorMessage += "Prix de vente must be greater than 0!\n";
            }
        } catch (NumberFormatException e) {
            errorMessage += "Prix de vente must be a valid number!\n";
        }

        try {
            int stock = Integer.parseInt(stockTextField.getText());
            if (stock < 0) {
                errorMessage += "Stock must be greater than or equal to 0!\n";
            }
        } catch (NumberFormatException e) {
            errorMessage += "Stock must be a valid integer!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "Validation Error", errorMessage);
            return false;
        }
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            // Récupérer les données des champs
            String designation = designationTextField.getText();
            double prixAchat = Double.parseDouble(prixAchatTextField.getText());
            double prixVente = Double.parseDouble(prixVenteTextField.getText());
            int stock = Integer.parseInt(stockTextField.getText());

            // Créer un nouvel objet Produit
            Produit newProduit = new Produit(prixAchat, prixVente, stock, designation);

            // Insérer le nouveau produit dans la base de données
            produitService.insert(newProduit);

            // Rafraîchir le tableau des produits dans l'indexProduitController
            if (indexProduitController != null) {
                indexProduitController.refreshTableView();
            }

            // Fermer la fenêtre d'ajout de produit
            Stage stage = (Stage) designationTextField.getScene().getWindow();
            stage.close();
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
