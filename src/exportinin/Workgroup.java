package exportinin;

/**
 * Classe décrivant un groupe de traitement
 *
 * @author Thierry Baribaud
 * @version 0.02
 */
public class Workgroup {

    /**
     * Nom du groupe de travail
     */
    private String name;

    /**
     * Délai de wrapup du groupe de travail
     */
    private int wrapup = 6;

    /**
     * Contructeur principal de la classe Workgroup
     */
    public Workgroup() {
    }

    /**
     * Contructeur secondaire de la classe Workgroup
     *
     * @param name nom du groupe de travail
     * @param wrapup délai de wrapup du groupe de travail
     */
    public Workgroup(String name, int wrapup) {
        this.name = name;
        this.wrapup = wrapup;
    }

    /**
     * Contructeur tertiaire de la classe Workgroup
     *
     * @param name nom du groupe de travail
     */
    public Workgroup(String name) {
        this.name = name;
    }

    /**
     * @return le nom du groupe de travail
     */
    public String getName() {
        return name;
    }

    /**
     * @param name définit le nom du groupe de travail
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return le délai de wrapup du groupe de travail
     */
    public int getWrapup() {
        return wrapup;
    }

    /**
     * @param wrapup définit le délai de wrapup du groupe de travail
     */
    public void setWrapup(int wrapup) {
        this.wrapup = wrapup;
    }

    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "Workgroup:{"
                + "name:" + getName()
                + ", wrapup:" + getWrapup()
                + "}";
    }

}
