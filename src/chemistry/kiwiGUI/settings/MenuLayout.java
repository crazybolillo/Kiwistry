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
package chemistry.kiwiGUI.settings;

import chemistry.basicGUI.StyleLoader;
import chemistry.resourceloader.LanguageLoader;
import java.util.Arrays;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Layout used to display the different settings that can be modified by the 
 * user. It keeps the same size regardless of windows size. 
 * @author https://github.com/AntonioBohne
 */
public class MenuLayout extends GridPane{
    
    private Label styleLbl;
    private ComboBox styleBox;
    
    private Label langLbl;
    private ComboBox langBox;
    
    private Button saveBtn;
    
    public MenuLayout(){
        
        langLbl = new Label();
        langLbl.setText(LanguageLoader.getAppTranslation("langLbl"));
        
        langBox = new ComboBox();
        langBox.setPrefWidth(260);
        langBox.setPromptText("Language");
        Arrays.asList(LanguageLoader.LANGUAGE.values()).forEach(
                e -> langBox.getItems().add(e.toString()));
        langBox.getSelectionModel().select(
                LanguageLoader.getCurrentLanguage().toString());
        
        saveBtn = new Button();
        saveBtn.setPrefWidth(180);
        saveBtn.setText(LanguageLoader.getAppTranslation("saveLbl"));
        saveBtn.setOnAction(e ->{
            try{;
                LanguageLoader.setCurrentLanguage(LanguageLoader.getLanguage(
                    langBox.getSelectionModel().getSelectedItem().toString()));
            }catch(NoSuchFieldException ex){
                ex.printStackTrace();
            }
        });
        
        GridPane.setConstraints(langLbl, 0, 0);
        GridPane.setHalignment(langLbl, HPos.LEFT);
        this.getChildren().add(langLbl);
        
        GridPane.setConstraints(langBox, 0, 2);
        GridPane.setHalignment(langBox, HPos.CENTER);
        this.getChildren().add(langBox);
        
        GridPane.setConstraints(saveBtn, 0, 3);
        GridPane.setHalignment(saveBtn, HPos.CENTER);
        this.getChildren().add(saveBtn);
        
    }
}
