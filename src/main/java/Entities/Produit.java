package Entities;

public class Produit {

    int id;
    double prix_achat,prix_vente;
    int stock;
    String designation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(double prix_achat) {
        this.prix_achat = prix_achat;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Produit(int id, double prix_achat, double prix_vente, int stock, String designation) {
        this.id = id;
        this.prix_achat = prix_achat;
        this.prix_vente = prix_vente;
        this.stock = stock;
        this.designation = designation;
    }

    public Produit(double prix_achat, double prix_vente, int stock, String designation) {
        this.prix_achat = prix_achat;
        this.prix_vente = prix_vente;
        this.stock = stock;
        this.designation = designation;
    }

    public Produit(double prix_achat, double prix_vente, String designation) {
        this.prix_achat = prix_achat;
        this.prix_vente = prix_vente;
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", prix_achat=" + prix_achat +
                ", prix_vente=" + prix_vente +
                ", stock=" + stock +
                ", designation='" + designation + '\'' +
                '}';
    }
}
