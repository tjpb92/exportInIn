package exportinin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exporter dans un fichier Excel la configuration d’InIn
 *
 * @author Thierry Baribaud
 * @version 0.01
 */
public class ExportInIn {

    /**
     * Référence au fichier contenant les données extraites d'InIn
     */
    BufferedReader bufferedReader;

    /**
     * Contructeur principal de la classe
     *
     * @param args argument en ligne commande
     * @throws java.io.FileNotFoundException exception en cas de fichier non
     * trouvé
     * @throws java.io.IOException exception en cas d'erreur en lecture
     */
    public ExportInIn(String[] args) throws FileNotFoundException, IOException, BadlyFormedInInRecordException {
        openInputFile("IAExport.csv");
        processInputFile();
        closeInputFile();
    }

    /**
     * Méthode pour ouvrir le fichier de données
     *
     * @param filename nom du fichier
     * @throws java.io.FileNotFoundException exception en cas de fichier non
     * trouvé
     */
    private void openInputFile(String filename) throws FileNotFoundException {
        System.out.println("Ouverture de " + filename + " ...");
        bufferedReader = new BufferedReader(new FileReader(filename));
        System.out.println("Fichier " + filename + " ouvert.");
    }

    /**
     * Méthode pour traiter le contenu du fichier
     *
     * @throws java.io.IOException exception en cas d'erreur en lecture
     */
    private void processInputFile() throws IOException, BadlyFormedInInRecordException {
        String line;
        int nbRecords;
        InInRecord inInRecord;
        Skill skill;
        Workgroup workgroup;
        Agent agent;
        String objectClass;
        int nbAgents;
        int nbSkills;
        int nbWorkgroups;
        String isKey;
        String valueName;
        String value;
        String path;
        StringTokenizer stringTokenizer;
        String name;

        System.out.println("Traitement du fichier ...");
        nbRecords = 0;
        nbAgents = 0;
        nbSkills = 0;
        nbWorkgroups = 0;
        agent = new Agent();
        skill = new Skill();
        workgroup = new Workgroup();
        while ((line = bufferedReader.readLine()) != null) {
            nbRecords++;
            if ((nbRecords % 10000) == 0) {
                System.out.println(nbRecords + " enregistrements traités ...");
            }
            System.out.println(line);
            inInRecord = new InInRecord(line);
//            System.out.println(inInRecord);
            path = inInRecord.getPath();
            objectClass = inInRecord.getObjectClass();
            valueName = inInRecord.getValueName();
            value = inInRecord.getValue();
            isKey = inInRecord.getIsKey();
            name = "undefined";

//            System.out.println("isKey:" + isKey + ", isKey=1:" + isKey.equals("1"));
            if (isKey.equals("1")) {
                stringTokenizer = new StringTokenizer(path, "\\");
                while (stringTokenizer.hasMoreTokens()) {
                    name = stringTokenizer.nextToken();
                }
                System.out.println(line+", name="+name);
            }

            if ("User".equals(objectClass)) {
                if ("1".equals(isKey)) {
                    nbAgents++;
                    agent = new Agent(name);
                } else {
                    if ("Extension".equals(valueName)) {
                        agent.setExtension(value);
                    } else if ("displayName".equals(valueName)) {
                        agent.setName(value);
                    }
                }
                
            } else if ("Skill".equals(objectClass)) {
                if ("1".equals(isKey)) {
                    nbSkills++;
                    skill = new Skill(name);
                }
            } else if ("Workgroup".equals(objectClass)) {
                if ("1".equals(isKey)) {
                    nbWorkgroups++;
                    workgroup = new Workgroup(name);
                } else {
                    if ("Wrapup Time".equals(valueName)) {
                        workgroup.setWrapup(Integer.parseInt(value));
                    }
                }
            }
        }
        System.out.println(nbAgents + " agent(s) trouvé(s)");
        System.out.println(nbSkills + " compétence(s) trouvé(s)");
        System.out.println(nbWorkgroups + " workgroup(s) trouvé(s)");

        System.out.println(nbRecords + " enregistrements traités.");
        System.out.println("Fichier traité.");
    }

    /**
     * Méthode pour fermer le fichier de données
     *
     * @throws java.io.IOException exception en cas d'erreur de fermeture
     */
    private void closeInputFile() throws IOException {
        System.out.println("Fermeture du fichier ...");
        bufferedReader.close();
        System.out.println("Fichier fermé.");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Skill skill;
        Workgroup workgroup;
        Agent agent;
        InInRecord record;
        ExportInIn exportInIn;

        skill = new Skill("Antargaz");
        System.out.println(skill);

        workgroup = new Workgroup("A_Antargaz");
        System.out.println(workgroup);

        agent = new Agent("thierry.baribaud");
        agent.setExtension("7504");
        agent.setName("Thierry Baribaud");
        System.out.println(agent);

//        try {
//            record = new InInRecord("\"\\Courbevoie\\Production\\Users\\alexandra.nuiro\",\"User\",\"0\",\"displayName\",\"Alexandra NUIRO\",\"0\"");
//            System.out.println(record);
//        } catch (BadlyFormedInInRecordException ex) {
//            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, ex);
//        }

        System.out.println("Lancement de ExportInIn ...");
        try {
            exportInIn = new ExportInIn(args);
        } catch (Exception exception) {
            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, exception);
        }
        System.out.println("Fin de ExportInIn");
    }

}
