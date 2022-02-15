package fr.generator;

import fr.generator.model.Employe;
import fr.generator.model.Materiel;
import fr.generator.utils.Utilitaire;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Start Generating HTML Pages ..... 🤞");

        List<Materiel> lesMateriels = Materiel.getLesMaterielsParFichier("./db/liste.txt");

        List<Employe> lesEmployes = Employe.getLesEmployesParFichier("./db/", "staff.txt", lesMateriels);
        for(Employe e : lesEmployes){
            System.out.println(e);
        }

        Utilitaire.CreateAgentsListPageByTemplate(lesEmployes);
        Utilitaire.CreateEachAgentsPageByTemplate(lesEmployes);
        Utilitaire.CreateHTPASSWD(lesEmployes);
        Utilitaire.createJSONEmployes(lesEmployes);

        System.out.println("Finish 😀");
    }
}