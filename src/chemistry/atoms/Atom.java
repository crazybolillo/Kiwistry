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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class Atom {
    
    private int atomicNumber;
    private String symbol;
    private String name;
    private double atomicMass;
    
    private List<Integer> electronicConfig;
    
    /**
     * Creates an atom with basic properties like name, symbol, and atomic mass
     * from the atom that has the atomic number passed trough the parameters.
     * @param atomNum Atomic number from atom that will be created.
     * @throws SQLException In case the connection to the database fails.
     * @throws NoSuchFieldException In case the atom with the atomic number
     * on the parameters was not found.
     */
    public Atom(int atomNum) throws SQLException, NoSuchFieldException {

        Connection con = SQLReader.getConnection();
        ResultSet results = SQLReader.getAtom(atomNum, con);
        
        if(!results.isBeforeFirst()){
            results.close();
            throw new NoSuchFieldException(
                    "No atom found for atomic number: " + atomNum);
        }
        
        else{
            atomicNumber = atomNum;
            symbol = results.getString(SQLTracker.ATOM_SYMBOL_FIELD);
            name = results.getString(SQLTracker.ATOM_NAME_FIELD);
            atomicMass = results.getDouble(SQLTracker.ATOM_MASS_FIELD);
        }
        
        //Close result resources
        results.close();
        this.setElectronicConfiguration(con);
    }
    
    /**
     * Creates an atom based on the name passed trough the parameters. Case 
     * insensitive.
     * @param name Name of the atom that will be created. For example, 'Hydrogen'
     * @throws SQLException In case the connection to the database fails.
     * @throws java.lang.NoSuchFieldException In case no atom with that name
     * was found.
     */
    public Atom(String name) throws SQLException, NoSuchFieldException{
        
        String query = "SELECT atomicNumber, symbol FROM # WHERE name = ?"
                + " COLLATE NOCASE";
        query = query.replace("atomicNumber", SQLTracker.ATOM_NUM_FIELD);
        query = query.replace("symbol", SQLTracker.ATOM_SYMBOL_FIELD);
        query = query.replace("#", SQLTracker.getAtomNameTable());
        query = query.replace("name", SQLTracker.ATOM_NAME_FIELD);
        
        Connection con = SQLReader.getConnection();
        
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, name);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new NoSuchFieldException("No atom found for atomic number: "
                    + name);
        }
        this.name = name;
        atomicNumber = results.getInt(SQLTracker.ATOM_NUM_FIELD);
        symbol = results.getString(SQLTracker.ATOM_SYMBOL_FIELD);
        results.close();
        
        //---------------------------------------
        //Second query to gather the atomic mass.
        //----------------------------------------
        query = "SELECT atomicMass FROM mainProperties"
                + " WHERE "
                + "atomicNumber = ?";
        query = query.replace("atomicMass", SQLTracker.ATOM_MASS_FIELD);
        query = query.replace("mainProperties", SQLTracker.MAIN_TABLE);
        query = query.replace("atomicNumber", SQLTracker.ATOM_NUM_FIELD);
        
        stmt = con.prepareStatement(query);
        stmt.setInt(1, atomicNumber);
        
        ResultSet secResult = stmt.executeQuery();
        if(!secResult.isBeforeFirst()){
            stmt.close();
            con.close();
            throw new NoSuchFieldException("No atomic mass found for atomic "
                    + "number: " + atomicNumber);
        }
        atomicMass = secResult.getDouble(SQLTracker.ATOM_MASS_FIELD);
        secResult.close();
        stmt.close();
        
        this.setElectronicConfiguration(con); //This method closes the connection.
    }
    
    /**
     * Gathers data on the electronic configuration of the atom and sets
     * the electronic configuration field. It uses the atomic number field
     * of the class to make the query which means <h3> the atomic number field
     * must be set before calling this function</h3>
     * @param con Open database connection to the embedded database containing
     * the information.
     * @throws SQLException If there is any problem connecting to the database.
     * @throws NoSuchFieldException In the very rare if not impossible
     * case the result empty. This should not happen if the atomic number
     * which is used to make the query has been validated before.
     */
    private void setElectronicConfiguration(Connection con) 
            throws SQLException, NoSuchFieldException{
        
        String query = "SELECT * FROM confelec WHERE "
                + "atomicNumber = ?";
        query = query.replace("confelec", SQLTracker.ELECTRONIC_TABLE);
        query = query.replace("atomicNumber", SQLTracker.ATOM_NUM_FIELD);
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, atomicNumber);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new NoSuchFieldException("No electronic config found for atomic "
                    + "number: " + name);
        }

        electronicConfig = new ArrayList<>();
        for(int x = 2; x < 9; x++) {
            if(results.getInt(x) != 0) {
                electronicConfig.add(results.getInt(x));
            }
        }
        results.close();
        stmt.close();
        con.close();
    }
    
    /**
     * Returns the atomic number (0-118) of the atom.
     * @return 
     */
    public int getAtomicNumber() {
        return atomicNumber;
    }
    
    /**
     * Returns the symbol or abbreviation of the atom's name. 
     * @return 
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Returns the name of the atom.
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns a double with the atomic mass of the atomic number. The amount
     * of decimals the number may have can differ.
     * @return 
     */
    public double getAtomicMass() {
        return atomicMass;
    }
    
    /**
     * The amount of neutrons the atom has. Calculated by rounding the 
     * atomci mass and substracting the atomic number. 
     * @return 
     */
    public int getNeutrons() {
        /*There is no data loss casting the result to an integer since the 
        atomic mass is way smaller than the integer size limit.*/
        return (int)atomicMass - atomicNumber;
    }
    
    /**
     * Returns a list representing the energy levels an atom has. The list will
     * always contain at least one element. Each index represents a level and
     * it contains the amount of electrons inside the specific level. As of 2018
     * not atoms with more than 7 levels have been discovered so this method
     * will always return a list with a size between 1 and 7. Do not confuse orbitals
     * with levels.
     * @return Integer list representing the atom's energy levels.
     * Guaranted to always have at least one index.
     */
    public List<Integer> getElectronicConfig() {
        return electronicConfig;
    }
}   
