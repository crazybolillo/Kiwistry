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
    
}
