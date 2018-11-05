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
package chemistry.dataModel;

import chemistry.sql.SQLReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which encloses several properties atom have. Non-modifiable once 
 * instantiated. 
 * @author https://github.com/AntonioBohne
 */
public class Atom {
    
    private int atomicNumber;
    private String symbol;
    private String name;
    private int group;
    private int period;
    private double electronegativity;
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
            symbol = results.getString("symbol");
            name = results.getString("name");
            group = results.getInt("atomGroup");
            period = results.getInt("atomPeriod");
            atomicMass = results.getDouble("atomicMass");
            electronegativity = results.getDouble("electronegativity");
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

        Connection con = SQLReader.getConnection();
        ResultSet results = SQLReader.getAtom(name, con);
        
        if(!results.isBeforeFirst()){
            results.close();
            con.close();
            throw new NoSuchFieldException("No atom found for atomic number: "
                    + name);
        }
        this.name = name;
        atomicNumber = results.getInt("atomicNumber");
        symbol = results.getString("symbol");
        group = results.getInt("atomGroup");
        period = results.getInt("atomPeriod");
        atomicMass = results.getDouble("atomicMass");
        electronegativity = results.getDouble("electronegativity");
        
        results.close(); 
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
        
        String query = "SELECT * FROM configElectronic"
                + " WHERE "
                + "atomicNumber = ?";
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
    
    /**
     * Returns the group the atom belongs to. Certain atoms do not have a 
     * group. In that case this method returns a '0'.
     * @return Group the atom belongs to.
     */
    public int getGroup(){
        return group;
    }
    
    /**
     * Returns the period the atom belongs to.
     * @return Period the atom belongs to.
     */
    public int getPeriod(){
        return period;
    }
    
    /**
     * Returns the electronegativity of the atom. If the atom has no electro
     * negativity then it will return a 0.
     * @return Atom's electronegavity. 0 if the atom has no electronegativity.
     */
    public double getElectronegativity(){
        return electronegativity;
    }
}   
