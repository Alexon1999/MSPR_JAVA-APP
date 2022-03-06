package fr.generator.utils;

import com.google.gson.Gson;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import fr.generator.html.Formatter;
import fr.generator.model.Employe;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static j2html.TagCreator.*;

public class Utilitaire {

    private static final Logger logger = LoggerFactory.getLogger(Utilitaire.class);

    public static void CreateAgentsListPage(List<Employe> lesEmployes) throws IOException {
        String Content = div(attrs("#employees"),
                h1("Liste d'employés : "),
                each(lesEmployes, employee ->
                        div(attrs(".employee"),
                                a().withHref("/" + employee.getNomHtml()).with(
                                        h2(employee.getNom() + " " + employee.getPrenom()))
                        )
                )
        ).render();

        FileWriter agents = new FileWriter("./web/agents.html");
        agents.write(Formatter.toHtml("./templates/agents.html", "Agents", Content));
        agents.close();
    }

    public static void CreateEachAgentsPage(List<Employe> lesEmployes) throws IOException{
        for(Employe employe : lesEmployes){
            FileWriter file = new FileWriter("./web/" + employe.getNomHtml());
            String content = div(attrs("#employe"),
                    h1(employe.getNom() + " " + employe.getPrenom()),
                    h2("Les matériels empruntés : "),
                    each(employe.getMaterielsEmpruntes(), materiel ->
                            div(attrs(".materiel"),
                                    h3(materiel.getLabel())
                            )
                    )
            ).render();

            file.write(Formatter.toHtml("./templates/details.html", employe.getNom(), content));
            file.close();
        }
    }

    // Pebble Template Engine (moteur de template)
    // https://pebbletemplates.io/
    public static void CreateAgentsListPageByTemplate(List<Employe> lesEmployes) throws IOException{
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/agents.html");

        Map<String, Object> context = new HashMap<>();
        context.put("employes", lesEmployes);

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String content = writer.toString();
        FileWriter agents = new FileWriter("./web/agents.html");
        agents.write(content);
        agents.close();
        logger.info("agent list page successfully created");
    }

    public static void CreateEachAgentsPageByTemplate(List<Employe> lesEmployes) throws IOException{
        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("templates/details_agent.html");
        for(Employe employe : lesEmployes){
            FileWriter file = new FileWriter("./web/" + employe.getNomHtml());
            Map<String, Object> context = new HashMap<>();
            context.put("employe", employe);

            Writer writer = new StringWriter();
            compiledTemplate.evaluate(writer, context);
            String content = writer.toString();
            file.write(content);
            file.close();
        }
        logger.info("agent details page successfully created");
    }

    public static void CreateHTPASSWD(List<Employe> lesEmployes) throws IOException{
        StringBuilder content = new StringBuilder();
        for(Employe employe : lesEmployes){
            String encryptedPassword = Md5Crypt.md5Crypt(employe.getMdp().getBytes());
            content.append(employe.getUserName()).append(":").append(encryptedPassword).append("\n");
        }
        FileWriter file = new FileWriter("./web/.htpasswd");
        file.write(String.valueOf(content));
        file.close();
        logger.info(".htpasswd successfully created");
    }

    public static String pojoToJson(Object obj){
        return new Gson().toJson(obj);
    }

    public static void createJSONEmployes(List<Employe> lesEmployes) throws IOException{
        FileWriter file = new FileWriter("./web/data.json");
        file.write(pojoToJson(lesEmployes));
        file.close();
        logger.info("agents into json file successfully created");
    }
}
