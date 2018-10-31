package chemistry.basicGUI;

import chemistry.defStage.DefaultStage;
import chemistry.resourceloader.LanguageLoader;
import chemistry.resourceloader.StyleLoader;
import java.util.Arrays;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

    private Label styleLbl;
    private ComboBox styleBox;
    
    private Label langLbl;
    private ComboBox langBox;
    
    private Button saveBtn;
    
    public Settings(){
        
        this.setStyleForm();
        this.setLangForm();
        this.setButton();
        
        layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        
        GridPane.setConstraints(styleLbl, 0, 0);
        GridPane.setHalignment(styleLbl, HPos.LEFT);
        layout.getChildren().add(styleLbl);
        
        GridPane.setConstraints(styleBox, 0, 1);
        GridPane.setHalignment(styleBox, HPos.CENTER);
        layout.getChildren().add(styleBox);
        
        GridPane.setConstraints(langLbl, 0, 2);
        GridPane.setHalignment(langLbl, HPos.LEFT);
        layout.getChildren().add(langLbl);
        
        GridPane.setConstraints(langBox, 0, 3);
        GridPane.setHalignment(langBox, HPos.CENTER);
        layout.getChildren().add(langBox);
        
        GridPane.setConstraints(saveBtn, 0, 4);
        GridPane.setHalignment(saveBtn, HPos.CENTER);
        layout.getChildren().add(saveBtn);
         
        sc = new Scene(layout, 300, 200);
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
    private void setStyleForm(){
        
        styleLbl = new Label();
        styleLbl.setText(LanguageLoader.getAppTranslation("themeLbl"));
        
        styleBox = new ComboBox();
        styleBox.setPrefWidth(260);
        styleBox.setPromptText("Style");
        Arrays.asList(StyleLoader.STYLE_TYPE.values()).forEach(
                e -> styleBox.getItems().add(e.toString()));
        styleBox.getSelectionModel().select(
                StyleLoader.getCurrentStyle().toString());
    }
    
    /**
     * Initializes the language box and adds all the possible languages to the
     * combo box.
     */
    private void setLangForm(){
        
        langLbl = new Label();
        langLbl.setText(LanguageLoader.getAppTranslation("langLbl"));
        
        langBox = new ComboBox();
        langBox.setPrefWidth(260);
        langBox.setPromptText("Language");
        Arrays.asList(LanguageLoader.LANGUAGE.values()).forEach(
                e -> langBox.getItems().add(e.toString()));
        langBox.getSelectionModel().select(
                LanguageLoader.getCurrentLanguage().toString());
    }
    
    private void setButton(){
        saveBtn = new Button();
        saveBtn.setPrefWidth(180);
        saveBtn.setText(LanguageLoader.getAppTranslation("saveLbl"));
        saveBtn.setOnAction(e ->{
            try{
                StyleLoader.setApplicationStyle(StyleLoader.getType(
                    styleBox.getSelectionModel().getSelectedItem().toString()));
                LanguageLoader.setCurrentLanguage(LanguageLoader.getLanguage(
                    langBox.getSelectionModel().getSelectedItem().toString()));
                window.close();
            }catch(NoSuchFieldException ex){
                window.close();
                ex.printStackTrace();
            }
        });
    }
}
