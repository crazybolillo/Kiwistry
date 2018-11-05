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
package chemistry.kiwiGUI.game;

import chemistry.kiwiGUI.Preview;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class GameButtonWrapper {

    private GridPane layout;
    private GameButton button;
 
    public GameButtonWrapper(GameButton button){
        
        layout = new GridPane();
        layout.setId("wrapperPane");
        
        this.button = button;
        this.button.setId("wrappedPreviewPane");
        
        GridPane.setConstraints(this.button, 0, 0);
        GridPane.setHgrow(this.button, Priority.ALWAYS);
        GridPane.setVgrow(this.button, Priority.ALWAYS);
        layout.getChildren().add(this.button);
        
        layout.getStylesheets().add("chemistry/kiwiGUI/WrappedPreviewStyle.css");
        layout.setPadding(new Insets(5));
        layout.setCursor(Cursor.HAND);
    }
    
    /**
     * Returns the Button this ButtonWrapper is currently wrapping.
     * @return Preview.
     */
    public GameButton getPreview(){
        return button;
    }
    
    /**
     * Returns the layout which wraps the preview.
     * @return Pane that wraps the preview.
     */
    public Pane getLayout(){
        return layout;
    }
    
}
