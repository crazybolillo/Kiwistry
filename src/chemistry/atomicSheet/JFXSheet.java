package chemistry.atomicSheet;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import javafx.geometry.Insets;
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
    private GridPane layout;
    private Scene sc;
    
    private Label atomInfo[];
    
    private AtomicModelFX atomModel;
    
    private Button increaseBtn;
    private Button view3DBtn;
    
    public JFXSheet(String atomName) throws Exception {
        
        Atom atomo = new Atom(atomName);
        
        window = new DefaultStage();
        layout = new GridPane();
        
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
        
        atomModel = new AtomicModelFX(300, atomo);
        atomModel.paintModel();
        
        increaseBtn = new Button();
        increaseBtn.setText("View 2D model");
        increaseBtn.setOnAction(e-> {
            CanvasFrame frame;
            try {
                frame = new CanvasFrame(atomo);
                frame.display();
            } catch (Exception ex) {
                
            }
        });
        
        view3DBtn = new Button();
        view3DBtn.setText("View 3D model");
        view3DBtn.setTooltip(new Tooltip("Not supported yet"));
        
        /**
         * Setting up layout and adding objects to it
         */
        ColumnConstraints colOne = new ColumnConstraints();
        colOne.setPrefWidth(200);
        layout.getColumnConstraints().add(colOne);
        
        ColumnConstraints colTwo = new ColumnConstraints();
        colTwo.setPrefWidth(150);
        layout.getColumnConstraints().add(colTwo);
        
        ColumnConstraints colThree = new ColumnConstraints();
        colThree.setPrefWidth(150);
        layout.getColumnConstraints().add(colThree);
        
        //
        RowConstraints[] rows = new RowConstraints[5];
        for(RowConstraints row : rows){
            row = new RowConstraints();
            row.setPrefHeight(360 / 5);
            layout.getRowConstraints().add(row);
        }
        
        //First column
        for(int row = 0; row < 4; row++){
            GridPane.setConstraints(atomInfo[row], 0, row);
            layout.getChildren().add(atomInfo[row]);
        }
        
        //Second column
        GridPane.setConstraints(atomModel, 1, 0, 2, 4);
        layout.getChildren().add(atomModel);
        
        GridPane.setConstraints(increaseBtn, 1, 4, 1, 1);
        layout.getChildren().add(increaseBtn);
        
        GridPane.setConstraints(view3DBtn, 2, 4, 1, 1);
        layout.getChildren().add(view3DBtn);
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(10);
        
        //Set scene
        sc = new Scene(layout, 520, 380);
        sc.getStylesheets().add("/chemistry/start/JFXStyle.css");
        
        window.setScene(sc);
        window.setResizable(false);
        window.setTitle(atomName);
    }
    
    public void display() {
        window.show();
    }
    
    public void close() {
        window.close();
    }
}   
