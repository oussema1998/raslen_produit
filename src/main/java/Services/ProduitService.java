package Services;

import Entities.Produit;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitService implements IService<Produit> {
    private Connection conn;

    public ProduitService() {
        conn = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Produit p) {
        String requete = "INSERT INTO produit (prix_achat, prix_vente, stock, designation) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setDouble(1, p.getPrix_achat());
            pst.setDouble(2, p.getPrix_vente());
            pst.setInt(3, p.getStock());
            pst.setString(4, p.getDesignation());
            pst.executeUpdate();
            System.out.println("Produit ajouté avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Produit p) {
        String requete = "UPDATE produit SET prix_achat=?, prix_vente=?, stock=?, designation=? WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setDouble(1, p.getPrix_achat());
            pst.setDouble(2, p.getPrix_vente());
            pst.setInt(3, p.getStock());
            pst.setString(4, p.getDesignation());
            pst.setInt(5, p.getId());
            pst.executeUpdate();
            System.out.println("Produit mis à jour avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM produit WHERE id=?";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Produit readById(int id) {
        String requete = "SELECT * FROM produit WHERE id=?";
        Produit p = null;
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                p = new Produit(
                        rs.getInt("id"),
                        rs.getDouble("prix_achat"),
                        rs.getDouble("prix_vente"),
                        rs.getInt("stock"),
                        rs.getString("designation")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public ArrayList<Produit> readAll() {
        String requete = "SELECT * FROM produit";
        ArrayList<Produit> list = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Produit p = new Produit(
                        rs.getInt("id"),
                        rs.getDouble("prix_achat"),
                        rs.getDouble("prix_vente"),
                        rs.getInt("stock"),
                        rs.getString("designation")
                );
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
