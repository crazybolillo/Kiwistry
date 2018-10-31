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
package chemistry.basicGUI;

import chemistry.utils.GridBoiler;
import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import chemistry.game.AtomTriviaInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import chemistry.rendering.ResizableLabel;
import chemistry.resourceloader.StyleLoader;

/**
 * This class provides the GUI to play an interactive trivia game about Atoms.
 * It is based on the abstract class AtomTriviaInterface which provides useful methods
 and keeps track of the score and lives left. The window uses modified labels
 and buttons that implement a better resizing behavior.
 * @author https://github.com/AntonioBohne
 */
public class AtomTrivia extends AtomTriviaInterface{

    private DefaultStage window;
    private Scene sc;
    
    private GridPane container;
    private GridPane topPane;
    private GridPane bottomPane;
    
    private List<GridPane> liveWrapper;
    
    private ResizableLabel clueLbl;
    private ResizableLabel scoreLbl;
    
    private ResizableLabel choiceLbl;
    private List<GameButton> choiceBtn;
    
    /**
     * Initializes all the layout components the window has.
     */
    public AtomTrivia() {

        this.setTopPanel();
        this.setBottomPanel();
                
       //-------------------
       //Setting container panel
       //-------------------
       container = new GridPane();
       container.setAlignment(Pos.CENTER);
       GridBoiler.addRowConstraints(container, 60, 40);
       
       GridPane.setConstraints(topPane, 0, 0);
       container.getChildren().add(topPane);
       
       GridPane.setConstraints(bottomPane, 0, 1);
       container.getChildren().add(bottomPane);
       
        sc = new Scene(container, 400, 480);
        sc.getStylesheets().add(StyleLoader.getGameStyleSheetURL());
        
        window = new DefaultStage();
        window.setWidth(sc.getWidth());
        window.setHeight(sc.getHeight());
        window.setScene(sc);
        window.setResizable(true);
        window.setTitle("Atomic game");
        
        //Controlling window resizing
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight()); 
    }
       
    /**
     * 
     */
    private void setTopPanel(){
    
        topPane = new GridPane();
        GridBoiler.addColumnConstraints(topPane, 15, 15, 15, 30, 25);
        GridBoiler.addRowConstraints(topPane, 25, 75);
        
       /*Creating hearts which signify how many lives the player has left. They
        are wrapped in a GridPane so that their width and height are binded to
        it, allowing for resizing.*/
        liveWrapper = new ArrayList<>();
        for(int x = 0; x < 3; x++){
            ImageView img = new ImageView(new Image(
                    StyleLoader.getFilledHeartURL()));
            img.setFitWidth(50);
            img.setFitHeight(50);
            img.setPreserveRatio(true);
            
            GridPane pane = new GridPane();
            pane.setAlignment(Pos.CENTER);
            pane.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            pane.setMinSize(0, 0);
            liveWrapper.add(pane);
            
            GridPane.setConstraints(img, 0, 0);
            GridPane.setHalignment(img, HPos.CENTER);
            pane.getChildren().add(img);
            
            img.fitWidthProperty().bind(pane.widthProperty());
            img.fitHeightProperty().bind(pane.heightProperty());
            
            GridPane.setConstraints(pane, x, 0);
            topPane.getChildren().add(pane);
        }
        clueLbl = new ResizableLabel();
        clueLbl.setSizeToHeightRatio(5);
        clueLbl.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        clueLbl.setMinSize(0, 0);
        clueLbl.setAlignment(Pos.CENTER);
        
        GridPane.setConstraints(clueLbl, 0, 1, 5, 1);
        topPane.getChildren().add(clueLbl);
        
        scoreLbl = new ResizableLabel("000");
        scoreLbl.setSizeToHeightRatio(1.5);
        scoreLbl.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        scoreLbl.setMinSize(0, 0);
        scoreLbl.setAlignment(Pos.CENTER);
        
        GridPane.setConstraints(scoreLbl, 4, 0);
        GridPane.setHalignment(scoreLbl, HPos.RIGHT);
        topPane.getChildren().add(scoreLbl);
    }
    
    /**
     * 
     */
    private void setBottomPanel(){
    
       bottomPane = new GridPane();
       choiceBtn = new ArrayList<>();
       for(int x = 0; x < 4; x++){
           GameButton btn = new GameButton();
           btn.setId("choice");
           btn.setAlignment(Pos.CENTER);
           btn.setSizeToHeightRatio(3);
           btn.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
           btn.setMinSize(0, 0);
           btn.setOnMouseClicked(e ->{
               this.checkAnswer(btn.getAtom());
           });
           choiceBtn.add(btn);
       }
       choiceLbl = new ResizableLabel();
       choiceLbl.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
       choiceLbl.setMinSize(0, 0);
       choiceLbl.setAlignment(Pos.CENTER);
       
       GridBoiler.addColumnConstraints(bottomPane, 50, 50);
       GridBoiler.addRowConstraints(bottomPane, 20, 40, 40);
       
       GridPane.setConstraints(choiceLbl, 0, 0, 2, 1);
       GridPane.setHalignment(choiceLbl, HPos.CENTER);
       bottomPane.getChildren().add(choiceLbl);
       
       int row = 1;
       int col = 1;
       int added = 0;
       for(GameButton btn : choiceBtn){        
           GridPane.setConstraints(btn, col - row, row, 1, 1);
           bottomPane.getChildren().add(btn);
           if(col == 2 && added == 0){ 
               row++;
               added++;
           }
           else col++;
       }
    }
    
    /**
     * Displays the window. 
     */
    public void display(){
        window.show();
        this.setQuestion();
    }
    
    /**
     * Closes the window.
     */
    public void close(){
        window.close();
    }

    /**
     * Generates a list containing four random atoms and sets the first one
     * as the correct answer. It then proceeds to select a random QuestionMap 
 object which contains the getter that will display the clue, the getter
 that will display the possible answers and a String that tells the user
 what needs to be guessed.
     */
    @Override
    protected void setQuestion() {     
        try {
            List<Atom> atomChoices;
            atomChoices = this.getRandomAtoms(this.getDifficultyBasedOnScore(
                    this.getScore()));   
            /*Generate 4 random numbers from 0 to 3 to randomly add the possible
            answers to the buttons*/
            List<Integer> randIndex = AtomTriviaInterface.getRandomFour();
            QuestionMap question = this.getMethods().get(new Random().nextInt(3));
            for(int index : randIndex){
                choiceBtn.get(index).setText(
                    question.getChoiceMethod().invoke(atomChoices.get(index)));
                choiceBtn.get(index).setAtom(atomChoices.get(index));
            }
            choiceLbl.setText(question.getDescriptor());
            clueLbl.setText((String) 
                question.getClueMethod().invoke(atomChoices.get(randIndex.get(0))));
            this.setAnswers(atomChoices.get(randIndex.get(0)));
        } catch (Exception ex) {
            window.close();
        }
    }
    
    /**
     * Called whenever a user selects his answer. Checks wether the answer
     * is correct or not and acts accordingly.
     * @param <E>
     * @param answer 
     */
    @Override
    protected <E> void checkAnswer(E answer){
        if(this.verifyAnswer(answer)) {
            this.updateScore();
            this.setQuestion();
        }
        else{
            this.removeLife(); 
            this.setQuestion();
        }  
    }
    
    /**
     * Substracts one from the live count inherited by the superclass. Changes
     * the utmost right filled heart  to an empty heart. If it detects 
     * that the liveCount has reached 0 it invokes the finishGame method.
     */
    @Override
    protected void removeLife() {
        this.setLiveCount(this.getLiveCount() - 1);
        if(this.getLiveCount() <= 0){
            this.finishGame();
            return;
        }
        int col = 0 + Math.abs(this.getLiveCount());
        
        liveWrapper.get(col).getChildren().remove(0);
        ImageView imgv = new ImageView(new Image(
                StyleLoader.getEmptyHeartURL()));
        imgv.setPreserveRatio(true);
        imgv.setFitWidth(45);
        imgv.setFitHeight(45);
        
        GridPane.setConstraints(imgv, col, 0);
        liveWrapper.get(col).getChildren().add(imgv);
        imgv.fitWidthProperty().bind(liveWrapper.get(col).widthProperty());
        imgv.fitHeightProperty().bind(liveWrapper.get(col).heightProperty());
    }
    
    /**
     * Updates the score by one. Adds the necessary zeros (1 or 2) to make the
     * score measure at least 3 numbers. If the score measures 3 or more
     * characters no zeros are added.
     */
    @Override
    protected void updateScore(){
        this.setScore(this.getScore() + 1);
        String score = Integer.toString(this.getScore());
        switch(score.length()){
            case 1:
                scoreLbl.setText("00" + score);
                break;
            case 2:
                scoreLbl.setText("0" + score);
                break;
            default:
                scoreLbl.setText(score);
        } 
    }
    
    /**
     * Called when the livecount has reached 0 and therefore the game has ended.
     * Closes this window and displays a new stage showing the ending score.
     */
    @Override
    protected void finishGame() {
        window.close();
    }
}
