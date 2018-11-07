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

import chemistry.dataModel.Atom;
import chemistry.kiwiGUI.defStage.DefaultStage;
import chemistry.utils.GridBoiler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import chemistry.rendering.AtomicModelWrapper;
import chemistry.rendering.CanvasFrame;
import chemistry.rendering.ResizableLabel;
import chemistry.resourceloader.KiwiStyleLoader;
import chemistry.resourceloader.LanguageLoader;

/**
 * This class is used to display information about an atom. It includes a
 * 2D model and several labels with general information like name, atomic number
 * mass etc...
 * @author https://github.com/AntonioBohne
 */
public class JFXSheet{

    private GridPane layout;
    private DefaultStage window;
    private Scene sc;
    
    private Atom atomo;

    private ResizableLabel atomInfo[];
    
    private AtomicModelWrapper atomModel;
    /**
     * Creates a new sheet with the atom´s information. 
     * @param atomNumber Atomic number. Must be from 1-118
     * @throws Exception If the atomic number is invalid an exception will be
     * thrown.
     */
    public JFXSheet(int atomNumber) throws Exception {
        atomo = new Atom(atomNumber);
        this.init();
    }
    
    /**
     * 
     * @param atomName
     * @throws Exception 
     */
    public JFXSheet(String atomName) throws Exception {
        atomo = new Atom(atomName);
        this.init();
    }
    
    private void init() throws Exception{
        
        
        layout = new GridPane();
        layout.setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        layout.setAlignment(Pos.CENTER);

        this.setAtomicModel();
        this.setAtomicInformation();
        
        //---------------
        //Setting up layout. 
        //---------------      
        GridBoiler.addColumnConstraints(layout, 10, 40, 40, 10);
        GridBoiler.addRowConstraints(layout, 60, 5, 5, 5, 5, 5, 5, 5, 5);
        
        this.setNarrowLayout();
        
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setHgap(15);
        layout.setVgap(7);
        
        //Set scene
        sc = new Scene(layout, 400, 650);
        sc.getStylesheets().add(KiwiStyleLoader.getStyleSheet());
        
        window = new DefaultStage();
        window.setScene(sc);
        window.setWidth(sc.getWidth());
        window.setHeight(sc.getHeight());
        window.setResizable(true);
        window.setTitle(atomo.getName());
        
        //For resizing purposes
        window.setMinWidth(window.getWidth());
        window.setMinHeight(window.getHeight());
        
        window.widthProperty().addListener(e ->{
            if(window.getWidth() >= 600)
                this.setWideLayout();
            else
                this.setNarrowLayout();
        });
    }
    
    /**
     * Initializes the AtomicModelCanvas, adds it to a wrapper and binds
     * its width and height to it to enable resizing.
     */
    private void setAtomicModel() throws Exception{
        
        atomModel = new AtomicModelWrapper(190, atomo);
        atomModel.setOnMouseClicked(e ->{
            if(e.getClickCount() >= 2){
                try {
                    CanvasFrame frame;
                    frame = new CanvasFrame(atomo);
                    frame.display();
                } catch (Exception ex) {
                }
            }
        });
    }
    
    /**
     * Creates the labels which  display information about atoms and their
     * respective wrappers so their width and height can be binded to it. 
     * Enabling resizing.
     */
    private void setAtomicInformation(){
        
        atomInfo = new ResizableLabel[8];
        for(int x = 0; x < atomInfo.length; x++){
            atomInfo[x] = new ResizableLabel();
            atomInfo[x].setSizeToHeightRatio(1.5);
            atomInfo[x].setPrefSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
            atomInfo[x].setMinSize(0, 0);
            atomInfo[x].setAlignment(Pos.CENTER);
        }
        
        atomInfo[0].setText(LanguageLoader.getAppTranslation("nameLbl") +
                ": " + atomo.getName());
        
        atomInfo[1].setText(LanguageLoader.getAppTranslation("symbolLbl") + 
                ": " + atomo.getSymbol());
        
        atomInfo[2].setText(LanguageLoader.getAppTranslation("massLbl") + 
                ": " + Double.toString(atomo.getAtomicMass()));
        
        atomInfo[3].setText(LanguageLoader.getAppTranslation("atomNumLbl") + 
                ": " + Integer.toString(atomo.getAtomicNumber()));
        
        atomInfo[4].setText(LanguageLoader.getAppTranslation("groupLbl") + 
                ": " + Integer.toString(atomo.getGroup()));
        
        atomInfo[5].setText(LanguageLoader.getAppTranslation("periodLbl") + 
                ": " + Integer.toString(atomo.getPeriod()));
        
        atomInfo[6].setText(LanguageLoader.getAppTranslation("valenceLbl") +
                ": " + Integer.toString(atomo.getElectronicConfig().get(
                        atomo.getElectronicConfig().size() -  1)));
        
        atomInfo[7].setText(LanguageLoader.getAppTranslation("electronegaLbl") +
                ": " + Double.toString(atomo.getElectronegativity()));
    }
    
    /**
     * Used to remove all elements from the layout. Usually used in order
     * to reassign them when the size of the window changes.
     */
    private void removeElements(){
        layout.getChildren().remove(atomModel);
        for(ResizableLabel lbl : atomInfo){
            if(layout.getChildren().contains(lbl))
                layout.getChildren().remove(lbl);
        }
    }
    
    /**
     * 
     */
    private void setNarrowLayout(){
        
        this.removeElements();
        
        GridPane.setConstraints(atomModel, 0, 0, 5, 1);
        GridPane.setHalignment(atomModel, HPos.CENTER);
        layout.getChildren().add(atomModel);
        
        int y = 1;
        for(ResizableLabel label : atomInfo){
            GridPane.setConstraints(label, 1, y, 2, 1);
            layout.getChildren().add(label);
            y++;
        }
    }
    
    /**
     * Changes the layout design when the pixel width reaches a certain
     * size. It allows the atomic model to grow and places the atom's info
     * on rows with two elements each.
     */
    private void setWideLayout(){
        
        this.removeElements();
        
        GridPane.setConstraints(atomModel, 0, 0, 5, 5);
        layout.getChildren().add(atomModel);
        int y = 5;
        for(int x = 0; x < atomInfo.length;){
            for(int z = 0; z < 2 && x < atomInfo.length; z++, x++){
                GridPane.setConstraints(atomInfo[x], z + 1, y, 1, 1);
                layout.getChildren().add(atomInfo[x]);
            }
            y++;
        }
    }
    
    public void display() {
        window.show();
    }
    
    public void close() {
        window.close();
    }
}   
