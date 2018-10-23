package rendering;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 * This window contains a resizable canvas with the atomic model of the atom
 * passed trough the parameters.
 * @author https://github.com/AntonioBohne
 */
public class CanvasFrame {
    
    DefaultStage window;
    Scene sc;
    GridPane layout;
    
    AtomicModelFX canvas;
    private double size;
    
    public CanvasFrame(Atom atomo) throws Exception {
        
        size = 800;
        
        window = new DefaultStage();
        layout = new GridPane();

        canvas = new AtomicModelFX(size, atomo);
        
        layout.getChildren().add(canvas);
        
        sc = new Scene(layout, size, size);
        window.setScene(sc);
        window.setTitle(atomo.getName());
        window.setResizable(true);
        
        /**
         * Binds the window size with the container. Effectively
         * resizing the canvas together with the window if resized. The canvas
         * has its own action listener that will resize automatically when it 
         * detects its size has changed.
         */
        canvas.widthProperty().bind(sc.widthProperty());
        canvas.heightProperty().bind(sc.heightProperty());
        
        }
    
    public void display() {
        window.show();
        canvas.paintModel();
    }
    
    public void close() {
        window.close();
    }
    
}
