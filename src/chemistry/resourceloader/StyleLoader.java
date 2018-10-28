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

/**
 * This class manages the look and feel of the application which can be changed
 * at runtime by the user. It stores the different links to the application's 
 * resources like stylesheets or applications so that applications can access
 * to them via getter methods. This makes sure that the style of the application
 * is synchronized troughout its different sections.
 * @author https://github.com/AntonioBohne
 */
public class StyleLoader {
    
    /**
     * Enum used to set the style of the application. States whether the 
     * light or dark resources should be loaded.
     */
    public enum STYLE_TYPE{
        LIGHT("Light Theme"),
        DARK("Dark Theme");
        
        private String description;
        
        private STYLE_TYPE(String description){
            this.description = description;
        }
        
        /**
         * Returns the string binded to the ENUM. The string can be used to
         * describe what the enum stands for in user-friendly terms.
         * @return String that describes what the enum stands for.
         */
        @Override
        public String toString(){
            return this.description;
        }
    }
    
    /**
     * Returns the type of enum associated to the description passed trough
     * the parameters.
     * @param description Description that is tied to an enum.
     * @return Enum type. If not found it will return a LIGHT enum
     * by default.
     */
    public static STYLE_TYPE getType(String description){
        if(description.equals(STYLE_TYPE.LIGHT.toString())) return STYLE_TYPE.LIGHT;
        else if(description.equals(STYLE_TYPE.DARK.toString())) return STYLE_TYPE.DARK;
        return STYLE_TYPE.LIGHT;
    }
    
    /**
     * Keeps track of the current style loaded.
     */
    private static STYLE_TYPE currentStyle = STYLE_TYPE.LIGHT;
    
    /**
     * Link for the stylesheet that the applications should load.
     */
    private static String generalStyleSheet = StyleLoader.class.getResource(
            "css/LightGeneralStyle.css").toExternalForm();
    
    /**
     * Link for the stylesheet that any game related window should load.
     */
    private static String gameStyleSheet = StyleLoader.class.getResource(
            "css/LightGameStyle.css").toExternalForm();
    
    /**
     * Link to the image that represents a filled heart (lives the player
     * has left). This image is used in the game window.
     */
    private static String filledHeartURL = StyleLoader.class.getResource(
            "img/LightFillHeart.png").toExternalForm();
    
    /**
     * Link to the image that represents an empty heart (lives the player has lost).
     * This image is used in the game window.
     */
    private static String emptyHeartURL = StyleLoader.class.getResource(
            "img/LightEmptyHeart.png").toExternalForm();
    
    /**
     * Changes all the links to the resources so they point to the style passed
     * trough the parameters.
     * @param style New style that will be loaded.
     */
    public static void setApplicationStyle(STYLE_TYPE style){
        switch(style){
            case LIGHT:
                StyleLoader.loadLightStyle();
                break;
                
            case DARK:
                StyleLoader.loadDarkStyle();
                break;
        }
    }
    
    /**
     * Returns the current style that is loaded in the application.
     * @return StyleType
     */
    public static STYLE_TYPE getCurrentStyle(){
        return currentStyle;
    }
    
    /**
     * Returns an URL in String format pointing to the current stylesheet
     * that should be loaded for general purpose windows.
     * @return String pointing to stylesheet.
     */
    public static String getGeneralStyleSheetURL(){
        return generalStyleSheet;
    }
    
    /**
     * Returns an URL in String format pointing to the current stylesheet
     * that should be loaded for game related windows.
     * @return String pointing to stylesheet.
     */
    public static String getGameStyleSheetURL(){
        return gameStyleSheet;
    }
    
    /**
     * Returns an URL in String format pointing to the current filled heart image
     * that should be used in game windows to represent how many lives the 
     * players has left.
     * @return String pointing to filled heart image.
     */
    public static String getFilledHeartURL(){
        return filledHeartURL;
    }
    
    /**
     * 
     * @return 
     */
    public static String getEmptyHeartURL(){
        return emptyHeartURL;
    }
    
    /**
     * Changes all the links to resources so they point to the light style.
     */
    private static void loadLightStyle(){
        currentStyle = STYLE_TYPE.LIGHT;
        
        generalStyleSheet = StyleLoader.class.getResource(
            "css/LightGeneralStyle.css").toExternalForm();
        
        gameStyleSheet = StyleLoader.class.getResource(
            "css/LightGameStyle.css").toExternalForm();

        filledHeartURL = StyleLoader.class.getResource(
            "img/LightFillHeart.png").toExternalForm();
    
        emptyHeartURL = StyleLoader.class.getResource(
            "img/LightEmptyHeart.png").toExternalForm();
    }
    
    /**
     * Changes all the links to resources so they point to the dark style.
     */
    private static void loadDarkStyle(){
        currentStyle = STYLE_TYPE.DARK;
        
        generalStyleSheet = StyleLoader.class.getResource(
            "css/DarkGeneralStyle.css").toExternalForm();
        
        gameStyleSheet = StyleLoader.class.getResource(
            "css/DarkGameStyle.css").toExternalForm();
        
        filledHeartURL = StyleLoader.class.getResource(
            "img/DarkFillHeart.png").toExternalForm();
    
        emptyHeartURL = StyleLoader.class.getResource(
            "img/DarkEmptyHeart.png").toExternalForm();
    }
}
