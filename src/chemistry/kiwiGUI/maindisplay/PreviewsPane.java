package chemistry.kiwiGUI.maindisplay;

import chemistry.atoms.Atom;
import chemistry.kiwiGUI.Preview;
import chemistry.kiwiGUI.PreviewWrapper;
import chemistry.rendering.AtomicModelWrapper;
import chemistry.utils.GridBoiler;
import java.sql.SQLException;
import java.util.Random;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *  
 * @author https://github.com/AntonioBohne
 */
public class PreviewsPane extends GridPane{
    
    PreviewWrapper wrappedPreviews[];
    
    public PreviewsPane() throws SQLException, NoSuchFieldException, Exception{

        GridBoiler.addColumnConstraints(this, 33.33, 33.33, 33.33);
        GridBoiler.addRowConstraints(this, 33.33, 33.33, 33.33);
        
        Preview preview[] = new Preview[9];
        wrappedPreviews = new PreviewWrapper[preview.length];
        for(int x = 0; x < preview.length; x++){
            Atom atom = new Atom(new Random().nextInt(108) + 1 + x);
            preview[x] = new Preview(new AtomicModelWrapper(50, atom),
                    atom.getName(), 80, 20);
            preview[x].setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            preview[x].setMinSize(0, 0);
            
            wrappedPreviews[x] = new PreviewWrapper(preview[x]);
        }
        
        int y = 0;
        for(int x = 0; x < wrappedPreviews.length; ){
            for(int z = 0; z < 3 && x < wrappedPreviews.length; z++, x++){
                GridPane.setConstraints(wrappedPreviews[x], z, y);
                GridPane.setFillWidth(wrappedPreviews[x], true);
                GridPane.setHgrow(wrappedPreviews[x], Priority.ALWAYS);
                GridPane.setFillHeight(wrappedPreviews[x], true);
                GridPane.setVgrow(preview[x], Priority.ALWAYS);
                this.getChildren().add(wrappedPreviews[x]);
            }
            y++;
        }
    }
}