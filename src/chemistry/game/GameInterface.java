/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chemistry.game;

import chemistry.atoms.Atom;
import java.sql.SQLException;
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
     * Stores the set of choices the users has for the round.<h3>By convention
     * the right answer is always index 0 of this List.</h3>
     */
    private List<?> answers;
    protected void setAnswers(List<?> answers){this.answers = answers;}
    protected List<?> getAnswers(){return answers;}
    
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
     */
    protected Atom[] getRandomAtoms() throws SQLException{
        int atomicNumbers[] = 
                new Random().ints(1, 119).distinct().limit(4).toArray();
        
        Atom retVal[] = new Atom[4];
        for(int x = 0; x < retVal.length; x++){
            retVal[x] = new Atom(atomicNumbers[x]);
        }
        return retVal;
    } 
    
    protected <E> boolean verifyAnswer(E answer){
        return answers.indexOf(answer) == 0;
    }
    
    protected abstract void setQuestion();
    protected abstract <E> void checkAnswer(E answer);
    protected abstract void removeLife();
    protected abstract void updateScore();
    protected abstract void finishGame();
}
