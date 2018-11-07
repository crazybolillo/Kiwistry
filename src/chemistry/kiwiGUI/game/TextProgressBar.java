package chemistry.kiwiGUI.game;

import chemistry.rendering.ResizableLabel;
import chemistry.utils.GridBoiler;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

/**
 * Class used to display a ProgressBar inside it. Used in the Game windows
 * to display the live counter inside a 
 * @author https://github.com/AntonioBohne
 */
public class TextProgressBar {
    
    public ProgressBar bar;
    public ResizableLabel text;
    public GridPane pane;
    
    public TextProgressBar(){
        bar = new ProgressBar();
        text = new ResizableLabel();
        text.setId("lifeBarText");
        
        pane = new GridPane();
        pane.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        pane.setMinSize(0, 0);
        GridBoiler.addColumnConstraints(pane, 100);
        GridBoiler.addRowConstraints(pane, 100);
        
        GridPane.setConstraints(bar, 0, 0);
        pane.getChildren().add(bar);
        
        GridPane.setConstraints(text, 0, 0);
        pane.getChildren().add(text);
    }
    
}
