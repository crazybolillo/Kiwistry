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

import chemistry.resourceloader.KiwiStyleLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Wraps the preview and adds a shadow effect to give a material design 
 * look.
 * @author https://github.com/AntonioBohne
 */
public class PreviewWrapper {

    private GridPane layout;
    private Preview preview;
 
    public PreviewWrapper(Preview preview){
        
        layout = new GridPane();
        layout.setId("wrapperPane");
        
        this.preview = preview;
        this.preview.getPreviewLayout().setId("wrappedPreviewPane");
        
        GridPane.setConstraints(this.preview.getPreviewLayout(), 0, 0);
        GridPane.setHgrow(this.preview.getPreviewLayout(), Priority.ALWAYS);
        GridPane.setVgrow(this.preview.getPreviewLayout(), Priority.ALWAYS);
        layout.getChildren().add(this.preview.getPreviewLayout());
        
        layout.getStylesheets().add(KiwiStyleLoader.getStyleSheet());
        layout.setPadding(new Insets(5));
    }
    
    /**
     * Returns the preview this PreviewWrapper is currently wrapping.
     * @return Preview.
     */
    public Preview getPreview(){
        return preview;
    }
    
    /**
     * Returns the layout which wraps the preview.
     * @return Pane that wraps the preview.
     */
    public Pane getLayout(){
        return layout;
    }
}
