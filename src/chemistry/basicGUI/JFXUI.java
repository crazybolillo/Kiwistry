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
package chemistry.basicGUI;

import chemistry.sql.SQLReader;
import chemistry.basicGUI.JFXSheet;
import chemistry.defStage.DefaultStage;
import chemistry.basicGUI.AtomTrivia;
import chemistry.resourceloader.LanguageLoader;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import chemistry.resourceloader.StyleLoader;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class JFXUI extends Application{

    DefaultStage window;
    Scene sc;
    GridPane bodyPanel;
    
    Label headerTxt;
    TextField searchFld;
    
    ObservableList<String> defData;
    ListView<String> listVw;
    
    Button randomBtn;
    Button gameBtn;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = new DefaultStage();
        bodyPanel = new GridPane();
        bodyPanel.setAlignment(Pos.CENTER);
        
        headerTxt = new Label();
        headerTxt.setText("Atomic models");
        headerTxt.setId("header");
        headerTxt.setOnMouseClicked(e ->{
            Settings settings = new Settings();
            settings.displayAndWait();
            sc.getStylesheets().remove(0);
            sc.getStylesheets().add(StyleLoader.getGeneralStyleSheetURL());
            try{
                defData = SQLReader.getAtomNames();
                listVw.setItems(defData);
                this.updateComponentsText();
            }catch(Exception ex){}
        });
        
        searchFld = new TextField();
        searchFld.setPrefWidth(250);
        searchFld.setOnMouseClicked(e ->{
            listVw.getSelectionModel().clearSelection();
        });
        searchFld.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.DOWN){
                listVw.requestFocus();
                listVw.getSelectionModel().select(0);
                return;
            }
            this.filterEvt();
        });
        
        defData = SQLReader.getAtomNames();
        
        listVw = new ListView<>();
        listVw.setPrefSize(250, Integer.MAX_VALUE);
        listVw.setItems(defData);
        listVw.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.ENTER){
                try{
                    JFXSheet sheet = new JFXSheet(
                    listVw.getSelectionModel().getSelectedItem());
                    sheet.display();
                }catch(Exception ex){}
            }
        });
        listVw.setOnMouseClicked(e ->{
            if(e.getClickCount() > 1){
                try{
                    JFXSheet sheet = new JFXSheet(
                        listVw.getSelectionModel().getSelectedItem());
                    sheet.display();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
                
        randomBtn = new Button();
        randomBtn.setPrefWidth(150);
        randomBtn.setOnAction(e ->{
            Random random = new Random();
            try {
                JFXSheet sheet = new JFXSheet(new Random().nextInt(119));
                sheet.display();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        gameBtn = new Button();
        gameBtn.setPrefWidth(randomBtn.getPrefWidth());
        gameBtn.setOnAction(e ->{
            AtomTrivia gmWin = new AtomTrivia();
            gmWin.display();
        });
        
        this.updateComponentsText();
        
        Node components[] = new Node[5];
        components[0] = headerTxt;
        components[1] = searchFld;
        components[2] = listVw;
        components[3] = randomBtn;
        components[4] = gameBtn;
            
        for(int x = 0; x < components.length; x++){
            GridPane.setConstraints(components[x], 0, x);
            GridPane.setHalignment(components[x], HPos.CENTER);
            GridPane.setHgrow(components[x], Priority.ALWAYS);
            bodyPanel.getChildren().add(components[x]);
        }
        
        bodyPanel.setVgap(10);
        bodyPanel.setPadding(new Insets(10, 15, 10, 15));

        sc = new Scene(bodyPanel, 280, 530);
        sc.getStylesheets().add(StyleLoader.getGeneralStyleSheetURL());
        
        window.setScene(sc);
        window.setTitle("Atomic Models");
        window.setWidth(280);
        window.setHeight(530);
        window.setResizable(true);
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });        
        
        //Following code is all for resizing purposes.
        window.setMinWidth(200);
        window.setMinHeight(290);
 
        searchFld.setMinWidth(window.getMinWidth() - 50);        
        searchFld.setMinHeight(35);
        
        listVw.setMinWidth(searchFld.getMinWidth());
        listVw.setMinHeight(40);
        
        randomBtn.setMinWidth(searchFld.getMinWidth());
        randomBtn.setMinHeight(searchFld.getMinHeight());
        
        gameBtn.setMinWidth(searchFld.getMinWidth());
        gameBtn.setMinHeight(searchFld.getMinHeight());
        
        //Display window and request focus so prompt text appears on text field.
        window.show();
        listVw.requestFocus();
    }
    
    /**
     * Sets the text for all the GUI components based on the current language.
     * Called during initialization and whenever the user changes languages
     * so the GUI reacts accordingly.
     */
    private void updateComponentsText(){
        searchFld.setPromptText(LanguageLoader.getAppTranslation("searchLbl"));
        randomBtn.setText(LanguageLoader.getAppTranslation("randomLbl"));
        gameBtn.setText(LanguageLoader.getAppTranslation("playLbl"));
    }
    
    /**
     * Called everytime the user types into the search bar. Filtering out
     * atoms' names that do not contain the letters in the search bar.
     */
    private void filterEvt() {
        if(searchFld.getText().isEmpty()){
            listVw.setItems(defData);
            return;
        }
        ObservableList<String> filterData = FXCollections.observableArrayList();
        for(String str : defData){
            if(str.toLowerCase().contains(searchFld.getText().toLowerCase())){
                filterData.add(str);
            } 
        }
        if(filterData.isEmpty())
            filterData.add("No results found");
        listVw.setItems(filterData);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
