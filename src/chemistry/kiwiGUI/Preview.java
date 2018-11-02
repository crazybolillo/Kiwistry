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

import chemistry.rendering.ResizableLabel;
import chemistry.utils.GridBoiler;
import javafx.scene.Cursor;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author https://github.com/AntonioBohne
 * @param <E>
 */
public class Preview <E extends Node> extends GridPane{

    protected E previewNode;
    protected ResizableLabel description;
    
    public Preview(E preview, String previewText, double topSize, 
            double bottomSize){
        
        previewNode = preview;
        
        description = new ResizableLabel();
        description.setId("previewLbl");
        description.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        description.setMinSize(0, 0);
        description.setText(previewText);
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);
        
        GridBoiler.addRowConstraints(this, topSize, bottomSize);
        
        this.addPreviewNode();
        
        GridPane.setConstraints(description, 0, 1);
        this.getChildren().add(description);
        
        this.setCursor(Cursor.HAND);
        this.setGridLinesVisible(true);
    }
    
    /**
     * Adds the node to the preview.
     */
    private void addPreviewNode(){
        GridPane.setConstraints(previewNode, 0, 0);
        GridPane.setHalignment(previewNode, HPos.CENTER);
        GridPane.setHgrow(previewNode, Priority.ALWAYS);
        GridPane.setFillWidth(previewNode, true);
        GridPane.setVgrow(previewNode, Priority.ALWAYS);
        GridPane.setFillHeight(previewNode, true);
        this.getChildren().add(previewNode);
    }
    
    /**
     * Establishes the relation between the text size and the label's height.
     * @param ratio The amount of times the text will be smaller than
     * the height.
     */
    public void setLabelRatio(double ratio){
        this.description.setSizeToHeightRatio(ratio);
    }
    
    /**
     * Sets the text that sits below the preview node.
     * @param text Text that will be displayed on the preview.
     */
    public void setLabelText(String text){
        this.description.setText(text);
    }
    
    /**
     * Removes the current node being displayed and adds the node passed
     * trough the parameters.
     * @param node Node that will be displayed.
     */
    public void setDisplayedNode(E node){
        this.getChildren().remove(previewNode);
        previewNode = node;
        this.addPreviewNode();
    }
}
