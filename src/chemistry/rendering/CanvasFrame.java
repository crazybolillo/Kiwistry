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
package chemistry.rendering;

import chemistry.atoms.Atom;
import chemistry.defStage.DefaultStage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 * This window contains a resizable canvas with the atomic model of the atom
 * passed trough the parameters.
 * @author https://github.com/AntonioBohne
 */
public class CanvasFrame {
    
    DefaultStage window;
    Scene sc;
    GridPane layout;
    
    AtomicModelFX canvas;
    private double size;
    
    public CanvasFrame(Atom atomo) throws Exception {
        
        size = 800;
        
        window = new DefaultStage();
        layout = new GridPane();

        canvas = new AtomicModelFX(size, atomo);
        
        layout.getChildren().add(canvas);
        
        sc = new Scene(layout, size, size);
        window.setScene(sc);
        window.setTitle(atomo.getName());
        window.setResizable(true);
        
        /**
         * Binds the window size with the container. Effectively
         * resizing the canvas together with the window if resized. The canvas
         * has its own action listener that will resize automatically when it 
         * detects its size has changed.
         */
        canvas.widthProperty().bind(sc.widthProperty());
        canvas.heightProperty().bind(sc.heightProperty());
        
        }
    
    public void display() {
        window.show();
        canvas.paintModel();
    }
    
    public void close() {
        window.close();
    }
    
}
