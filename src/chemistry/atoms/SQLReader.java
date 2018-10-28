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
package chemistry.atoms;

import chemistry.sql.SQLTracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class manages the interactions with the database that holds all
 * the atoms' information. It stores common queries and returns their result
 * sets. Provides read-only connections to the database and keeps track of 
 * the table names used in the queries. Using Prepared like queries which 
 * use the replace function to insert the table names stored in the 
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
        
        Connection con = SQLReader.getConnection();
        
        String query = "SELECT name FROM #";
        query = query.replace("#", SQLTracker.getAtomNameTable());
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(query);
        
        if(!rs.isBeforeFirst()) throw new Exception("Atom names not found");
        
        ObservableList<String> retVal = FXCollections.observableArrayList();
        while(rs.next()){
            retVal.add(rs.getString("name"));
        }
        
        return retVal;
    }
    
    /**
     * Returns a connection to the read only embedded sqlite database found
     * inside the compiled .jar
     * @return Read only connection to the database contaning information
     * about atoms.
     * @throws SQLException If it can not find the resource which should be rare
     * since the internals of the .jar should not be modified.
     */
    public static Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection("jdbc:sqlite::resource:" +
                "chemistry/atoms/atomicDB.db");
        return con;
    }
    
    /**
     * Queries the database and returns a result set that contains the atomic
     * mass, name and symbol from the atom that has the atomic number passed
     * trough the parameters. The result set may be empty if the atomic number
     * does not exist.
     * @param atomicNum Atomic number from the atom that the information
     * returned will belong to.
     * @param con Connection to the database. Can be gathered with
     * <code>SQLReader.getConnection()</code>
     * @return ResultSet, there is no guarantee that it will not be empty.
     * If the ResultSet is not empty it should contain three columns. One
     * with the atomic mass, one with the name of the atom and one with the symbol.
     * The order of these columns is not guaranteed so accesing the ResultSet by 
     * column labels instead of column indexes is recommended.
     * @throws SQLException This method should not throw any exception unless
     * the connection to the database fails which could indicate a corrupted
     * jar file.
     */
    public static ResultSet getAtom(int atomicNum, 
            Connection con) throws SQLException{
        
        String query = SQLTracker.SelectBasicAtomByNumber();
        
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, atomicNum);
        
        ResultSet retVal = stmt.executeQuery();
        return retVal;
    }
}
