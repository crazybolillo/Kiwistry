package chemistry.sql;

import chemistry.resourceloader.LanguageLoader;

/**
 * This class stores all the table and column names inside the database. Any 
 * queries done in the application are encouraged to reference the name stored
 * here to improve flexibility. The class also stores some very common queries.
 * @author https://github.com/AntonioBohne
 */
public class SQLTracker {

     /**
     * Name of the main table in the database. Contains atoms' main properties
     * like the atomic number and the atomic mass. Language dependent information
     * can be found on the table  returned by the
     * <code>getAtomNameTable()</code> function.
     */
    public final static String MAIN_TABLE = "mainProperties";
    
    /**
     * Name of the table which stores the electronic configuration of atoms. 
     */
    public final static String ELECTRONIC_TABLE = "configElectronic";
    
    /**
     * This is by convention the default name the database uses in any column
     * which stores the atomicNumber of atoms.
     */
    public final static String ATOM_NUM_FIELD = "atomicNumber";
    
    /**
     * This is by convention the default name the database uses in any column
     * which stores the atomicMass of atoms.
     */
    public final static String ATOM_MASS_FIELD = "atomicMass";
    
    /**
     * By convention the default name for any column that stores atoms' names.
     */
    public final static String ATOM_NAME_FIELD = "name";
    
    /**
     * By convention the default name for any column that stores atoms' symbols.
     */
    public final static String ATOM_SYMBOL_FIELD = "symbol";
    /**
     * Returns the name of the table that stores the current language
     * atoms' names and symbols.
     * @return Table name where names and symbols in the selected language
     * are stored.
     */
    public static String getAtomNameTable(){
        return LanguageLoader.getCurrentLanguage().toString() + "Lang";
    }
    
    /**
     * Returns a query that will select the atomic mass, name and symbol from
     * an atom based on an atomic number. It is in a PreparedStatement format
     * where the atomic number will be the only variable that will need
     * to be set.
     * @return PreparedStatement that will select atomic mass, name and symbol.
     * Must set variable as the atomic number.
     */
    public static String SelectBasicAtomByNumber(){
        
        String query = "SELECT atomicMass, name, symbol "
                + "FROM mainProperties "
                + "INNER JOIN lang ON lang.atomicNumber = mainProperties.atomicNumber"
                + " WHERE mainProperties.atomicNumber = ?";
        query = query.replace("atomicMass", SQLTracker.ATOM_MASS_FIELD);
        query = query.replace("name", SQLTracker.ATOM_NAME_FIELD);
        query = query.replace("symbol", SQLTracker.ATOM_SYMBOL_FIELD);
        query = query.replace("mainProperties", SQLTracker.MAIN_TABLE);
        query = query.replace("lang", SQLTracker.getAtomNameTable());
        query = query.replace("atomicNumber", SQLTracker.ATOM_NUM_FIELD);
        
        return query;
    }
    
}
