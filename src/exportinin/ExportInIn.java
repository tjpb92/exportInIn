package exportinin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PaperSize;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Exporter dans un fichier Excel la configuration d’InIn
 *
 * @author Thierry Baribaud
 * @version 0.07
 */
public class ExportInIn {

    /**
     * Référence au fichier contenant les données extraites d'InIn
     */
    private BufferedReader bufferedReader;

    /**
     * Liste des agents
     */
    private Hashtable<String, Agent> agents;

    /**
     * Liste des compétences
     */
    private Hashtable<String, Skill> skills;

    /**
     * Liste des workgroups
     */
    private Hashtable<String, Workgroup> workgroups;

    /**
     * Liste des entrées Attendant
     */
    private Hashtable<String, AttendantEntry> attendantEntries;

    /**
     * Fichier Excel recevant les résultats
     */
    private XSSFWorkbook classeur;

    /**
     * Feuillet pour les agents
     */
    private XSSFSheet agentsWorksheet;

    /**
     * Feuillet pour les compétences
     */
    private XSSFSheet skillsWorksheet;

    /**
     * Feuillet pour les groupes de travail
     */
    private XSSFSheet workgroupsWorksheet;

    /**
     * Feuillet pour les entrées Attendant
     */
    private XSSFSheet attendantEntriesWorksheet;

