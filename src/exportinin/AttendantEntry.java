package exportinin;

/**
 * Classe décrivant une entrée Attendant
 *
 * @author Thierry Baribaud
 * @version 0.07
 */
public class AttendantEntry {

    /**
     * Nom de l'entrée Attendant
     */
    private String name;
    
    /**
     * Identifiant de l'entrée Attendant
     */
    private String id;
    
    /**
     * Liste des SDAs
     */
    private String sdaList;

    /**
     * Constructeur principal de la classe AttendantEntry
     */
    public AttendantEntry() {
    }

    /**
     * Constructeur secondaire de la classe AttendantEntry
     *
     * @param record nom et identifiant de l'entrée Attendant
     */
    public AttendantEntry(String record) {
        this();
        String[] buffer;

        name = "undefined";
        id = "undefined";
        
        buffer = record.split("\\|");
        if (buffer.length>0) {
            name=buffer[0];
            if (buffer.length>1) {
                id=buffer[1];
            }
        }
    }

    /**
     * @return le nom de l'entrée Attendant
     */
    public String getName() {
        return name;
    }

    /**
     * @param name définit le nom de l'entrée Attendant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return l'identifiant de l'entrée Attendant
     */
    public String getId() {
        return id;
    }

    /**
     * @param id définit l'identifiant de l'entrée Attendant
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return la liste de SDAs
     */
    public String getSdaList() {
        return sdaList;
    }

    /**
     * @param sdaList définit la liste des SDAs
     */
    public void setSdaList(String sdaList) {
        this.sdaList = sdaList;
    }

    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "AttendantEntry:{"
                + "name:" + getName()
                + ", id:" + getId()
                + ", SDAs:" + getSdaList()
                + "}";
    }
}
