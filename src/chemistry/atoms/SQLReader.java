package chemistry.atoms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class SQLReader {
    
    /**
     * Reads all the atom names from the database and groups them into an
     * ObservableList of strings.
     * @return ObservableList with all atomic names on the database.
     * @throws Exception This method should not throw any exception if the 
     * database has not been modified. It may throw exceptions if the sqlite
     * driver can not be found or if the database has changed and the query
     * made to it is no longer valid and returns nothing.
     */
    public static ObservableList<String> getAtomNames() throws Exception {
        
        Connection con = DriverManager.getConnection("jdbc:sqlite:" +
                SQLReader.class.getResource("atomos.db").getPath());
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery("SELECT nombre FROM atomos");
        
        if(!rs.isBeforeFirst()) throw new Exception("Atom names not found");
        
        ObservableList<String> retVal = FXCollections.observableArrayList();
        while(rs.next()){
            retVal.add(rs.getString("nombre"));
        }
        
        return retVal;
    }
    
}
