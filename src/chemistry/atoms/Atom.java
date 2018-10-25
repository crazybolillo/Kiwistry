package chemistry.atoms;

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
     * 
     * @param atomNum
     * @throws SQLException 
     */
    public Atom(int atomNum) throws SQLException {
        
        Connection con = SQLReader.getConnection();
        
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM atomos WHERE "
                + "numero = ?");
        stmt.setInt(1, atomNum);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new SQLException("No atom found for atomic number: " + atomNum);
        }
        
        else{
            atomicNumber = results.getInt("numero");
            symbol = results.getString("symbol");
            name = results.getString("nombre");
            atomicMass = results.getDouble("masatomica");
        }
        
        //Close result set
        results.close();
        
        this.setElectronicConfiguration(con);

        stmt.close();
        con.close();
    }
    
    /**
     * Creates an atom based on the name passed trough the parameters. Case 
     * insensitive.
     * @param name Name of the atom that will be created. For example, 'Hydrogen'
     * @throws SQLException In case the name is not found inside the database. The
     * biggest atom the list contains is Oganneson.
     */
    public Atom(String name) throws SQLException{
        
        Connection con = SQLReader.getConnection();
        
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM atomos WHERE "
                + "nombre = ? COLLATE NOCASE");
        stmt.setString(1, name);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new SQLException("No atom found for atomic number: " + name);
        }
        
        else{
            atomicNumber = results.getInt("numero");
            symbol = results.getString("symbol");
            this.name = results.getString("nombre");
            atomicMass = results.getDouble("masatomica");
        }
        
        results.close();
        
        this.setElectronicConfiguration(con);
        
        stmt.close();
        con.close();
    }
    
    /**
     * Gathers data on the electronic configuration of the atom and sets
     * the electronic configuration field.
     * @param con Open database connection to the embedded database containing
     * the information.
     * @throws SQLException If the ResultSet from the query is empty an exception
     * will be thrown.
     */
    private void setElectronicConfiguration(Connection con) 
            throws SQLException{
        
        //Now get electronic configuration by querying the other table
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM confelec WHERE "
                + "numatomico = ?");
        stmt.setInt(1, atomicNumber);
        
        ResultSet secResults = stmt.executeQuery();
        
        if(!secResults.isBeforeFirst()){
            secResults.close();
            stmt.close();
            con.close();
            throw new SQLException("No electronic config found for atomic number: " + name);
        }

        electronicConfig = new ArrayList<Integer>();
        for(int x = 2; x < 9; x++) {
            if(secResults.getInt(x) != 0) {
                electronicConfig.add(secResults.getInt(x));
            }
        }
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
