/* 
 * Copyright (C) 2018 Antonio---https://github.com/AntonioBohne
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package chemistry.kiwiGUI.maindisplay;

import chemistry.atoms.Atom;
import chemistry.kiwiGUI.Preview;
import chemistry.kiwiGUI.PreviewWrapper;
import chemistry.rendering.AtomicModelWrapper;
import chemistry.utils.GridBoiler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Layout that shows a 3x3 grid of wrapped previews. By default the application
 * starts in the Atoms' section so this PreviewPane displays random atomic
 * models upon start.
 * @author https://github.com/AntonioBohne
 */
public class PreviewsPane extends GridPane{
    
    /**
     * Keeps track of all the preview wrappers.
     */
    private List<PreviewWrapper> wrappedPreviews;
    
    public PreviewsPane() throws SQLException, NoSuchFieldException, Exception{

        GridBoiler.addColumnConstraints(this, 33.33, 33.33, 33.33);
        GridBoiler.addRowConstraints(this, 33.33, 33.33, 33.33);
        
        List<Preview> preview = new ArrayList<>();
        wrappedPreviews = new ArrayList<>();
        for(int x = 0; x < 9; x++){
            Atom atom = new Atom(new Random().nextInt(108) + 1 + x);
            preview.add(new Preview(new AtomicModelWrapper(50, atom),
                    atom.getName(), 80, 20));
            preview.get(x).setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            preview.get(x).setMinSize(0, 0);
            
            wrappedPreviews.add(new PreviewWrapper(preview.get(x)));
        }
        
        int y = 0;
        for(int x = 0; x < wrappedPreviews.size(); ){
            for(int z = 0; z < 3 && x < wrappedPreviews.size(); z++, x++){
                GridPane.setConstraints(wrappedPreviews.get(x), z, y);
                GridPane.setFillWidth(wrappedPreviews.get(x), true);
                GridPane.setHgrow(wrappedPreviews.get(x), Priority.ALWAYS);
                GridPane.setFillHeight(wrappedPreviews.get(x), true);
                GridPane.setVgrow(wrappedPreviews.get(x), Priority.ALWAYS);
                this.getChildren().add(wrappedPreviews.get(x));
            }
            y++;
        }
    }
    
    /**
     * Returns the list of PreviewWrappers this pain contains. This allows
     * the preview content to be changed as needed.
     * @return List of PreviewWrappers the panel contains and displays.
     */
    public List<PreviewWrapper> getPanePreviews(){
        return wrappedPreviews;
    }
}
