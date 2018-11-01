package exportinin;

import java.util.ArrayList;

/**
 * Calsse décrivant un agent de centre d'appel
 *
 * @author Thierry Baribaud
 * @version 0.03
 */
public class Agent {

    /**
     * Identifiant de l'agent
     */
    private String id;

    /**
     * Nom de l'agent
     */
    private String name;

    /**
     * Extension téléphonique de l'agent
     */
    private String extension;

    /**
     * Rôle de l'agent
     */
    private String role;

    /**
     * Liste des compétences de l'agent
     */
    private ArrayList<String> skills;

    /**
     * Liste des groupes de traitement sur lesquels travaille l'agent
     */
    private ArrayList<String> workgroups;

    /**
     * Constructeur principal de la classe
     */
    public Agent() {
        skills = new ArrayList<>();
        workgroups = new ArrayList<>();
    }

    /**
     * Constructeur principal de la classe
     *
     * @param id identifiant de l'agent
     */
    public Agent(String id) {
        this();
        this.id = id;
    }

    /**
     * @return l'identifiant de l'agent
     */
    public String getId() {
        return id;
    }

    /**
     * @param id définit l'identifiant de l'agent
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return le nom de l'agent
     */
    public String getName() {
        return name;
    }

    /**
     * @param name définit le nom de l'agent
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return l'extension téléphonique de l'agent
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension définit l'extension téléphonique de l'agent
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return le rôle de l'agent
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role définit le rôle de l'agent
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return les compétences de l'agent
     */
    public ArrayList<String> getSkills() {
        return skills;
    }

    /**
     * @param skills définit les compétences de l'agent
     */
    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }

    /**
     * Ajoute une compétence à la liste des compétences de l'agent
     *
     * @param skill compétence à ajouter
     */
    public void addSkill(String skill) {
        this.skills.add(skill);
    }

    /**
     * @return les groupes de traitement sur lesquels travaille l'agent
     */
    public ArrayList<String> getWorkgroups() {
        return workgroups;
    }

    /**
     * @param workgroups définit les groupes de traitement sur lesquels
     * travaille l'agent
     */
    public void setWorkgroups(ArrayList<String> workgroups) {
        this.workgroups = workgroups;
    }

    /**
     * Ajoute un groupe de traitement à la liste des groupes de traitement sur
     * lesquels travaille l'agent
     *
     * @param workgroup groupe de traitement à ajouter
     */
    public void addWorkgroup(String workgroup) {
        this.workgroups.add(workgroup);
    }

    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "Agent:{"
                + "id:" + getId()
                + ", name:" + getName()
                + ", extension:" + getExtension()
                + ", role:" + getRole()
                + ", skills:" + getSkills()
                + ", workgroups:" + getWorkgroups()
                + "}";
    }
}
