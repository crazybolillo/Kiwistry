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
package chemistry.kiwiGUI.menu;

import chemistry.kiwiGUI.VisualMessageQeue;
import chemistry.kiwiGUI.VisualMessageQeue.MESSAGE_TYPE;
import chemistry.utils.GridBoiler;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Bar that appears at the top of the main display. Contains a search bar 
 * to filter out the main display content. It also allows users to navigate
 * trough sections by clicking on the different icons. Components have a
 * fixed size but will place themselves accordingly (left, right center) to 
 * keep the same size regardless of windows size.
 * @author https://github.com/AntonioBohne
 */
public class TopBar extends  GridPane{
    
    public TextField searchBar;
    public NodeGroup<Pane> sections;
    
    public TopBar(){
        
        super();
        this.setPadding(new Insets(15, 0, 15, 5));        
        this.getStylesheets().add(this.getClass().getResource(
                "topBarStyle.css").toExternalForm());
        GridBoiler.addRowConstraints(this, 100);
        GridBoiler.addColumnConstraints(this, 33, 1, 16.5, 16.5, 16.5, 16.5);
        
        searchBar = new TextField();
        searchBar.setPromptText("Search");
        searchBar.setPrefWidth(Integer.MAX_VALUE);
        searchBar.setOnKeyReleased(e ->{
            VisualMessageQeue.sendMessage(MESSAGE_TYPE.SEARCH_FLD_EVT, 
                    searchBar.getText());
        });
        
        
        GridPane.setConstraints(searchBar, 0, 0, 1, 3);
        GridPane.setHalignment(searchBar, HPos.LEFT);
        GridPane.setValignment(searchBar, VPos.CENTER);
        this.getChildren().add(searchBar);
        
        List<ImageView> imgv = new ArrayList<>();
        imgv.add(new ImageView(new Image(
                "chemistry/kiwiGUI/res/atomIcon.png")));
        imgv.add(new ImageView(new Image(
                "chemistry/kiwiGUI/res/moleculeIcon.png")));
        imgv.add(new ImageView(new Image(
                "chemistry/kiwiGUI/res/testIcon.png")));
        imgv.add(new ImageView(new Image(
                "chemistry/kiwiGUI/res/settingsIcon.png")));
        
        List<BorderPane> tempList = new ArrayList<>();
        for(int x = 0; x < imgv.size(); x++){
            
            imgv.get(x).setFitWidth(52);
            imgv.get(x).setFitHeight(52);
            imgv.get(x).setPreserveRatio(true);
            
            BorderPane pane = new BorderPane();
            pane.setId("unselectedItem");
            pane.setCenter(imgv.get(x));
            pane.setCursor(Cursor.HAND);
            
            GridPane.setConstraints(pane, x + 2, 0, 1, 1);
            GridPane.setVgrow(pane, Priority.ALWAYS);
            GridPane.setHgrow(pane, Priority.ALWAYS);
            this.getChildren().add(pane);
            
            tempList.add(pane);
        }
        
        sections = new NodeGroup<>();
        
        sections.add(tempList.get(0), MESSAGE_TYPE.SHOW_ATOM_SCR);
        sections.add(tempList.get(1), MESSAGE_TYPE.SHOW_MOLECULE_SCR);
        sections.add(tempList.get(2), MESSAGE_TYPE.SHOW_TEST_SCR);
        sections.add(tempList.get(3), MESSAGE_TYPE.SHOW_SETTINGS_SCR);
        
        sections.setSelected(tempList.get(0));
    }
}
