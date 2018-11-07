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

import chemistry.dataModel.Atom;
import chemistry.kiwiGUI.defStage.DefaultStage;
import chemistry.resourceloader.KiwiStyleLoader;
import javafx.scene.Scene;

/**
 * This window contains a resizable canvas with the atomic model of the atom
 * passed trough the parameters.
 * @author https://github.com/AntonioBohne
 */
public class CanvasFrame {
    
    DefaultStage window;
    Scene sc;
    
    AtomicModelWrapper canvas;
    private double size;
    
    public CanvasFrame(Atom atomo) throws Exception {
        
        size = 800;
        
        window = new DefaultStage();
        canvas = new AtomicModelWrapper(size, atomo);
        canvas.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        canvas.setMinSize(0, 0);
        
        sc = new Scene(canvas, size, size);
        sc.getStylesheets().add(KiwiStyleLoader.getStyleSheet());
        window.setScene(sc);
        window.setTitle(atomo.getName());
        window.setResizable(true);
          
        }
    
    public void display() {
        window.show();
    }
    
    public void close() {
        window.close();
    }
    
}
