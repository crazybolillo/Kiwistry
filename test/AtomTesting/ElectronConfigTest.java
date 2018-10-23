package AtomTesting;

import chemistry.atoms.Atom;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class ElectronConfigTest {
    
    public static void main(String[] args) throws Exception {
        
        /*The following test ensures that electronic configuaration arrays
        are displayed correctly. Correctly means that no array contains 0. It 
        can just contain numbers greater than 0. The test is done to the 112
        elements on the DB with electronic configuration*/
        for(int x = 1; x <= 112; x++) {
            
            Atom atomo = new Atom(x);
            System.out.println(atomo.getName());
            
            for(int z : atomo.getElectronicConfig()) {
                System.out.println(z);
            }
            
            System.out.println("-----");
        }
        
    }
    
}
