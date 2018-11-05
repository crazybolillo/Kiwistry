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
package chemistry.resourceloader;

import chemistry.sql.SQLReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is in charge of loading several resources from the database
 * that will be constatly requested. This saves resources as queries to
 * the database have to be done only upon startup (or when the language changes
 * in certain cases). 
 * @author https://github.com/AntonioBohne
 */
public class AtomBuffer {
    
    
    /**
     * Keeps track of all the atom names found inside the database and only
     * changes whenever the user decides to change the language.
     */
    private static List<String> atomNames = setAtomNames();
    
    private static List<String> atomSymbols = getAtomSymbols();
    
    /**
     * Wraps over the SQLRedaer method to catch any exceptions thrown and
     * returns all the names of the atoms in the current language. Returns
     * null if any exception is thrown while reading the data. 
     * @return List with name of atoms. Null if there is any exception when
     * reading the data.
     */
    private static List<String> setAtomNames() {
        try{
            return SQLReader.getAtomNames();
        }catch(Exception ex){
            return null;
        }
    }
    
    /**
     * 
     * @return 
     */
    private static List<String> getAtomSymbols() {
        try{
            return SQLReader.getAtomSymbols();
        }catch(Exception ex){
            return null;
        }
    }
    
    
    /**
     * Updates all language dependent resources by querying the database again.
     * Used whenever the language has changed and the information needs to be
     * updated.
     */
    protected static void updateBuffers(){
        atomNames = setAtomNames();
    }
    
    /**
     * Returns a copy of all the atom names contained in the database.
     * @return List with all atom names.
     */
    public static List<String> getAllAtomNames(){
        return new ArrayList<>(atomNames);
    }
}
