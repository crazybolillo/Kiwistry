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
import javafx.scene.layout.GridPane;

/**
 * Canvas do not have an implementation for prefered with/height and generic
 * methods troughout the app rely on it to control resizing. These class
 * fixes this by wrapping it on a GridPane which does have prefered size 
 * implementations. This wrapper will resize so it takes all the space possible.
 * @author https://github.com/AntonioBohne
 */
public class AtomicModelWrapper extends GridPane{
    
    AtomicModelFX canvas;
    
    public AtomicModelWrapper(double size, Atom atomo) throws Exception{
        
        canvas = new AtomicModelFX(size, atomo);
        this.getChildren().add(canvas);
        this.setMaxSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.setMinSize(0, 0);
        /**
         * Binds the window size with the container. Effectively
         * resizing the canvas together with the window if resized. The canvas
         * has its own action listener that will resize automatically when it 
         * detects its size has changed.
         */
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
    }
}
