package chemistry.resourceloader;

/**
 * Keeps track of the language that the app is supposed to display. The language
 * impacts the atoms' names and symbols. It also impacts the labeling of all
 * the GUI controls. By making all language related calls to this class
 * a synchronized language is ensured.
 * @author https://github.com/AntonioBohne
 */
public class LanguageLoader {

    /**
     * Used to load a certain language into the app or gather
     * the current language.
     */
    public enum LANGUAGE{
        ENG("eng"),
        ESP("esp");
        
        private String abrv;
        
        private LANGUAGE(String abrv){
            this.abrv = abrv;
        }
        
        /**
         * Returns the abreviaton for the language. Useful when querying 
         * the database since tables that hold atoms' names for different
         * languages are distinguished by this abbreviation. All tables that
         * hold atoms' symbols and names have the name "Lang" and the language
         * abbreviation before it. For example the table that holds atoms' 
         * names in English is called "engLang".
         * @return Abreviation for the language.
         */
        @Override
        public String toString (){
            return abrv;
        }
    }
    
    /**
     * Keeps track of the current language the loader has. 
     */
    private static LANGUAGE currLang = LANGUAGE.ENG;
    
    /**
     * Sets the language used to query 
     * @param lang Language that will be used.
     */
    public static void setCurrentLanguage(LANGUAGE lang){
        currLang = lang;
    }
    
    /**
     * Returns the current language being used.
     * @return Enum which states the language being used.
     */
    public static LANGUAGE getCurrentLanguage(){
        return currLang;
    }
}
