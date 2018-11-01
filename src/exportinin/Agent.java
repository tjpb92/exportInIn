package exportinin;

/**
 * Calsse décrivant un agent de centre d'appel
 *
 * @author Thierry Baribaud
 * @version 0.02
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
     * Constructeur principal de la classe
     */
    public Agent() {
    }

    /**
     * Constructeur principal de la classe
     *
     * @param id identifiant de l'agent
     */
    public Agent(String id) {
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
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "Agent:{"
                + "id:" + getId()
                + ", name:" + getName()
                + ", extension:" + getExtension()
                + "}";
    }

}
