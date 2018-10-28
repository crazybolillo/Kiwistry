package scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class NewDB {
    
    public static void main(String[] args) throws Exception{
        
        Connection oldCon = DriverManager.getConnection(
                "jdbc:sqlite:D:\\Users\\Antonio\\Documents\\NetBeansProjects\\Chemistry\\src\\chemistry\\atoms\\atomos.db");
        String query = "SELECT * FROM confelec";
        Statement stmt = oldCon.createStatement();
        
        Connection newCon = DriverManager.getConnection("jdbc:sqlite:D:\\Users\\Antonio\\Documents\\atomicDB.db");
        
        List<List<Integer>> pk = new ArrayList<>();
        
        ResultSet set = stmt.executeQuery(query);
        while(set.next()){
            List<Integer> list = new ArrayList<>();
            list.add(set.getInt(1));
            list.add(set.getInt(2));
            list.add(set.getInt(3));
            list.add(set.getInt(4));
            list.add(set.getInt(5));
            list.add(set.getInt(6));
            list.add(set.getInt(7));
            list.add(set.getInt(8));
            pk.add(list);
        } 
     
        set.close();
        stmt.close();
        oldCon.close();
        
        String nwQuery = "INSERT INTO confelec VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement prStmt = newCon.prepareStatement(nwQuery);
        
        for(int x = 0; x < pk.size(); x++){
            for(int y = 0; y < 8; y++){
                prStmt.setInt(y + 1, pk.get(x).get(y));
            }
            prStmt.executeUpdate();
        }
        
        newCon.close();
    }
    
}
