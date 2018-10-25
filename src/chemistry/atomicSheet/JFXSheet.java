package chemistry.atomicSheet;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import rendering.AtomicModelFX;
import rendering.CanvasFrame;

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
    private Label atomInfo[];
    
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
        layout.prefHeight(Integer.MAX_VALUE);
        layout.setAlignment(Pos.CENTER);
        
        atomInfo = new Label[4];
        for(int x = 0; x < atomInfo.length; x++){
            atomInfo[x] = new Label();
            atomInfo[x].setId("atomtxt");        
        }
        
        atomInfo[0].setText("Name: " + atomo.getName());
        atomInfo[1].setText("Symbol: " + atomo.getSymbol());
        atomInfo[2].setText("Atomic mass: " + Double.toString(
                atomo.getAtomicMass()));
        atomInfo[3].setText("Atomic number: " + Integer.toString(
                atomo.getAtomicNumber()));
        
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
        
        //---------------
        //Setting up layout. 
        //---------------      
        for(int x = 0; x < 4; x++){
            ColumnConstraints col = new ColumnConstraints();
            layout.getColumnConstraints().add(col);
        }
        layout.getColumnConstraints().get(0).setPercentWidth(10);
        layout.getColumnConstraints().get(3).setPercentWidth(10);
        
        
        /*First row. The layout that wraps the atomic model. This is done
        so the atomic model can be binded to the wrapper`s size triggering
        the resizing effect on the atomic model.*/
        modelContainer = new GridPane();
        modelContainer.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        
        GridPane.setConstraints(atomModel, 0, 0);
        modelContainer.getChildren().add(atomModel);
        atomModel.widthProperty().bind(modelContainer.widthProperty());
        atomModel.heightProperty().bind(modelContainer.heightProperty());
        
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(65);
        layout.getRowConstraints().add(row);
        
        GridPane.setConstraints(modelContainer, 0, 0, 4, 1);
        GridPane.setHalignment(modelContainer, HPos.CENTER);
        layout.getChildren().add(modelContainer);
        
        //Second section. All labels containing the information on the atom.
        int y = 1;
        for(; y < atomInfo.length + 1; y++){
            GridPane.setConstraints(atomInfo[y - 1], 0, y, 4, 1);
            GridPane.setHalignment(atomInfo[y - 1], HPos.CENTER);
            layout.getChildren().add(atomInfo[y - 1]);
        }
        
        //The two buttons to either open the next atom's sheet or show the 3D model.
        GridPane.setConstraints(nextBtn, 1, ++y);
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
    
    public void display() {
        window.show();
    }
    
    public void close() {
        window.close();
    }
}   
