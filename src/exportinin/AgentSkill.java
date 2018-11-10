package exportinin;

/**
 * Classe décrivant une compétence d'un agent
 * @author Thierry Baribaud
 * @version 0.04
 */
public class AgentSkill {
    
    /**
     * Compétence
     */
    private String skill;
    
    /**
     * Niveau acquis
     */
    private int level;
    
    /**
     * Volonté d'utiliser la compétence
     */
    private int desireToUse;

    /**
     * Constructeur principal de la classe
     */
    public AgentSkill() {
    }
    
    /**
     * Constructeur secondaire de la classe
     * @param record chaine de caractères encodée de la manière suivante :
     * nom de la compétence|niveau|volonté d'utilisée
     */
    public AgentSkill(String record) {
        this();

        String[] buffer;

        buffer = record.split("\\|");
        if (buffer.length>0) {
            setSkill(buffer[0]);
            if (buffer.length>1) {
                setLevel(Integer.parseInt(buffer[1]));
                if (buffer.length>2) {
                    setDesireToUse(Integer.parseInt(buffer[2]));
                }
            }
        }
    }
    
    /**
     * @return la compétence
     */
    public String getSkill() {
        return skill;
    }

    /**
     * @param skill définit la compétence
     */
    public void setSkill(String skill) {
        this.skill = skill;
    }

    /**
     * @return le level de compétence
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level définit le level de compétence
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return la volonté d'utilisation de la compétence
     */
    public int getDesireToUse() {
        return desireToUse;
    }

    /**
     * @param desireToUse définit la volonté d'utilisation de la compétence
     */
    public void setDesireToUse(int desireToUse) {
        this.desireToUse = desireToUse;
    }
    
    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "AgentSkill:{"
                + "skill:" + getSkill()
                + ", level:" + getLevel()
                + ", desireToUse:" + getDesireToUse()
                + "}";
    }
    
}
