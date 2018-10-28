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
package chemistry.game;

import chemistry.atoms.Atom;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This is the "interface" that GameWindow extends. It keeps tracks of
 * the answers for the round, the score and lives count and forces the sub-class
 * to implement methods that set a question for the user, update the graphical
 * live counter and finish the game once the user loses the game.
 * @author https://github.com/AntonioBohne
 */
public abstract class GameInterface {
    
    /**
     * Used to randomize the right answer inside the set of 
     */
    private static List<Integer> INDEXES = new ArrayList<>(Arrays.asList(0,1,2,3));
    public static List<Integer> getRandomFour(){
        Collections.shuffle(INDEXES);
        return INDEXES;
    }
    
    /**
     * Stores the right answer for the round.
     */
    private Atom answer;
    protected void setAnswers(Atom answers){this.answer = answers;}
    protected Atom getAnswers(){return answer;}
    
    /**
     * Keeps track of the lives the user has left. Once they reach cero the
     * game should end by calling the abstract finishGame method.
     */
    private int liveCount = 3;
    protected void setLiveCount(int count){liveCount = count;}
    protected int getLiveCount(){return liveCount;}
    
    /**
     * Keeps track of the score the user has.
     */
    private int scoreCount = 0;
    protected void setScore(int score){scoreCount = score;}
    protected int getScore(){return scoreCount;}
    
    /**
     * Generates 4 different Atoms and returns them in an array. These atoms
     * can be used to set the question for the round. The atoms are guaranteed
     * to be different.
     * @return An array of Atom objects. Guaranteed to always have 4 items in it.
     * @throws SQLException The only way for the method to throw an excpetion
     * is by faiing to read the embedded database inside the .jar file. This
     * is rare udner normal conditions and will probably only happen if the
     * .jar is corrupted.
     * @throws java.lang.NoSuchFieldException
     */
    protected List<Atom> getRandomAtoms() throws SQLException, 
            NoSuchFieldException{
        int atomicNumbers[] = 
                new Random().ints(1, 119).distinct().limit(4).toArray();
        
        List<Atom> retVal = new ArrayList<>();
        for(int x = 0; x < 4; x++){
            retVal.add(new Atom(atomicNumbers[x]));
        }
        return retVal;
    } 
    
    /**
     * Compares the answer passed trough the parameters with the correct
     * answer. Returns false if they are not equal. True if they are. 
     * @param <E> Type of answer passed to the parameters.
     * @param answer Variable that will be compared.
     * @return True if the parameter matches the answer and false if it doesnt.
     */
    protected <E> boolean verifyAnswer(E answer){
        return this.answer.equals(answer);
    }
    
    /**
     * Inner class that contains a method and a string. In this context
     * the class holds a getter Method from the atom class and the String
     * contains a descrption of what the getter returns. This is can be used
     * to randomly generate different guesses for the user each round and 
     * display the string that displays what they are supposed to guess.
     */
    public class MethodMap {
        
        private Method clueMeth;
        private Method questionMeth;
        private String descriptor;
        
        public MethodMap(Method clMeth, Method meth, String str){
            this.clueMeth = clMeth;
            this.questionMeth = meth;
            this.descriptor = str;
        }
        
        /**
         * Returns the recommended method which returns data that can
         * be displayed to the user as a clue to aid him in guessing
         * the right answer.
         * @return getter Method from the Atom class. Can return a String or int
         */
        public Method getClueMethod(){
            return clueMeth;
        }
        
        /**
         * Returns the recommended method which returns data that can
         * be displayed to the user as a choice in the answer section.
         * @return getter Method from the Atom class. Can return a String or int
         */
        public Method getChoiceMethod(){
            return questionMeth;
        }
        
        /**
         * Returns a String which describes the information the user has to guess.
         * This information is the same as the information being given in the 
         * choices section.
         * @return String that described value returned by method returned
         *  by getChoiceMethod()
         */
        public String getDescriptor(){
            return descriptor;
        }
    }
    
    /**
     * Returns a list containing getter methods that returns variables which
     * can be used to be guessed by the player. The objects inside the list
     * contain the getter method and a String which can be shown to the user
     * which explains what needs to be guessed.
     * @return List with MethodMap objects, contains a getter method from the
     * Atom class and a String that details what the methods return.
     * @throws Exception If the methods can not be found.
     */
    public List<MethodMap> getMethods() throws Exception{
        List<MethodMap> retVal = new ArrayList<>();
        retVal.add(new MethodMap(Atom.class.getDeclaredMethod("getSymbol"),
                Atom.class.getDeclaredMethod("getName"),
                "Atom's name"));
        retVal.add(new MethodMap(Atom.class.getDeclaredMethod("getName"),
                Atom.class.getDeclaredMethod("getSymbol"),
                "Atom's symbol"));
        retVal.add(new MethodMap(Atom.class.getDeclaredMethod("getName"),
                Atom.class.getDeclaredMethod("getAtomicNumber"),
                "Atom's atomic number"));
        
        return retVal;
    }
    
    protected abstract void setQuestion();
    protected abstract <E> void checkAnswer(E answer);
    protected abstract void removeLife();
    protected abstract void updateScore();
    protected abstract void finishGame();
    
}
