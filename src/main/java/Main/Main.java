package Main;

import Entities.Produit;
import Services.ProduitService;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        Produit p1 = new Produit(11.5,21.5,"ddd");

        ProduitService ps = new ProduitService();

        ps.insert(p1);
        
        ArrayList<Produit> list = ps.readAll();

       for(Produit i:list)
       {
        System.out.println(i.toString());
       }
        
        
        
    }
    
}
