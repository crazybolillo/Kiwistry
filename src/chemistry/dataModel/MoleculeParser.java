package chemistry.dataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Class dedicated to parse molecule formulas into data structures the program
 * can process. It first tokenizes the molecules formula by decomposing it
 * into a table that contains all the atom's found inside the molecule and
 * their ammount to later make sense of it.
 * @author https://github.com/AntonioBohne
 */
public class MoleculeParser {
    
    /**
     * Tokenizes a molecule formula. It breaks down the formula into a table
     * that contains all the symbols found and the amount of them. For example,
     * H20 would be converted into a 2x2 table with a row that contains H as
     * the symbol and 2 as the amount, followed by another row which contains
     * O as the symbol and 1 as the amount.
     * @param formula Molecule formula to be tokenized.
     * @return List that contains all the symbols found with the respective
     * amount. Each index of the list contains an Atom token which has 
     * both the symbol and the amount of it.
     */
    public static List<AtomToken> lexMolecule(String formula) 
            throws NumberFormatException {
        List<AtomToken> retVal = new ArrayList<>();
        for(int x = 0; x < formula.length(); x++){
            if(Character.isUpperCase(formula.charAt(x))){
                int len = skipUntilUpperCase(formula.substring(x));
                retVal.add(new AtomToken(formula.substring(x, x + len)));
                x = len == 1 ? x : x + len - 1;
            }
        }
        return retVal;
    }
    
    /**
     * Skips all characters until it finds one that is uppercase or it reaches
     * the String's end. 
     * @param string String that will be skipped.
     * @return Integer that represents the amount of characters that 
     * were skipped over.
     */
    private static int skipUntilUpperCase(String string){
        int x = 1;
        for(; x < string.length(); x++){
            if(Character.isUpperCase(string.charAt(x)))
                return x;
        }
        return x;
    }
}
