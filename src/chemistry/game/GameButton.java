package chemistry.game;

import chemistry.atoms.Atom;
import javafx.scene.control.Button;

/**
 * This class is almost identical to a button. The only difference is that
 * it has an extra generic field which stores an Atom. This is useful to
 * as it can return this atom upong being clicked to a method which verifies
 * if this was the correct answer or not.
 * @author https://github.com/AntonioBohne
 */
public class GameButton extends Button{
    
    private Atom atom;
    
    public void setAtom(Atom atom){
        this.atom = atom;
    }
    
    public Atom getAtom(){
        return atom;
    }
    
    /**
     * This method takes an object, checks its class type and tries to parse
     * it to a readable string. Objects like Integers or Doubles will be parsed
     * and converted into a String type. If the object can not be converted
     * into a string its toString method will be called. This method may
     * return garbage like memory adresses.
     * @param obj Object that will be set to the Button's text.
     */
    public void setText(Object obj) {
        if(obj.getClass() == Integer.class){
            this.textProperty().set(Integer.toString((int)obj));
        }
        else if(obj.getClass() == Double.class){
            this.textProperty().set(Double.toString((double)obj));
        }
        else if(obj.getClass() == String.class){
            this.textProperty().set((String)obj);
        }
        else 
            this.textProperty().set(obj.toString());
    }
}
