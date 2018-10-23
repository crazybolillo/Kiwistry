package chemistry.atoms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class Atom {
    
    private int atomicNumber;
    private String symbol;
    private String name;
    private double atomicMass;
    
    private ArrayList<Integer> electronicConfig;
    
    /**
     * 
     * @param atomNum
     * @throws Exception 
     */
    public Atom(int atomNum) throws Exception {
        
        Connection con = DriverManager.getConnection(
                "jdbc:sqlite:" + this.getClass().getResource("atomos.db").toExternalForm());
        
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM atomos WHERE "
                + "numero = ?");
        stmt.setInt(1, atomNum);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new Exception("No atom found for atomic number: " + atomNum);
        }
        
        else{
            atomicNumber = results.getInt("numero");
            symbol = results.getString("symbol");
            name = results.getString("nombre");
            atomicMass = results.getDouble("masatomica");
        }
        
        //Close result set
        results.close();
        
        //Now get electronic configuration by querying the other table
        stmt = con.prepareStatement("SELECT * FROM confelec WHERE "
                + "numatomico = ?");
        stmt.setInt(1, atomicNumber);
        
        ResultSet secResults = stmt.executeQuery();
        
        if(!secResults.isBeforeFirst()){
            secResults.close();
            stmt.close();
            con.close();
            throw new Exception("No electronic config found for atomic number: " + atomNum);
        }
        else{   
            //Initialize array that will have electronic configuration
            electronicConfig = new ArrayList<Integer>();
            for(int x = 2; x < 9; x++) {
                if(secResults.getInt(x) != 0) {
                    electronicConfig.add(secResults.getInt(x));
                }
            }
        }
        
        secResults.close();
        stmt.close();
        con.close();
    }
    
    /**
     * 
     * @param name
     * @throws Exception 
     */
    public Atom(String name) throws Exception{
        
        Connection con = DriverManager.getConnection(
                "jdbc:sqlite:" + this.getClass().getResource("atomos.db").toExternalForm());
        
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM atomos WHERE "
                + "nombre = ?");
        stmt.setString(1, name);
        
        ResultSet results = stmt.executeQuery();
        
        if(!results.isBeforeFirst()){
            results.close();
            stmt.close();
            con.close();
            throw new Exception("No atom found for atomic number: " + name);
        }
        
        else{
            atomicNumber = results.getInt("numero");
            symbol = results.getString("symbol");
            this.name = results.getString("nombre");
            atomicMass = results.getDouble("masatomica");
        }
        
        //Close result set
        results.close();
        
        //Now get electronic configuration by querying the other table
        stmt = con.prepareStatement("SELECT * FROM confelec WHERE "
                + "numatomico = ?");
        stmt.setInt(1, atomicNumber);
        
        ResultSet secResults = stmt.executeQuery();
        
        if(!secResults.isBeforeFirst()){
            secResults.close();
            stmt.close();
            con.close();
            throw new Exception("No electronic config found for atomic number: " + name);
        }
        else{   
            //Initialize array that will have electronic configuration
            electronicConfig = new ArrayList<Integer>();
            for(int x = 2; x < 9; x++) {
                if(secResults.getInt(x) != 0) {
                    electronicConfig.add(secResults.getInt(x));
                }
            }
        }
        
        secResults.close();
        stmt.close();
        con.close();
    }
    
    /**
     * 
     * @return 
     */
    public int getAtomicNumber() {
        return atomicNumber;
    }
    
    /**
     * 
     * @return 
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return 
     */
    public double getAtomicMass() {
        return atomicMass;
    }
    
    public long getNeutrons() {
        return Math.round(atomicMass) - atomicNumber;
    }
    
    public ArrayList<Integer> getElectronicConfig() {
        return electronicConfig;
    }
}   
