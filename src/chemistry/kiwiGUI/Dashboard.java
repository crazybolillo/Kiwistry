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

import chemistry.defStage.DefaultStage;
import chemistry.kiwiGUI.maindisplay.PreviewsPane;
import chemistry.kiwiGUI.sidemenu.SideMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Starting window for the application. Contains a side menu for users to
 * select content and a main pane that displays the content. Fully resizable.
 * @author https://github.com/AntonioBohne
 */
public class Dashboard extends Application{
    
    private GridPane container;
    private SideMenu sideMenu;
    private GridPane mainDisplay;

    private Scene sc;
    private DefaultStage window;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        container = new GridPane();
        container.maxWidth(Integer.MAX_VALUE);
        container.maxHeight(Integer.MAX_VALUE);
        container.minWidth(0);
        container.minHeight(0);
        container.getColumnConstraints().add(new ColumnConstraints(150));
        
        sideMenu = new SideMenu();
        GridPane.setConstraints(sideMenu, 0, 0);
        GridPane.setVgrow(sideMenu, Priority.ALWAYS);
        container.getChildren().add(sideMenu);
        
        mainDisplay = new PreviewsPane();
        GridPane.setConstraints(mainDisplay, 1, 0);
        GridPane.setHgrow(mainDisplay, Priority.ALWAYS);
        GridPane.setVgrow(mainDisplay, Priority.ALWAYS);
        container.getChildren().add(mainDisplay);
        
        sc = new Scene(container, 800, 600);
        sc.getStylesheets().add(this.getClass().getResource("AppStyle.css").
                toExternalForm());
        window = new DefaultStage();
        window.setTitle("MASTER KIWI-DEVELOPMENT");
        window.setScene(sc);
        window.setResizable(true);
        window.show();
        
        window.setMinWidth(800);
        window.setMinHeight(600);
    }
    
    public static void main(String[] args){
        launch(args);
    } 
    
}
