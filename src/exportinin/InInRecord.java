package exportinin;

/**
 * Classe décrivant un enregistrement InIn
 *
 * @author Thierry Baribaud
 * @version 0.03
 */
public class InInRecord {

    private String path;
    private String objectClass;
    private String isKey;
    private String valueName;
    private String value;
    private String cntd;

    /**
     * Constructeur principal de la classe InInRecord
     *
     * @param inInRecord enregistrement InIn
     * @throws exportinin.BadlyFormedInInRecordException exception lancée si
     * l'enregistrement est mal formé
     */
    public InInRecord(String inInRecord) throws BadlyFormedInInRecordException {
        int nbToken;
//        StringTokenizer stringTokenizer;
//        String dummy;
        int i;
        StringBuffer field;
        String[] tokens;
        boolean inField;
        int quotes;
        char c;
        int len;
        int fieldLen;

        len = inInRecord.length();
//        System.out.println("inInRecord:" + inInRecord + ", len=" + len);

        inField = false;
        quotes = 0;
        field = new StringBuffer();
        nbToken = 0;
        tokens = new String[6];
        c=',';
        for (i = 0; i < len; i++) {
            c = inInRecord.charAt(i);
            if (c == '\"') {
                quotes++;
                if (inField) {
                    field.append(c);
                } else {
                    inField=true;
                }
            }
            else if (c == ',') {
                if ((quotes % 2) == 0) {
                    fieldLen = field.length() - 1;
                    if (fieldLen > 0) {
                        tokens[nbToken++] = field.substring(0, fieldLen);
                    } else {
                        tokens[nbToken++] = new String();
                    }
                    inField = false;
                    quotes = 0;
                    field = new StringBuffer();
                } else {
                    field.append(c);
                }
            }
            else {
               field.append(c); 
            }
        }
        if ((quotes % 2) == 0 && c != ',') {
            fieldLen = field.length() - 1;
            if (fieldLen > 0) {
                tokens[nbToken++] = field.substring(0, fieldLen);
            } else {
                tokens[nbToken++] = new String();
            }
        }
//        System.out.println("nbToken=" + nbToken);
//        for (i=0;i<inInRecord.length();i++){
//            System.out.println("inInRecord[" + i + "]=" + inInRecord.charAt(i));
//        }
//        tokens = inInRecord.split("\"");
//        nbToken = tokens.length;
//        System.out.println("split.nbToken:" + nbToken);
//        for (i=0;i<nbToken;i++){
//            System.out.println("inInRecord[" + i + "]=" + tokens[i]);
//        }
//        
//        stringTokenizer = new StringTokenizer(inInRecord, "\"");
//        nbToken = stringTokenizer.countTokens();
//        System.out.println("stringTokenizer.nbToken:" + nbToken);
//        i=0;
//        while(stringTokenizer.hasMoreTokens()) {
//            i++;
//            System.out.println("field(" + i + ")=" + stringTokenizer.nextToken());
//        }
//        if (nbToken == 12) {
//            this.path = stringTokenizer.nextToken();
//            dummy = stringTokenizer.nextToken();
//            this.objectClass = stringTokenizer.nextToken();
//            dummy = stringTokenizer.nextToken();
//            this.isKey = stringTokenizer.nextToken();
//            dummy = stringTokenizer.nextToken();
//            this.valueName = stringTokenizer.nextToken();
//            dummy = stringTokenizer.nextToken();
//            this.value = stringTokenizer.nextToken();
//            dummy = stringTokenizer.nextToken();
//            this.cntd = stringTokenizer.nextToken();
//        }
//        else {
//            throw new BadlyFormedInInRecordException("inInRecord"); 
//        }
        if (nbToken == 6) {
            this.path = tokens[0];
            this.objectClass = tokens[1];
            this.isKey = tokens[2];
            this.valueName = tokens[3];
            this.value = tokens[4];
            this.cntd = tokens[5];
        } else {
            throw new BadlyFormedInInRecordException("inInRecord");
        }

    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the objectClass
     */
    public String getObjectClass() {
        return objectClass;
    }

    /**
     * @param objectClass the objectClass to set
     */
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * @return the isKey
     */
    public String getIsKey() {
        return isKey;
    }

    /**
     * @param isKey the isKey to set
     */
    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    /**
     * @return the valueName
     */
    public String getValueName() {
        return valueName;
    }

    /**
     * @param valueName the valueName to set
     */
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the cntd
     */
    public String getCntd() {
        return cntd;
    }

    /**
     * @param cntd the cntd to set
     */
    public void setCntd(String cntd) {
        this.cntd = cntd;
    }

    /**
     * @return Retourne l'objet sous forme textuelle
     */
    @Override
    public String toString() {
        return "InInRecord:{"
                + "path:" + getPath()
                + ", objectClass:" + getObjectClass()
                + ", isKey:" + getIsKey()
                + ", valueName:" + getValueName()
                + ", value:" + getValue()
                + ", cntd:" + getCntd()
                + "}";
    }

}
