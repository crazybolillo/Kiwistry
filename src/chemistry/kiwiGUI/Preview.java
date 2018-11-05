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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * This class contains a node, usually some sort of graphic and a label below it
 * which is used to describe what the preview is all about. This is a 
 * critical component of the application as several sections of the GUI 
 * consist of just previews.
 * @author https://github.com/AntonioBohne
 * @param <E> Element that will be displayed.
 */
public class Preview <E extends Node> {

    private GridPane layout;
    protected E previewNode;
    protected ResizableLabel description;
    
    /**
     * Creates a preview object which will display the node and the text
     * below it.
     * @param preview Node that will be put on the preview.
     * @param previewText Text that will sit below the preview. 
     * @param topSize Percentage of the preview that the top node will take
     * (height)
     * @param bottomSize Percentage of the preview that the text will take
     * (height).
     */
    public Preview(E preview, String previewText, double topSize, 
            double bottomSize){
        
        this.initPane(topSize, bottomSize);
        
        previewNode = preview;
        
        this.initLabel();
        description.setText(previewText);
        
        this.addPreviewNode();
    }
    
    /**
     * Creates an empty preview with node nor text being displayed. 
     * @param topSize Percentage that the node in display will take.
     * @param BottomSize Percentage that the text that sits below the node
     * will display.
     */
    public Preview(double topSize, double BottomSize){
        this.initPane(topSize, BottomSize);
        previewNode = null;
        this.initLabel();
    }
    
    /**
     * Initializes the layout and sets how much space the node and the label
     * (height) will proportionally take.
     * @param topSize Percentage of the layout the node will take
     * @param bottomSize Percentage of the layout the label will take.
     */
    private void initPane(double topSize, double bottomSize){
        layout = new GridPane();
        GridBoiler.addColumnConstraints(layout, 100);
        GridBoiler.addRowConstraints(layout, topSize, bottomSize);
        layout.setCursor(Cursor.HAND);
    }
    
    /**
     * Initiaizes the label and adds it to the layout. This method does not 
     * add any text to the label.
     */
    private void initLabel(){
        description = new ResizableLabel();
        description.setId("previewLbl");
        description.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        description.setMinSize(0, 0);
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);
        
        GridPane.setConstraints(description, 0, 1);
        layout.getChildren().add(description);
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
        layout.getChildren().add(previewNode);
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
        if(layout.getChildren().contains(previewNode))
            layout.getChildren().remove(previewNode);
        previewNode = node;
        this.addPreviewNode();
    }
    
    /**
     * Returns the text currently being  displayed by the label.
     * @return Text being shown to the user.
     */
    public String getText(){
        return this.description.getText();
    }
    
    /**
     * Returns the Pane which contains the node and text.
     * @return Preview layout. 
     */
    public Pane getPreviewLayout(){
        return layout;
    }
}
