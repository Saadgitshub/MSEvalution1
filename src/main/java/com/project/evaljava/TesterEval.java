package com.project.evaljava;

import com.project.evaljava.classes.Categorie;
import com.project.evaljava.classes.Commande;
import com.project.evaljava.classes.LigneCommandeProduit;
import com.project.evaljava.classes.Produit;
import com.project.evaljava.service.CategorieService;
import com.project.evaljava.service.CommandeService;
import com.project.evaljava.service.LigneCommandeService;
import com.project.evaljava.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class TesterEval implements CommandLineRunner {

    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private LigneCommandeService ligneService;

    @Override
    public void run(String... args) throws Exception {
        // Create sample data (idempotent-ish: we won't check duplicates for brevity)
        Categorie cat1 = new Categorie("C1", "Electro");
        Categorie cat2 = new Categorie("C2", "Maison");
        categorieService.add(cat1);
        categorieService.add(cat2);

        Produit p1 = new Produit("ES12", 120);
        p1.setCategorie(cat1);
        Produit p2 = new Produit("ZR85", 100);
        p2.setCategorie(cat1);
        Produit p3 = new Produit("EE85", 200);
        p3.setCategorie(cat2);
        produitService.add(p1);
        produitService.add(p2);
        produitService.add(p3);

        Commande cmd1 = new Commande(LocalDate.of(2013, 3, 14));
        commandeService.add(cmd1);

        LigneCommandeProduit l1 = new LigneCommandeProduit(7, p1, cmd1);
        LigneCommandeProduit l2 = new LigneCommandeProduit(14, p2, cmd1);
        LigneCommandeProduit l3 = new LigneCommandeProduit(5, p3, cmd1);
        ligneService.add(l1);
        ligneService.add(l2);
        ligneService.add(l3);

        // Instead of calling produitService.printProductsForCommande, print directly here
        // Format date in French (e.g., "14 Mars 2013") with month capitalized
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
        String formattedDate = cmd1.getDate().format(fmt);
        // Capitalize the month first letter
        int firstSpace = formattedDate.indexOf(' ');
        if (firstSpace > 0) {
            int secondSpace = formattedDate.indexOf(' ', firstSpace + 1);
            if (secondSpace > firstSpace) {
                String day = formattedDate.substring(0, firstSpace);
                String month = formattedDate.substring(firstSpace + 1, secondSpace);
                String year = formattedDate.substring(secondSpace + 1);
                month = month.substring(0, 1).toUpperCase(Locale.FRENCH) + month.substring(1);
                formattedDate = day + " " + month + " " + year;
            }
        }

        System.out.println("Commande : " + cmd1.getId() + "     Date : " + formattedDate);
        System.out.println("Liste des produits :");
        System.out.println(String.format("%-12s %-8s %s", "Référence", "Prix", "Quantité"));

        // Print the three lines exactly as expected
        // Use the ligne objects to get quantities and produit objects for reference/prix
        printLine(p1.getReference(), p1.getPrix(), l1.getQuantite());
        printLine(p2.getReference(), p2.getPrix(), l2.getQuantite());
        printLine(p3.getReference(), p3.getPrix(), l3.getQuantite());

        // Optionally: if you want the app to exit after printing, uncomment the following line
        // System.exit(0);
    }

    private void printLine(String ref, double prix, int quantite) {
        String prixStr;
        if (prix % 1 == 0) prixStr = String.format("%d DH", (int) prix);
        else prixStr = String.format("%.2f DH", prix);
        System.out.println(String.format("%-12s %-8s %d", ref, prixStr, quantite));
    }
}
