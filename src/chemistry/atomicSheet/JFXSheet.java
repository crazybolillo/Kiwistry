package chemistry.atomicSheet;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import chemistry.utils.GridBoiler;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import rendering.AtomicModelFX;
import rendering.CanvasFrame;
import rendering.ResizableLabel;
import rendering.StyleLoader;

/**
 * This class is used to display information about an atom. It includes a
 * 2D model and several labels with general information like name, atomic number
 * mass etc...
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
        GridBoiler.addRowConstraints(layout, 60, 8, 8, 8, 8);
        
        GridPane.setConstraints(modelContainer, 0, 0, 4, 1);
        layout.getChildren().add(modelContainer);
        
        int y = 1;
        for(GridPane pane : infoWrappers){
            GridPane.setConstraints(pane, 1, y, 2, 1);
            layout.getChildren().add(pane);
            y++;
        }
        
        GridPane.setConstraints(nextBtn, 1, y);
        GridPane.setHalignment(nextBtn, HPos.CENTER);
        layout.getChildren().add(nextBtn);
        
        GridPane.setConstraints(view3DBtn, 2, y);
        GridPane.setHalignment(view3DBtn, HPos.CENTER);
        layout.getChildren().add(view3DBtn);
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(15);
        layout.setVgap(7);
        
        //Set scene
        sc = new Scene(layout, 320, 520);
        sc.getStylesheets().add(StyleLoader.getGeneralStyleSheetURL());
        
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
        
        for(GridPane pane : infoWrappers){
            pane.setMinHeight(pane.getHeight());
            pane.setMinWidth(pane.getWidth());
        }
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
            GridPane.setConstraints(atomInfo[x], 0, 0);
            pane.getChildren().add(atomInfo[x]);
            
            atomInfo[x].setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            atomInfo[x].setMinSize(atomInfo[x].getWidth(), atomInfo[x].getHeight());
            atomInfo[x].setAlignment(Pos.CENTER);
            
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
        nextBtn.setText("<- Atom");
        nextBtn.setOnAction(e-> {
            try{
                int atomNum = atomo.getAtomicNumber() - 1;
                if(atomNum <= 0) atomNum = 118;
        
                JFXSheet sheet = new JFXSheet(atomNum);
                if(window.isMaximized())
                    sheet.displayMaximized();
                else
                    sheet.display();
                  
                this.close();
            }catch(Exception ex){}
        });
        
        view3DBtn = new Button();
        view3DBtn.setText("-> Atom");
        view3DBtn.setOnAction(e ->{
            try{
                int atomNum = atomo.getAtomicNumber() + 1;
                if(atomNum > 118) atomNum = 1;
        
                JFXSheet sheet = new JFXSheet(atomNum);
                if(window.isMaximized())
                    sheet.displayMaximized();
                else
                    sheet.display();
                  
                this.close();
            }catch(Exception ex){}
        });
    }

    public void display() {
        window.show();
    }
    
    public void displayMaximized(){
        window.show();
        window.setMaximized(true);
    }
    
    public void close() {
        window.close();
    }
}   
