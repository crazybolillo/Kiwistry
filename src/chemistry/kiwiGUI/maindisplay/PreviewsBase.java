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

import chemistry.kiwiGUI.PreviewWrapper;
import chemistry.utils.GridBoiler;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Base class that consists of a GridPane with a 3x3 grid. The pane is reziable
 * and will take as much space as possible. It forces classes that implement
 * it to set the Previews that will be displayed and adds them in the 
 * constructor. A filterEvt to answer user searches should also be implemented.
 * @author https://github.com/AntonioBohne
 */
public abstract class PreviewsBase {
    
    /**
     * Keeps track of all the preview wrappers.
     */
    protected List<PreviewWrapper> wrappedPreviews;
    protected GridPane layout = new GridPane();
    
    public PreviewsBase(double topSize, double bottomSize) throws Exception{
               
        layout = new GridPane();
        GridBoiler.addColumnConstraints(layout, 33.33, 33.33, 33.33);
        GridBoiler.addRowConstraints(layout, 33.33, 33.33, 33.33);
        
        wrappedPreviews = new ArrayList<>();
        this.setPreviews(topSize, bottomSize);
        
        int y = 0;
        for(int x = 0; x < 9 && x < wrappedPreviews.size(); ){
            for(int z = 0; z < 3 && x < 9 && x < wrappedPreviews.size(); 
                    z++, x++){
                GridPane.setConstraints(wrappedPreviews.get(x).getLayout(), z, y);
                GridPane.setFillWidth(wrappedPreviews.get(x).getLayout(), true);
                GridPane.setHgrow(wrappedPreviews.get(x).getLayout(), Priority.ALWAYS);
                GridPane.setFillHeight(wrappedPreviews.get(x).getLayout(), true);
                GridPane.setVgrow(wrappedPreviews.get(x).getLayout(), Priority.ALWAYS);
                layout.getChildren().add(wrappedPreviews.get(x).getLayout());
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
    
    /**
     * Returns the layout.
     * @return Pane.
     */
    public Pane getLayout(){
        return layout;
    }
    
    public abstract void setPreviews(double top, double bottom) throws Exception;
    public abstract void filterEvt(String filterText) throws Exception;
}
