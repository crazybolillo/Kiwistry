package chemistry.start;

import chemistry.defStage.DefaultStage;
import chemistry.resourceloader.StyleLoader;
import chemistry.resourceloader.StyleLoader.STYLE_TYPE;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

/**
 * Class that shows the available settings to the user. Takes care of changing
 * them to whatever the user selects.
 * @author https://github.com/AntonioBohne
 */
public class Settings {
    
    private DefaultStage window;
    private Scene sc;
    private GridPane layout;

    private ComboBox styleBox;
    private ComboBox langBox;
    private Button saveBtn;
    
    public Settings(){
        
        this.setStyleBox();
        this.setLangBox();
        this.setButton();
        
        layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        
        GridPane.setConstraints(styleBox, 0, 0);
        GridPane.setHalignment(styleBox, HPos.CENTER);
        layout.getChildren().add(styleBox);
        
        GridPane.setConstraints(langBox, 0, 1);
        GridPane.setHalignment(langBox, HPos.CENTER);
        layout.getChildren().add(langBox);
        
        GridPane.setConstraints(saveBtn, 0, 2);
        GridPane.setHalignment(saveBtn, HPos.CENTER);
        layout.getChildren().add(saveBtn);
         
        sc = new Scene(layout, 300, 160);
        sc.getStylesheets().add(StyleLoader.getGeneralStyleSheetURL());
        
        window = new DefaultStage();
        window.setTitle("Settings");
        window.setScene(sc);
    }
    
    /**
     * Displays the window.
     */
    public void display(){
        window.show();
    }
    
    public void displayAndWait(){
        window.showAndWait();
    }
    
    /**
     * Closes the window.
     */
    public void close(){
        window.close();
    }
    
    /**
     * Initializes the style box and adds all the possible themes to the
     * combo box options.
     */
    private void setStyleBox(){
        styleBox = new ComboBox();
        styleBox.setPrefWidth(260);
        styleBox.setPromptText("Style");
        STYLE_TYPE[] style = STYLE_TYPE.values();
        for(STYLE_TYPE type : style)
            styleBox.getItems().add(type.toString());
        
        styleBox.setOnAction(e ->{
            StyleLoader.setApplicationStyle(StyleLoader.getType(
                styleBox.getSelectionModel().getSelectedItem().toString()));
            sc.getStylesheets().remove(0);
            sc.getStylesheets().add(StyleLoader.getGeneralStyleSheetURL());
        });
    }
    
    /**
     * Initializes the language box and adds all the possible languages to the
     * combo box.
     */
    private void setLangBox(){
        langBox = new ComboBox();
        langBox.setPrefWidth(260);
        langBox.setPromptText("Language");
        langBox.getItems().addAll("Spanish", "English");
    }
    
    private void setButton(){
        saveBtn = new Button();
        saveBtn.setPrefWidth(180);
        saveBtn.setText("Save");
        saveBtn.setOnAction(e ->{
            StyleLoader.setApplicationStyle(StyleLoader.getType(
                (styleBox.getSelectionModel().getSelectedItem().toString())));
            this.close();
        });
    }
}
