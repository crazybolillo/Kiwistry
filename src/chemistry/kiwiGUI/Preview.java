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
        
        GridPane.setConstraints(previewNode, 0, 0);
        GridPane.setHalignment(previewNode, HPos.CENTER);
        GridPane.setHgrow(previewNode, Priority.ALWAYS);
        GridPane.setFillWidth(previewNode, true);
        GridPane.setVgrow(previewNode, Priority.ALWAYS);
        GridPane.setFillHeight(previewNode, true);
        this.getChildren().add(previewNode);
        
        GridPane.setConstraints(description, 0, 1);
        this.getChildren().add(description);
        
        this.setCursor(Cursor.HAND);
    }
    
    /**
     * Establishes the relation between the text size and the label's height.
     * @param ratio The amount of times the text will be smaller than
     * the height.
     */
    public void setLabelRatio(double ratio){
        this.description.setSizeToHeightRatio(ratio);
    }
}
