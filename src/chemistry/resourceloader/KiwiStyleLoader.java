package chemistry.resourceloader;

/**
 * App that keeps track of the current style (set of .css sheets) the user 
 * has chosen. All classes that must use .css stylesheets 
 * @author https://github.com/AntonioBohne
 */
public class KiwiStyleLoader {
    
    /**
     * Defines the several choices users have when loading the application.l
     */
    public enum STYLE{
        BLUE("Blue"),
        LIGHT("Light"),
        DARK("Dark");
       
        private String def;
        
        private STYLE(String str){
            this.def  =str;
        }
        
        @Override
        public String toString(){
            return def;
        }
    }
    
    private static STYLE currentStyle = STYLE.BLUE;
    
    /**
     * Returns the current style the app is using.
     * @return Current style being used.
     */
    public static STYLE getCurrentStyle(){
        return currentStyle;
    }
    
    /**
     * Sets the style the app will display to the user.
     * @param style New Style the app will display.
     */
    public static void setStyle(STYLE style){
        currentStyle = style;
    }
    
    /**
     * All Style enums are tied to a string that describes the style. This
     * application loops trough them and returns the enum that has the string
     * passed trough the parameters. Throws an exception if no enum is found.
     * @param style String that is tied to the enum.
     * @return Style enum that corresponds to the string passed trough the
     * parameters.
     * @throws NoSuchFieldException If no enum with that string is found.
     */
    public static STYLE getStyle(String style) throws NoSuchFieldException{
        STYLE styles[] = STYLE.values();
        for(STYLE stl : styles){
            if(stl.toString().equals(style))
                return stl;
        }
        throw new NoSuchFieldException("No style found for " + style);
    }
    
    /**
     * Returns the current URL in a String format to the current stylesheet
     * that displays the style the user has selected.
     * @return String that points to the .css stylesheet the application should
     * use.
     */
    public static String getStyleSheet(){
        String retVal = "chemistry/resourceloader/css/" +
              currentStyle.toString() + "Style.css";
        return retVal;
    }
    
}