    /**
     * Contructeur principal de la classe
     *
     * @param args argument en ligne commande
     * @throws java.io.FileNotFoundException exception en cas de fichier non
     * trouvé
     * @throws java.io.IOException exception en cas d'erreur en lecture
     */
    public ExportInIn(String[] args) throws FileNotFoundException, IOException {
        openInputFile("IAExport.csv");
        processInputFile();
        closeInputFile();

        openOutputFile();
        processOutputFile();
        closeOutputFile("IAExport.xlsx");
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
    private void processInputFile() throws IOException {
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
        int nbAttendantEntries;
        String isKey;
        String valueName;
        String value;
        String path;
        StringTokenizer stringTokenizer;
        String name;
        String previousName;
        Enumeration enumAgents;
        Enumeration enumSkills;
        Enumeration enumWorkgroups;
        Enumeration enumAttendantEntries;
        String aSkill;
        String[] buffer;
        AgentSkill agentSkill;
        AttendantEntry attendantEntry;
        boolean attendantSeen = false;

        System.out.println("Traitement du fichier ...");
        agents = new Hashtable<>();
        skills = new Hashtable<>();
        workgroups = new Hashtable<>();
        attendantEntries = new Hashtable<>();
        nbRecords = 0;
        nbAgents = 0;
        nbSkills = 0;
        nbWorkgroups = 0;
        nbAttendantEntries = 0;
        agent = new Agent();
        skill = new Skill();
        workgroup = new Workgroup();
        agentSkill = new AgentSkill();
        attendantEntry = new AttendantEntry();
        name = "undefined";
        previousName = "undefined";
        while ((line = bufferedReader.readLine()) != null) {
            nbRecords++;
            if ((nbRecords % 10000) == 0) {
                System.out.println(nbRecords + " enregistrements traités ...");
            }
//            System.out.println(line);
            try {
                inInRecord = new InInRecord(line);
                //            System.out.println(inInRecord);
                path = inInRecord.getPath();
                objectClass = inInRecord.getObjectClass();
                valueName = inInRecord.getValueName();
                value = inInRecord.getValue();
                isKey = inInRecord.getIsKey();
//                name = "undefined";
//                previousName = "undefined";

                //            System.out.println("isKey:" + isKey + ", isKey=1:" + isKey.equals("1"));
                if (isKey.equals("1")) {
                    stringTokenizer = new StringTokenizer(path, "\\");
                    while (stringTokenizer.hasMoreTokens()) {
                        previousName = name;
                        name = stringTokenizer.nextToken();
                    }
//                    System.out.println(line+", name="+name);
                }

                if ("User".equals(objectClass)) {
                    if ("1".equals(isKey)) {
                        nbAgents++;
                        agent = new Agent(name);
                        agents.put(name, agent);
                    } else {
                        if (null != valueName) {
                            switch (valueName) {
                                case "Extension":
                                    agent.setExtension(value);
                                    agents.put(name, agent);
                                    break;
                                case "surname":
                                    agent.setLastname(value);
                                    agents.put(name, agent);
                                    break;
                                case "givenName":
                                    agent.setFirstname(value);
                                    agents.put(name, agent);
                                    break;
                                case "displayName":
                                    agent.setName(value);
                                    agents.put(name, agent);
                                    break;
                                case "Role":
                                    agent.setRole(value);
                                    agents.put(name, agent);
                                    break;
                                case "Skills":
//                                    buffer = value.split("\\|");
//                                    agent.addSkill(buffer[0]);
                                    agentSkill = new AgentSkill(value);
                                    agent.addSkill(agentSkill);
                                    agents.put(name, agent);
                                    break;
                                case "Workgroups":
//                                System.out.println("Workgroup : name=" + name + ", value=" + value);
                                    agent.addWorkgroup(value);
                                    agents.put(name, agent);
                                    break;
                            }
                        }
                    }

                } else if ("Skill".equals(objectClass)) {
                    if ("1".equals(isKey)) {
                        nbSkills++;
                        skill = new Skill(name);
//                        System.out.println(attendantEntry + ", name=" + name);
                        skills.put(name, skill);
                    }
                } else if ("Workgroup".equals(objectClass)) {
                    if ("1".equals(isKey)) {
                        nbWorkgroups++;
                        workgroup = new Workgroup(name);
                        workgroups.put(name, workgroup);
                    } else {
                        if ("Wrapup Time".equals(valueName)) {
                            workgroup.setWrapup(Integer.parseInt(value));
                            workgroups.put(name, workgroup);
                        }
                    }
                } else if ("Attendant".equals(objectClass)) {
//                    System.out.println("  name=" + name + ", " + inInRecord);
                    if ("1".equals(isKey)) {
                        attendantSeen = "Attendant".equals(name);
                    }
                    if ("ProfilePaths".equals(valueName) && attendantSeen) {
                        nbAttendantEntries++;
                        attendantEntry = new AttendantEntry(value);
                        System.out.println(attendantEntry + ", name=" + value);
                        attendantEntries.put(attendantEntry.getId(), attendantEntry);
                    }
                } else if ("Profile".equals(objectClass) && "Attendant".equals(previousName)) {
//                    System.out.println("  name=" + name + ", previousName=" + previousName + ", " + inInRecord);
                    if ("1".equals(isKey)) {
                        attendantEntry = attendantEntries.get(name);
//                        System.out.println("    attendantEntry=" + attendantEntry);
                    }                    
                    if ("DNISString".equals(valueName)){
                        attendantEntry.setSdaList(value);
//                        System.out.println("    sdaList=" + attendantEntry.getSdaList());
                        attendantEntries.replace(attendantEntry.getId(), attendantEntry);
                    }
                }
            } catch (BadlyFormedInInRecordException | NumberFormatException exception) {
                System.out.println("ERREUR : enregistrement mal formatté");
            }
        }
        System.out.println(nbAgents + " agent(s) trouvé(s)");
//        enumAgents = agents.elements();
//        while (enumAgents.hasMoreElements()) {
//            System.out.println(enumAgents.nextElement());
//        }
        System.out.println(nbSkills + " compétence(s) trouvé(s)");
//        enumAttendantEntries = skills.elements();
//        while (enumAttendantEntries.hasMoreElements()) {
//            System.out.println(enumAttendantEntries.nextElement());
//        }
        System.out.println(nbWorkgroups + " workgroup(s) trouvé(s)");
//        enumWorkgroups = workgroups.elements();
//        while (enumWorkgroups.hasMoreElements()) {
//            System.out.println(enumWorkgroups.nextElement());
//        }
        System.out.println(nbAttendantEntries + " entrée(s) Attendant trouvée(s)");
//        enumAttendantEntries = attendantEntries.elements();
//        while (enumAttendantEntries.hasMoreElements()) {
//            System.out.println(enumAttendantEntries.nextElement());
//        }

        System.out.println(nbRecords + " enregistrements traités.");
        System.out.println("Fichier traité.");
    }

    /**
     * Méthode pour ouvrir le fichier Excel contenant les résultats
     */
    private void openOutputFile() {
        classeur = new XSSFWorkbook();
        agentsWorksheet = classeur.createSheet("Utilisateurs");
        skillsWorksheet = classeur.createSheet("Compétences");
        workgroupsWorksheet = classeur.createSheet("Groupes");
        attendantEntriesWorksheet = classeur.createSheet("Entrées_Attendant");
    }

    /**
     * Méthode pour générer le fichier Excel
     */
    private void processOutputFile() {
        XSSFCellStyle titleStyle;
        XSSFCellStyle titleStyle2;

        // Style pour les titres
        titleStyle = classeur.createCellStyle();
        titleStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.LESS_DOTS);

        titleStyle2 = (XSSFCellStyle) titleStyle.clone();
        titleStyle2.setRotation((short) 90);
//        titleStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

        addAgentsToExcel(titleStyle, titleStyle2);
        addSkillsToExcel(titleStyle);
        addWorkgroupsToExcel(titleStyle);
        addAttendantEntriesToExcel(titleStyle);
    }

