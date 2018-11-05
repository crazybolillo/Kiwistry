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
package chemistry.kiwiGUI.game;

import chemistry.dataModel.Atom;
import chemistry.rendering.ResizableLabel;

/**
 * This class is almost identical to a button. The only two differences is that
 * it has an extra generic field which stores an Atom. This is useful to
 * as it can return this atom upong being clicked to a method which verifies
 * if this was the correct answer or not. Second difference involves dynamic
 * font resizing based on its size.
 * @author https://github.com/AntonioBohne
 */
public class GameButton extends ResizableLabel {
    
    private Atom atom;
        
    /**
     * Sets the value for the atom field this class has.
     * @param atom Atom that will se to this classe's atom field. This value
     * is used to determine whether the user clicked on the rigth answer or not.
     */
    public void setAtom(Atom atom){
        this.atom = atom;
    }
    
    /**
     * Returns the Atom that this Button represents and stores.
     * @return Atom. Used to verify if the user clicked on the correct answer.
     */
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
