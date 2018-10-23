package AtomTesting;

import chemistry.atoms.Atom;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class AtomClassTest {
    
    public static void main(String[] args) throws Exception {
    
        /*The following test loops trough the 113 elements with electronic 
        configuration available to ensure all of the atom class methods work
        for all the 113 elements.*/
        
        
        for(int x = 1; x <= 112; x++){
            
            Atom atomo = new Atom(x);
            
            System.out.println(atomo.getAtomicNumber());
            System.out.println(atomo.getSymbol());
            System.out.println(atomo.getName());
            System.out.println(atomo.getNeutrons());
            System.out.println(atomo.getElectronicConfig().size());
            System.out.println("-----");
        }
    }
}
