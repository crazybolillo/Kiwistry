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
package chemistry.kiwiGUI;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Wraps the preview and adds a shadow effect to give a material design 
 * look.
 * @author https://github.com/AntonioBohne
 */
public class PreviewWrapper extends GridPane{

    private Preview preview;
 
    public PreviewWrapper(Preview preview){
        
        this.setId("wrapperPane");
        
        this.preview = preview;
        this.preview.setId("wrappedPreviewPane");
        
        GridPane.setConstraints(this.preview, 0, 0);
        GridPane.setHgrow(this.preview, Priority.ALWAYS);
        GridPane.setVgrow(this.preview, Priority.ALWAYS);
        this.getChildren().add(this.preview);
        
        this.getStylesheets().add(this.getClass().
                getResource("WrappedPreviewStyle.css").toExternalForm());
        this.setPadding(new Insets(5));
    }
    
    /**
     * Returns the preview this PreviewWrapper is currently wrapping.
     * @return Preview.
     */
    public Preview getPreview(){
        return preview;
    }
}
