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
package chemistry.gameLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This is the base for all trivia games logic. It contains very basic 
 * functionality which can be further expanded by subclasses. This 
 * class keeps track of the live count, the score and forces all implementations
 * to define certain methods. 
 * @author https://github.com/AntonioBohne
 */
public abstract class TriviaInterface {
    
    /**
     * Used to randomize the right answer inside the set of options. It keeps
     * track of the last way the four possible answers were organized and
     * shuffles them, making sure the order is not the same.
     */
    private static List<Integer> INDEXES = new ArrayList<>(Arrays.asList(0,1,2,3));
    public static List<Integer> getRandomFour(){
        Collections.shuffle(INDEXES);
        return INDEXES;
    }
    
    /**
     * Keeps track of the lives the user has left. Once they reach cero the
     * game should end by calling the abstract finishGame method.
     */
    protected int liveCount = 3;
    protected void setLiveCount(int count){liveCount = count;}
    protected int getLiveCount(){return liveCount;}
    
    /**
     * Keeps track of the score the user has.
     */
    protected int scoreCount = 0;
    protected void setScore(int score){scoreCount = score;}
    protected int getScore(){return scoreCount;}  
    

    protected abstract void setQuestion();
    protected abstract <E> void checkAnswer(E answer);
    protected abstract void removeLife();
    protected abstract void updateScore();
    protected abstract void finishGame();
    
}