    /**
     * Méthode pour ajouter les utilisateurs dans le fichier Excel
     */
    private void addAgentsToExcel(XSSFCellStyle titleStyle, XSSFCellStyle titleStyle2) {
        Enumeration enumAgents;
        XSSFRow titre;
        XSSFCell cell;
        XSSFRow ligne;
        int i;
        Agent agent;
        int skillFirstColumn;
        int workgroupFirstColumn;
        short column;
        Enumeration enumSkills;
        Skill skill;
        int nbSkills;
        Enumeration enumWorkgroups;
        Workgroup workgroup;
        int nbWorkgroups;
        ArrayList<AgentSkill> agentSkills;
        ArrayList<String> indexedSkills;
        ArrayList<String> agentWorkgroups;
        ArrayList<String> indexedWorkgroups;
        short skillColumn;
        short workgroupColumn;
        int nbColumns;
        StringBuffer aString;

        // Ligne de titre
        titre = agentsWorksheet.createRow(0);

        column = 0;
        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Id");

        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Nom");

        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Prénom");

        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Nom complet");

        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Extension");

        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Rôle");

        skillFirstColumn = column;
        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle2);
        cell.setCellValue("Compétences");

        enumSkills = skills.elements();
        nbSkills = 0;
        indexedSkills = new ArrayList<>();
        while (enumSkills.hasMoreElements()) {
            nbSkills++;
            cell = titre.createCell(column++);
            cell.setCellStyle(titleStyle2);
            skill = (Skill) enumSkills.nextElement();
            cell.setCellValue(skill.getName());
            indexedSkills.add(skill.getName());
        }

        workgroupFirstColumn = column;
        cell = titre.createCell(column++);
        cell.setCellStyle(titleStyle2);
        cell.setCellValue("Groupes de traitement");

        enumWorkgroups = workgroups.elements();
        nbWorkgroups = 0;
        indexedWorkgroups = new ArrayList<>();
        while (enumWorkgroups.hasMoreElements()) {
            nbWorkgroups++;
            cell = titre.createCell(column++);
            cell.setCellStyle(titleStyle2);
            workgroup = (Workgroup) enumWorkgroups.nextElement();
            cell.setCellValue(workgroup.getName());
            indexedWorkgroups.add(workgroup.getName());
        }
        nbColumns = column;

        enumAgents = agents.elements();
        i = 0;
        while (enumAgents.hasMoreElements()) {
            i++;
            agent = (Agent) enumAgents.nextElement();

            ligne = agentsWorksheet.createRow(i);
            column = 0;

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getId());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getLastname());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getFirstname());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getName());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getExtension());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(column++);
            cell.setCellValue(agent.getRole());
//            cell.setCellStyle(cellStyle);

            column++;
//            cell = ligne.createCell(column++);
//            cell.setCellValue(String.join(",", agent.getSkills()));
//            cell.setCellStyle(cellStyle);

            agentSkills = agent.getSkills();
            for (AgentSkill agentSkill : agentSkills) {
                skillColumn = (short) indexedSkills.indexOf(agentSkill.getSkill());
                if (skillColumn >= 0) {
                    skillColumn = (short) (skillColumn + skillFirstColumn + 1);
                    cell = ligne.createCell(skillColumn);
//                    cell.setCellValue("X");
                    aString = new StringBuffer();
                    aString.append(agentSkill.getLevel());
//                    System.out.println("attendantEntry:"+agentSkill.getLevel());
                    if (agentSkill.getDesireToUse() > 0) {
                        aString.append("/").append(agentSkill.getDesireToUse());
                    }
                    cell.setCellValue(aString.toString());
//                    cell.setCellStyle(cellStyle);
                }
            }

            column += nbSkills;

            column++;
