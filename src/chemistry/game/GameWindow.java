package chemistry.game;

import boiler.fxlayouts.GeometryBoiler;
import boiler.fxlayouts.GridBoiler;
import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
    private List<Button> choiceBtn;
    /**
     * Initializes all the layout components the window has. It also loads
     * all the atom names found in the database to create the first question
     * for the user. 
     */
    public GameWindow() {

        //----------------
        //Setting top panel
        //----------------
        topPane = new GridPane();
        topPane.setGridLinesVisible(true);
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
        clueLbl.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        GridBoiler.addNode(topPane, clueLbl, 1, 1, 3, 1);
        
        scoreLbl = new Label("000");
        scoreLbl.setId("scoreLbl");
        scoreLbl.setTextAlignment(TextAlignment.RIGHT);
        GridBoiler.addNode(topPane, scoreLbl, new GeometryBoiler(HPos.RIGHT, null),
                topPane.getColumnConstraints().size() - 1);
        
       //-----------
       //Setting panel that offers the user choices
       //----------
       bottomPane = new GridPane();
       bottomPane.setGridLinesVisible(true);
       choiceBtn = new ArrayList<>();
       for(int x = 0; x < 4; x++){
           Button btn = new Button();
           btn.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
           btn.setOnAction(e ->{this.removeLife();});
           choiceBtn.add(btn);
       }
       choiceLbl = new Label("Guess the: ");
       choiceLbl.setTextAlignment(TextAlignment.CENTER);
       GridBoiler.addColumnConstraints(bottomPane, 50, 50);
       GridBoiler.addRowConstraints(bottomPane, 20, 40, 40);
       GridBoiler.addNode(bottomPane, choiceLbl, 
               new GeometryBoiler(HPos.CENTER, null), 0, 0, 2, 1);
       GridBoiler.addNodes(bottomPane, choiceBtn, 2, 1, new GeometryBoiler());
        
       //-------------------
       //Setting container panel
       //-------------------
       container = new GridPane();
       container.setAlignment(Pos.CENTER);
       GridBoiler.addRowConstraints(container, 60, 40);
       GridBoiler.addNode(container, topPane);
       GridBoiler.addNode(container, bottomPane, 0, 1);
       
        sc = new Scene(container, 320, 500);
        sc.getStylesheets().add("/chemistry/game/gameStyle.css");
        
        window = new DefaultStage();
        window.setScene(sc);
        window.setResizable(true);
        window.setTitle("Atomic game");
        
        //Controlling window resizing
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());      
    }
    
    /**
     * Displays the window. 
     */
    public void display(){
        window.show();
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
        Atom atomChoices[];
        try {
            atomChoices = this.getRandomAtoms();
        } catch (SQLException ex) {window.close();}
      
        
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
            
            this.setQuestion();
        }
        else{
            this.removeLife();
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
        
        topPane.getChildren().remove(col + 1);
        ImageView imgv = new ImageView(new Image(
                "/chemistry/game/emptyHeart.png"));
        imgv.setFitWidth(45);
        imgv.setFitHeight(45);
        GridBoiler.addNode(topPane, imgv, col, 0);
        
        this.setQuestion();
    }

    @Override
    protected void updateScore(){
        
    }
    
    /**
     * Called when the livecount has reached 0 and therefore the game has ended.
     */
    @Override
    protected void finishGame() {
        window.close();
    }
}
