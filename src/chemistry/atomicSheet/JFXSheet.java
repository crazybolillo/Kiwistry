package chemistry.atomicSheet;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import chemistry.utils.GridBoiler;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import rendering.AtomicModelFX;
import rendering.CanvasFrame;
import rendering.ResizableLabel;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class JFXSheet {

    private DefaultStage window;
    private GridPane modelContainer;
    private GridPane layout;
    private Scene sc;
    
    private Atom atomo;
    
    private List<GridPane> infoWrappers;
    private ResizableLabel atomInfo[];
    
    private AtomicModelFX atomModel;
    
    private Button nextBtn;
    private Button view3DBtn;
    
    /**
     * Creates a new sheet with the atom´s information. 
     * @param atomNumber Atomic number. Must be from 1-118
     * @throws Exception If the atomic number is invalid an exception will be
     * thrown.
     */
    public JFXSheet(int atomNumber) throws Exception {
        atomo = new Atom(atomNumber);
        this.init();
    }
    
    /**
     * 
     * @param atomName
     * @throws Exception 
     */
    public JFXSheet(String atomName) throws Exception {
        atomo = new Atom(atomName);
        this.init();
    }
    
    private void init(){
        
        window = new DefaultStage();
        layout = new GridPane();
        layout.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        layout.setAlignment(Pos.CENTER);
        
        this.setAtomicModel();
        this.setAtomicInformation();
        this.setButtons();
        
        //---------------
        //Setting up layout. 
        //---------------      
        GridBoiler.addColumnConstraints(layout, 10, 40, 40, 10);
        GridBoiler.addRowConstraints(layout, 50, 8, 8, 8, 8, 8);
        
        GridPane.setConstraints(modelContainer, 0, 0, 4, 1);
        layout.getChildren().add(modelContainer);
        
        int y = 1;
        for(GridPane pane : infoWrappers){
            GridPane.setConstraints(pane, 1, y, 2, 1);
            GridPane.setHgrow(pane, Priority.ALWAYS);
            GridPane.setVgrow(pane, Priority.ALWAYS);
            GridPane.setFillHeight(pane, true);
            GridPane.setFillWidth(pane, true);
            y++;
        }
        
        GridPane.setConstraints(nextBtn, 1, ++y);
        GridPane.setHalignment(layout, HPos.CENTER);
        layout.getChildren().add(nextBtn);
        
        GridPane.setConstraints(view3DBtn, 2, y);
        GridPane.setHalignment(layout, HPos.CENTER);
        layout.getChildren().add(view3DBtn);
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(15);
        layout.setVgap(7);
        
        //Set scene
        sc = new Scene(layout, 320, 560);
        sc.getStylesheets().add("/chemistry/start/JFXStyle.css");
        
        window.setScene(sc);
        window.setWidth(sc.getWidth());
        window.setHeight(sc.getHeight());
        window.setResizable(true);
        window.setTitle(atomo.getName());
        
        //For resizing purposes
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());
        
        modelContainer.setMinWidth(modelContainer.getWidth());
        modelContainer.setMinHeight(modelContainer.getHeight());
    }
    
    /**
     * Initializes the AtomicModelCanvas, adds it to a wrapper and binds
     * its width and height to it to enable resizing.
     */
    private void setAtomicModel(){
        
        atomModel = new AtomicModelFX(190, atomo);
        atomModel.setOnMouseClicked(e ->{
            if(e.getClickCount() >= 2){
                try {
                    CanvasFrame frame;
                    frame = new CanvasFrame(atomo);
                    frame.display();
                } catch (Exception ex) {
                }
            }
        });
        atomModel.paintModel();
        
        modelContainer = new GridPane();
        modelContainer.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        GridPane.setConstraints(atomModel, 0, 0);
        modelContainer.getChildren().add(atomModel);
        
        atomModel.widthProperty().bind(modelContainer.widthProperty());
        atomModel.heightProperty().bind(modelContainer.heightProperty());
    }
    
    /**
     * Creates the labels which  display information about atoms and their
     * respective wrappers so their width and height can be binded to it. 
     * Enabling resizing.
     */
    private void setAtomicInformation(){
        
        infoWrappers = new ArrayList<>();
        atomInfo = new ResizableLabel[4];
        for(int x = 0; x < atomInfo.length; x++){
            atomInfo[x] = new ResizableLabel();
            
            GridPane pane = new GridPane();
            pane.prefHeight(Integer.MAX_VALUE);
            pane.prefWidth(Integer.MAX_VALUE);
            pane.minWidth(pane.getWidth());
            pane.minHeight(pane.getHeight());
            
            GridPane.setConstraints(atomInfo[x], 0, 0);
            pane.getChildren().add(atomInfo[x]);
            atomInfo[x].widthProperty().bind(pane.widthProperty());
            atomInfo[x].heightProperty().bind(pane.heightProperty());
            
            infoWrappers.add(pane);
        }
        
        atomInfo[0].setText("Name: " + atomo.getName());
        atomInfo[1].setText("Symbol: " + atomo.getSymbol());
        atomInfo[2].setText("Atomic mass: " + Double.toString(
                atomo.getAtomicMass()));
        atomInfo[3].setText("Atomic number: " + Integer.toString(
                atomo.getAtomicNumber()));
    }
    
    /**
     * Creates the two buttons in the layout and sets their action listeners.
     */
    private void setButtons(){
        
        nextBtn = new Button();
        nextBtn.setText("Next atom");
        nextBtn.setOnAction(e-> {
            try{
                int atomNum = atomo.getAtomicNumber() + 1;
                if(atomNum > 118) atomNum = 1;
                
                JFXSheet sheet = new JFXSheet(atomNum);
                sheet.display();
                
                this.close();
            }catch(Exception ex){}
        });
        
        view3DBtn = new Button();
        view3DBtn.setText("3D model");
        view3DBtn.setTooltip(new Tooltip("Not supported yet"));
    }
    
    public void display() {
        window.show();
        layout.setGridLinesVisible(true);
    }
    
    public void close() {
        window.close();
    }
}   
