package exportinin;

/**
 * Classe décrivant une compétence d'un agent
 *
 * @author Thierry Baribaud
 * @version 0.02
 */
public class Skill {

    /**
     * Nom de la compétence
     */
    private String name;

    /**
     * Constructeur principal de la classe Skill
     */
    public Skill() {
    }

    /**
     * Constructeur secondaire de la classe Skill
     *
     * @param name nom de la compétence d'un agent
     */
    public Skill(String name) {
        this.name = name;
    }

    /**
     * @return le nom de la compétence
     */
    public String getName() {
        return name;
    }

    /**
     * @param name définit le nom de la compétence
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "Skill:{"
                + "name:" + getName()
                + "}";
    }
}
