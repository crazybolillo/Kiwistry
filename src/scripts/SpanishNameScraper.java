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
package scripts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class SpanishNameScraper {
    
    public static void main(String[] args) throws Exception{
        
        Document doc = Jsoup.connect(
            "https://www.periodni.com/es/elementos_clasificados_por_numero_atomico.html").
                get();

        Element table = doc.selectFirst("tbody");
        Elements tableContent = table.select("*tr");
        tableContent.remove(0); //Table header removed.
        
        Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\Users\\Antonio\\Documents\\NetBeansProjects\\Chemistry\\src\\chemistry\\atoms\\atomos.db");
        PreparedStatement stmt = con.prepareStatement("UPDATE atomos SET simbolo_esp = ?, nombre_esp = ? WHERE numero = ?");
        for(int x = 0; x < tableContent.size(); x++){
            String simbolo = tableContent.get(x).select("*td").get(1).text();
            String nombre = tableContent.get(x).select("*td").get(2).text();
            
            stmt.setString(1, simbolo);
            stmt.setString(2, nombre);
            stmt.setInt(3, x + 1);
            stmt.executeUpdate();
        }
    }
}