//            cell = ligne.createCell(column++);
//            cell.setCellValue(String.join(",", agent.getWorkgroups()));
//            cell.setCellStyle(cellStyle);
            agentWorkgroups = agent.getWorkgroups();
            for (String agentWorkgroup : agentWorkgroups) {
                workgroupColumn = (short) indexedWorkgroups.indexOf(agentWorkgroup);
                if (workgroupColumn >= 0) {
                    workgroupColumn = (short) (workgroupColumn + workgroupFirstColumn + 1);
                    cell = ligne.createCell(workgroupColumn);
                    cell.setCellValue("X");
//                    cell.setCellStyle(cellStyle);
                }
            }

            column += nbWorkgroups;

            System.out.println(agent);
        }

//        System.out.println("nbColumns=" + nbColumns);
        finalizeWorksheet(agentsWorksheet, nbColumns, "Liste des utilisateurs", true);

        // Largeur des deux dernières colonnes fixées à 50 = 12 800 / 256
//            agentsWorksheet.setColumnWidth((int)skillFirstColumn, (int)12800);
//            agentsWorksheet.setColumnWidth((int)workgroupFirstColumn, (int)12800);
    }

    /**
     * Méthode pour ajouter les comptétences dans le fichier Excel
     */
    private void addSkillsToExcel(XSSFCellStyle titleStyle) {
        Enumeration enumSkills;
        XSSFRow titre;
        XSSFCell cell;
        XSSFRow ligne;
        int i;
        Skill skill;

        // Ligne de titre
        titre = skillsWorksheet.createRow(0);
        cell = titre.createCell((short) 0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Nom");

        enumSkills = skills.elements();
        i = 0;
        while (enumSkills.hasMoreElements()) {
            i++;
            skill = (Skill) enumSkills.nextElement();

            ligne = skillsWorksheet.createRow(i);

            cell = ligne.createCell(0);
            cell.setCellValue(skill.getName());
//            cell.setCellStyle(cellStyle);

            System.out.println(skill);
        }

        finalizeWorksheet(skillsWorksheet, 1, "Liste des compétences", false);
    }

    /**
     * Méthode pour ajouter les groupes de traitement dans le fichier Excel
     */
    private void addWorkgroupsToExcel(XSSFCellStyle titleStyle) {
        Enumeration enumWorkgroups;
        XSSFRow titre;
        XSSFCell cell;
        XSSFRow ligne;
        int i;
        Workgroup workgroup;

        // Ligne de titre
        titre = workgroupsWorksheet.createRow(0);
        cell = titre.createCell((short) 0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Nom");
        cell = titre.createCell((short) 1);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Wrap-up");

        enumWorkgroups = workgroups.elements();
        i = 0;
        while (enumWorkgroups.hasMoreElements()) {
            i++;
            workgroup = (Workgroup) enumWorkgroups.nextElement();

            ligne = workgroupsWorksheet.createRow(i);

            cell = ligne.createCell(0);
            cell.setCellValue(workgroup.getName());
//            cell.setCellStyle(cellStyle);

            cell = ligne.createCell(1);
            cell.setCellValue(workgroup.getWrapup());
//            cell.setCellStyle(cellStyle);

            System.out.println(workgroup);
        }

        finalizeWorksheet(workgroupsWorksheet, 2, "Liste des groupes de traitement", false);
    }

    /**
     * Méthode pour ajouter les entrées Attendant dans le fichier Excel
     */
    private void addAttendantEntriesToExcel(XSSFCellStyle titleStyle) {
        Enumeration enumAttendantEntries;
        XSSFRow titre;
        XSSFCell cell;
        XSSFRow ligne;
        int i;
        AttendantEntry attendantEntry;
        String sdaList;

        // Ligne de titre
        titre = attendantEntriesWorksheet.createRow(0);
        cell = titre.createCell((short) 0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Nom");
        cell = titre.createCell((short) 1);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("Id");
        cell = titre.createCell((short) 2);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("SDAs");

        enumAttendantEntries = attendantEntries.elements();
        i = 0;
        while (enumAttendantEntries.hasMoreElements()) {
            i++;
            attendantEntry = (AttendantEntry) enumAttendantEntries.nextElement();

            ligne = attendantEntriesWorksheet.createRow(i);

            cell = ligne.createCell(0);
            cell.setCellValue(attendantEntry.getName());
//            cell.setCellStyle(cellStyle);
            cell = ligne.createCell(1);
            cell.setCellValue(attendantEntry.getId());
            
            cell = ligne.createCell(2);
            if ((sdaList=attendantEntry.getSdaList())!=null) {
                cell.setCellValue(sdaList);
            }

            System.out.println(attendantEntry);
        }

        finalizeWorksheet(attendantEntriesWorksheet, 3, "Liste des entrées Attendant", false);
    }

    /**
     * Méthode pour fermer le fichier Excel
     */
    private void closeOutputFile(String filename) {
        FileOutputStream out;

        try {
            out = new FileOutputStream(new File(filename));
            classeur.write(out);
            out.close();
            System.out.println("Fichier Excel " + filename + " créé.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * Méthode pour finaliser un feuillet Excel
     */
    private void finalizeWorksheet(XSSFSheet worksheet, int nbColumns, String LeftTitle, boolean landscape) {

        // Ajustement automatique de la largeur des colonnes
        for (int k = 0; k < nbColumns; k++) {
            worksheet.autoSizeColumn(k);
        }

        // Format A4 en sortie
        worksheet.getPrintSetup().setPaperSize(PaperSize.A4_PAPER);

        // Orientation paysage
        worksheet.getPrintSetup().setLandscape(landscape);

        // Affiche le quadrillage à l'écran
        worksheet.setDisplayGridlines(true);

        // Affiche le quadrillage à l'impression
        worksheet.setPrintGridlines(true);

//        System.out.println("getDefaultColumnWidth"+worksheet.getDefaultColumnWidth());
        // Ajustement à une page en largeur
        worksheet.setFitToPage(true);
        worksheet.getPrintSetup().setFitWidth((short) 1);
        worksheet.getPrintSetup().setFitHeight((short) 0);

        // En-tête et pied de page
        Header header = worksheet.getHeader();
        header.setLeft(LeftTitle);
        header.setRight("&F/&A");

        Footer footer = worksheet.getFooter();
        footer.setLeft("Documentation confidentielle Anstel");
        footer.setCenter("Page &P / &N");
        footer.setRight("&D");

        // Ligne à répéter en haut de page
        worksheet.setRepeatingRows(CellRangeAddress.valueOf("1:1"));
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
        AttendantEntry attendantEntry;

        skill = new Skill("Antargaz");
        System.out.println(skill);

        workgroup = new Workgroup("A_Antargaz");
        System.out.println(workgroup);

        agent = new Agent("thierry.baribaud");
        agent.setExtension("7504");
        agent.setName("Thierry Baribaud");
        System.out.println(agent);

        attendantEntry = new AttendantEntry("Default profile");
        System.out.println(attendantEntry);

//        try {
//            record = new InInRecord("\"Champ1\",\"Champ2\",\"Champ3\",\"Champ4\",\"Champ5\",\"Champ6\"");
//            System.out.println(record);
//            record = new InInRecord("\"Champ1\",\"Champ2 avec guillement\"\"\",\"Champ3\",\"Champ4 avec virgule,\",\"Champ5\",\"Champ6 avec virgule, et guillemets \"\"\"");
//            System.out.println(record);
//            record = new InInRecord("\"\\Courbevoie\\Production\\Users\\alexandra.nuiro\",\"User\",\"0\",\"displayName\",\"Alexandra NUIRO\",\"0\"");
//            System.out.println(record);
//            record = new InInRecord("\"Champ1\"");
//            System.out.println(record);
//        } catch (BadlyFormedInInRecordException ex) {
//            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, ex);
//        }
        System.out.println("Lancement de ExportInIn ...");
        try {
            exportInIn = new ExportInIn(args);
        } catch (IOException exception) {
            Logger.getLogger(ExportInIn.class.getName()).log(Level.SEVERE, null, exception);
        }
        System.out.println("Fin de ExportInIn");
    }

}
