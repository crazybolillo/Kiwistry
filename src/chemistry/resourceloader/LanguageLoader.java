/* 
 * Copyright (C) 2018 Antonio---https://github.com/AntonioBohne
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package chemistry.resourceloader;

import chemistry.sql.SQLReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

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
        ENG("English"),
        ESP("Español");
        
        private String lang;
        
        private LANGUAGE(String language){
            this.lang = language;
        }
        
        /**
         * Returns the name of the language. For example. LANGUAGE.ENG
         * will return "English".
         * @return Abreviation for the language.
         */
        @Override
        public String toString (){
            return lang;
        }
        
        /**
         * Returns the language's abbreviation. This is the same abbreviation
         * used in the SQL table that holds atom names to signal which 
         * language the name in the row belongs to. 
         * @return Language abbreviation as used in the SQL table that holds
         * atoms' names.
         */
        public String getAbbreviation(){
            return lang.substring(0, 3).toLowerCase();
        }
    }
    
    /**
     * Returns the LANGUAGE enum that is binded to the string passed trough the
     * parameters. <h3>Not case sensitive.</h3>
     * @param lang Language's name that is binded to its ENUM. For example, 
     * "English" is tied to the LANGUAGE.ENG enum.
     * @return Enum that matches the name passed trough the parameter. Can be
     * then used to set the app's language.
     * @throws NoSuchFieldException In case no enum that fits the criteria listed
     * above is found.
     */
    public static LANGUAGE getLanguage(String lang) throws NoSuchFieldException{
        LANGUAGE langList[] = LANGUAGE.values();
        for(LANGUAGE language : langList){
            if(language.toString().equals(lang)) return language;
        }
        throw new NoSuchFieldException("No langauge found for " + lang);
    }
    
    /**
     * Keeps track of the current language the loader has. 
     */
    private static LANGUAGE currLang = LANGUAGE.ESP;
    
    /**
     * Sets the language used so that the GUI reacts accordingly.
     * @param lang Language that will be used.
     */
    public static void setCurrentLanguage(LANGUAGE lang){
        currLang = lang;
        appLanguage = LanguageLoader.loadAppLanguage();
        AtomBuffer.updateBuffers();
    }
    
    /**
     * Returns the current language being used.
     * @return Enum which states the language being used.
     */
    public static LANGUAGE getCurrentLanguage(){
        return currLang;
    }
   
    /**
     * Holds all the text the GUI displays.
     */
    private static HashMap appLanguage = LanguageLoader.loadAppLanguage();
    
    /**
     * Queries the database and loads all the text that is displayed
     * to the user into a hashmap so that it can be used by the GUI classes
     * and displayed to the user.
     * @return HashMap with all the GUI text from the current language.
     */
    private static HashMap loadAppLanguage(){
        
        String query = "SELECT * "
                + "FROM interfaceLang "
                + "WHERE lang = ?";
        
        try{
            Connection con = SQLReader.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, LanguageLoader.getCurrentLanguage().
                    getAbbreviation());

            ResultSet rs = stmt.executeQuery();
            if(!rs.isBeforeFirst()) 
                throw new NoSuchFieldException("No translations found for: " +
                        LanguageLoader.getCurrentLanguage().toString());

            HashMap<String, String> retVal = new HashMap<>();
            while(rs.next()){
                retVal.put(rs.getString("type").toLowerCase()
                        , rs.getString("transl"));
            }
            return retVal;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Returns the translation for GUI components associated with the key
     * passed trough the parameters.
     * @param key Key which describes what GUI translation needs to be returned.
     * <h3>The key is case insensitive</h3>
     * @return An object which should be considered as a string or null if no
     * translation was found for that key.
     */        
    public static String getAppTranslation(String key){
        return (String)appLanguage.get(key.toLowerCase());
    }

}
