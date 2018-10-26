package chemistry.game;

import chemistry.utils.GridBoiler;
import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class GameWindow extends GameInterface{

    private DefaultStage window;
    private Scene sc;
    
    private GridPane container;
    private GridPane topPane;
    private GridPane bottomPane;

    private Label clueLbl;
    private Label scoreLbl;
    
    private Label choiceLbl;
    private List<GameButton> choiceBtn;
    
    /**
     * Initializes all the layout components the window has.
     */
    public GameWindow() {

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
       
        sc = new Scene(container, 320, 500);
        sc.getStylesheets().add("/chemistry/game/gameStyle.css");
        
        window = new DefaultStage();
        window.setScene(sc);
        window.setResizable(false);
        window.setTitle("Atomic game");
        
        //Controlling window resizing
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());      
    }
       
    private void setTopPanel(){
    
        topPane = new GridPane();
        GridBoiler.addColumnConstraints(topPane, 15, 15, 15, 30, 25);
        GridBoiler.addRowConstraints(topPane, 20, 80);
        
       /*Creating hearts which signify how many lives the player has left
       and also add them directly to the topPane.*/
        for(int x = 0; x < 3; x++){
            ImageView img = new ImageView(new Image(
                    "/chemistry/game/fillHeart.png"));
            img.setFitWidth(45);
            img.setFitHeight(45);
            img.setPreserveRatio(true);
            
            GridPane.setConstraints(img, x, 0);
            topPane.getChildren().add(img);
        }
        clueLbl = new Label();
        clueLbl.setFont(new Font("Roboto", 30));
        clueLbl.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        GridPane.setConstraints(clueLbl, 1, 1, 3, 1);
        topPane.getChildren().add(clueLbl);
        
        scoreLbl = new Label("000");
        scoreLbl.setId("scoreLbl");
        scoreLbl.setTextAlignment(TextAlignment.RIGHT);
        
        GridPane.setConstraints(scoreLbl, 4, 0);
        topPane.getChildren().add(scoreLbl);
    }
    
    private void setBottomPanel(){
    
       bottomPane = new GridPane();
       choiceBtn = new ArrayList<>();
       for(int x = 0; x < 4; x++){
           GameButton btn = new GameButton();
           btn.setFont(new Font("Roboto", 20));
           btn.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
           btn.setOnAction(e ->{
               this.checkAnswer(btn.getAtom());
           });
           choiceBtn.add(btn);
       }
       choiceLbl = new Label();
       choiceLbl.setId("choice");
       choiceLbl.setTextAlignment(TextAlignment.CENTER);
       
       GridBoiler.addColumnConstraints(bottomPane, 50, 50);
       GridBoiler.addRowConstraints(bottomPane, 20, 40, 40);
       
       GridPane.setConstraints(choiceLbl, 0, 0, 2, 1);
       GridPane.setHalignment(choiceLbl, HPos.CENTER);
       bottomPane.getChildren().add(choiceLbl);
       
       int row = 1;
       int col = 1;
       int added = 0;
       for(Button btn : choiceBtn){        
           GridPane.setConstraints(btn, col - row, row);
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
     * 
     */
    @Override
    protected void setQuestion() {     
        try {
            List<Atom> atomChoices;
            atomChoices = this.getRandomAtoms();   
            /*Generate 4 random numbers from 0 to 3 to randomly add the possible
            answers to the buttons*/
            List<Integer> randIndex = GameInterface.getRandomFour();
            MethodMap question = this.getMethods().get(new Random().nextInt(3));
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
        
        topPane.getChildren().remove(col);
        ImageView imgv = new ImageView(new Image(
                "/chemistry/game/emptyHeart.png"));
        imgv.setFitWidth(45);
        imgv.setFitHeight(45);
        
        GridPane.setConstraints(imgv, col, 0);
        topPane.getChildren().add(imgv);
    }

    @Override
    protected void updateScore(){
        this.setScore(this.getScore() + 1);
        String sc = Integer.toString(this.getScore());
        switch(sc.length()){
            case 1:
                scoreLbl.setText("00" + sc);
                break;
            case 2:
                scoreLbl.setText("0" + sc);
                break;
            default:
                scoreLbl.setText(sc);
        } 
    }
    
    /**
     * Called when the livecount has reached 0 and therefore the game has ended.
     */
    @Override
    protected void finishGame() {
        window.close();
    }
}
