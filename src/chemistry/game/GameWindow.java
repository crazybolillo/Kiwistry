package chemistry.game;

import chemistry.defStage.DefaultStage;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class GameWindow {
    
    private DefaultStage window;
    private Scene sc;
    
    private GridPane container;
    private GridPane topPane;
    private GridPane bottomPane;
    
    private List<ImageView> lives;
    private int liveCnt = 3; 
            
    private Node gameClue;
    private List<Class> clueType;
    
    private Label choiceCategory;
    private List<Button> choiceBtn;
    
    public GameWindow(){
        
        window = new DefaultStage();
        
         
        //---------
        //Setting up components for the top panel. 
        //---------
        lives = new ArrayList<>();
        for(int x = 0; x < 3; x++){
            Image img = new Image("/chemistry/game/fillHeart.png");
            
            ImageView imgvw = new ImageView();
            imgvw.setImage(img);
            imgvw.setFitWidth(40);
            imgvw.setFitHeight(40);
            imgvw.setPreserveRatio(true);
            
            lives.add(imgvw);
        }
        
        /*The following List will later on be used to generate a "clue" based
        on a random number from 0 to 3. Reflection will be used.*/
        clueType = new ArrayList<>();
        clueType.add(int.class);
        clueType.add(double.class);
        clueType.add(String.class);
        clueType.add(Canvas.class);
        
        //-----------
        //Setting up components for the panel with user choices
        //-----------
        choiceCategory = new Label();
        
        choiceBtn = new ArrayList<>();
        for(int x = 0; x < 3; x++){
           Button btn = new Button();
           choiceBtn.add(btn);
        }
                
        //-------------
        //Set panel that displays the lives the user has and the clue.
        //-------------
        for(int x = 0; x < 2; x++){
            RowConstraints row = new RowConstraints();
            topPane.getRowConstraints().add(row);
        }
        topPane.getRowConstraints().get(0).setPrefHeight(50);
        topPane.getRowConstraints().get(1).setPrefHeight(250);
        
        for(int x = 0; x < lives.size(); x++){
            GridPane.setConstraints(lives.get(x), x, 0);
            topPane.getChildren().add(lives.get(x));
        }

        //----------------
        //Set panel that displays the choices
        //----------------
        for(int x = 0; x < 3; x++){
            RowConstraints row = new RowConstraints();
            bottomPane.getRowConstraints().add(row);
        }        
        
        
        //---------
        //Set panel that holds two other panels
        //----------
        GridPane.setConstraints(topPane, 0, 0);
        container.getChildren().add(topPane);
        
        GridPane.setConstraints(bottomPane, 0, 1);
        container.getChildren().add(bottomPane);
        
        
        sc = new Scene(container, 350, 500);
        sc.getStylesheets().add("/chemistry/start/JFXStyle.css");
        
        window.setScene(sc);
        window.setResizable(false);
        window.setTitle("Atomic game");
    }
    
    public void display(){
        window.show();
        
//        while(liveCounter != 0){
//            
//        }
    }
    
    public void close(){
        window.close();
    }
    
    /**
     * 
     * @param clue 
     */
    private void setClue(Node clue){
        this.gameClue = clue;
        
        GridPane.setConstraints(gameClue, 0, 1);
        GridPane.setHalignment(gameClue, HPos.CENTER);
        topPane.getChildren().add(gameClue);
    }
    
    /**
     * 
     * @param choices 
     */
    private void setChoices(List<String> choices){
        for(int x = 0; x < 4; x++){
            choiceBtn.get(x).setText(choices.get(x));
        }
    }
}
