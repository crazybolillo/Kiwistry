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

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import chemistry.kiwiGUI.MessageLoader.KiwiMessage;
import chemistry.kiwiGUI.MessageLoader.MESSAGE_TYPE;
import chemistry.kiwiGUI.maindisplay.PreviewsPane;
import chemistry.kiwiGUI.menu.TopBar;
import chemistry.kiwiGUI.settings.MenuWindow;
import chemistry.rendering.AtomicModelWrapper;
import chemistry.sql.SQLReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Starting window for the application. Contains a side menu for users to
 * select content and a main pane that displays the content. Fully resizable.
 * The main controller joins together all the different components of the 
 * GUI into one and controls them based on messages recieved when components
 * are clicked.
 * @author https://github.com/AntonioBohne
 */
public class MainController extends Application{
    
    private List<String> atomNames;
    
    private GridPane container;
    private TopBar topBar;
    private PreviewsPane mainDisplay;

    private Scene sc;
    private DefaultStage window;
    
    private MESSAGE_TYPE lastMessage = MESSAGE_TYPE.SHOW_ATOM_SCR;
    private int lastSelectedIndex;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        atomNames = SQLReader.getAtomNames();
        
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
        
        mainDisplay = new PreviewsPane();
        this.addToMainDisplay(mainDisplay);

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
       
        topBar.sections.get(0).requestFocus();
        
        /**
         * Set listener that will control the whole view depending on the 
         * messages sent to it. Most if not all nodes send messages to the
         * message loader when clicked. In some cases if the last message
         * recieved is the same as the new message recieved nothing will
         * be done.
         */
        MessageLoader.MESSAGE_TRIGGER.addListener(e ->{
            KiwiMessage msg = MessageLoader.getLastMessage();
            if(msg.getMessageType().equals(lastMessage)) 
                return;
            switch(msg.getMessageType()){
                case SHOW_ATOM_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_ATOM_SCR;
                    this.setAtomDisplay();
                    break;
                    
                case SHOW_MOLECULE_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_MOLECULE_SCR;
                    this.setMoleculeDisplay();
                    break;
                    
                case SHOW_LEWIS_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_LEWIS_SCR;
                    this.setLewisDisplay();
                    break;
                    
                case SHOW_TEST_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_TEST_SCR;
                    this.setTestDisplay();
                    break;
                    
                case SHOW_SETTINGS_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_SETTINGS_SCR;
                    this.onSettingsEvent();
                    break;
                    
                case SHOW_PREVIEW_SCR:
                    lastMessage = MESSAGE_TYPE.SHOW_PREVIEW_SCR;
                    this.onPreviewEvt();
                    break;
                    
                case SEARCH_FLD_EVT:
                    try{
                        this.onSearchEvent(msg.getArguments().get(0));
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    break;
            }
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
     *  
     */
    private void setAtomDisplay(){
        
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
    private void setTestDisplay(){}
    
    
    /**
     * Changes the main display to a settings layout where the user
     * can configure the application. Once the user saves the settings
     * a message is sent to the main controller that requests the main 
     * displayed to be put in again.
     */
    private void onSettingsEvent(){
        
        lastSelectedIndex = topBar.sections.getPreviousSelectedIndex();
        container.getChildren().remove(mainDisplay);
        this.addToMainDisplay(new MenuWindow());
        
    }
    
    /**
     * This method is used to remove the current layout from the main display
     * and add the layout with the previews back to the main display
     * section. 
     */
    private void onPreviewEvt(){
        container.getChildren().remove(1);
        this.addToMainDisplay(mainDisplay);
        topBar.sections.setSelected(topBar.sections.get(lastSelectedIndex));
    }
    
    /**
     * This method is called whenever the controller gets a "SEARCH_FLD_EVT" message
     * which signifies the user has typed something into the search field. This
     * method gathers all the information of the section the user is in and
     * proceeds to display the first nine results which contains the string
     * the user has typed in.
     * @param searchTxt Text that the user typed into the search bar
     */
    private void onSearchEvent(String searchText) throws NoSuchFieldException,
            SQLException, Exception{
        
        List<String> filterList = new ArrayList<>();
        for(String str : atomNames){
            if(str.toLowerCase().contains(searchText.toLowerCase())){
                filterList.add(str);
            }
        }
        
        int x = 0;
        for(; x < filterList.size() && 
                x < mainDisplay.getPanePreviews().size(); x++){
            mainDisplay.getPanePreviews().get(x).setVisible(true);
            mainDisplay.getPanePreviews().get(x).getPreview().setDisplayedNode(
                    new AtomicModelWrapper(50, new Atom(filterList.get(x))));
            mainDisplay.getPanePreviews().get(x).getPreview().setLabelText(
                    filterList.get(x));
        }
        for(; x < mainDisplay.getPanePreviews().size(); x++)
            mainDisplay.getPanePreviews().get(x).setVisible(false);
        
    }
    
    public static void main(String[] args){
        launch(args);
    } 
    
}
