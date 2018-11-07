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

import chemistry.kiwiGUI.game.KiwiAtomTrivia;
import chemistry.gameLogic.AtomTriviaInterface.ATOM_DIFFICULTY;
import chemistry.kiwiGUI.defStage.DefaultStage;
import chemistry.kiwiGUI.VisualMessageQeue.KiwiMessage;
import chemistry.kiwiGUI.maindisplay.PreviewsBase;
import chemistry.kiwiGUI.maindisplay.AtomsPreview;
import chemistry.kiwiGUI.maindisplay.TestsPreview;
import chemistry.kiwiGUI.menu.TopBar;
import chemistry.kiwiGUI.settings.Settings;
import chemistry.resourceloader.KiwiStyleLoader;
import chemistry.resourceloader.LanguageLoader;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Starting window for the application. Contains a top menu for users to
 * select content and a main pane that displays the content. Fully resizable.
 * The main controller joins together all the different components of the 
 * GUI into one and controls them based on messages recieved from the 
 * VisualMessageQeue.
 * @author https://github.com/AntonioBohne
 */
public class VisualController extends Application{
    
    private GridPane container;
    private TopBar topBar;
    private PreviewsBase mainDisplay;

    private Scene sc;
    private DefaultStage window;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        container = new GridPane();
        container.maxWidth(Integer.MAX_VALUE);
        container.maxHeight(Integer.MAX_VALUE);
        container.minWidth(0);
        container.minHeight(0);
        container.getRowConstraints().add(new RowConstraints(56));
        
        topBar = new TopBar();
        GridPane.setConstraints(topBar, 0, 0);
        GridPane.setHgrow(topBar, Priority.ALWAYS);
        GridPane.setVgrow(topBar, Priority.ALWAYS);
        container.getChildren().add(topBar);
        
        mainDisplay = new AtomsPreview(80, 20);
        this.addToMainDisplay(mainDisplay.getLayout());

        sc = new Scene(container, 800, 600);
        sc.getStylesheets().add(KiwiStyleLoader.getStyleSheet());
        window = new DefaultStage();
        window.setTitle("Kiwimistry");
        window.setScene(sc);
        window.setResizable(true);
        window.show();
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });
        
        window.setMinWidth(800);
        window.setMinHeight(600);
       
        topBar.sections.get(0).requestFocus();
        
        /**
         * Set listener that will control the whole view depending on the 
         * messages sent to it. Most if not all nodes send messages to the
         * message loader when clicked. In some cases if the last message
         * recieved is the same as the new message recieved nothing will
         * be done.
         */
        VisualMessageQeue.MESSAGE_TRIGGER.addListener(e ->{
            KiwiMessage msg = VisualMessageQeue.getLastMessage();
            this.processMessage(msg);
        });
    }
    
    
    /**
     * Adds the node to the main display section of the screen.
     */
    private void addToMainDisplay(Pane node){
        GridPane.setConstraints(node, 0, 1);
        GridPane.setHgrow(node, Priority.ALWAYS);
        GridPane.setVgrow(node, Priority.ALWAYS);
        container.getChildren().add(node);
    }
    
    /**
     * Method called whenever a message sent by one of the nodes in the 
     * application needs to be adressed.
     * @param msg Message wrapper that contains all the necessary information.
     */
    private void processMessage(KiwiMessage msg) {
        try{
            switch(msg.getMessageType()){
                case SHOW_ATOM_SCR:
                    this.setAtomDisplay();
                    break;

                case SHOW_MOLECULE_SCR:
                    this.setMoleculeDisplay();
                    break;

                case SHOW_LEWIS_SCR:
                    this.setLewisDisplay();
                    break;

                case SHOW_TEST_SCR:
                    this.setTestDisplay();
                    break;

                case SHOW_SETTINGS_SCR:
                    this.onSettingsEvent();
                    break;

                case SHOW_ATOM_SHEET_SCR:
                    JFXSheet sheet = new JFXSheet(msg.getArguments().get(0));
                    sheet.display();
                    break;
                    
                case SHOW_ATOM_GAME_SCR:
                    
                    ATOM_DIFFICULTY dif = ATOM_DIFFICULTY.EASY;
                    if(msg.getArguments().get(0).equals(
                            LanguageLoader.getAppTranslation("easyLbl")))
                        dif = ATOM_DIFFICULTY.EASY;
                    
                    if(msg.getArguments().get(0).equals(
                            LanguageLoader.getAppTranslation("normalLbl")))
                        dif = ATOM_DIFFICULTY.MEDIUM;
                    
                    if(msg.getArguments().get(0).equals(
                            LanguageLoader.getAppTranslation("hardLbl")))
                        dif = ATOM_DIFFICULTY.HARD;
                    
                    
                    KiwiAtomTrivia trivia = new KiwiAtomTrivia(dif);
                    trivia.display();
                    break;
                    
                case SEARCH_FLD_EVT:
                    this.onSearchEvent(msg.getArguments().get(0));
                    break;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     *  
     */
    private void setAtomDisplay() throws Exception{
        
        this.topBar.searchBar.setDisable(false);
        
        container.getChildren().remove(1);
        mainDisplay = new AtomsPreview(80, 20);
        this.addToMainDisplay(mainDisplay.getLayout());
    }
    
    /**
     * 
     */
    private void setMoleculeDisplay(){}
    
    /**
     * 
     */
    private void setLewisDisplay(){}
    
    /**
     * 
     */
    private void setTestDisplay() throws Exception{
        topBar.searchBar.setDisable(true);
        container.getChildren().remove(1);
        mainDisplay = new TestsPreview(80, 20);
        this.addToMainDisplay(mainDisplay.getLayout());
    }
    
    
    /**
     * Changes the main display to a settings layout where the user
     * can configure the application. This is called whenever the user
     * saves the settings or goes to the settings.
     */
    private void onSettingsEvent(){
        sc.getStylesheets().remove(0);
        sc.getStylesheets().add(KiwiStyleLoader.getStyleSheet());
        topBar.getStylesheets().set(0, KiwiStyleLoader.getStyleSheet());
                        
        topBar.searchBar.setDisable(true);
        container.getChildren().remove(1);
        this.addToMainDisplay(new Settings());
    }
    
    /**
     * This method is called whenever the controller gets a "SEARCH_FLD_EVT" message
     * which signifies the user has typed something into the search bar. 
     * This method just calls the main displays filterEvt method which all
     * preview panes must implement. This message can not be recieved
     * when a non-preview pane is being shown since the text-field is disabled
     * when that happens.
     * @param searchTxt Text that the user typed into the search bar
     */
    private void onSearchEvent(String searchText) throws NoSuchFieldException,
            SQLException, Exception{
        mainDisplay.filterEvt(searchText);
    }
    
    public static void main(String[] args){
        launch(args);
    } 
    
}
