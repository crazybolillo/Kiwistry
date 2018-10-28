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

import javafx.scene.canvas.Canvas;

/**
 * Extends canvas and overrides certain methods to allow the Canvas to resize.
 * Forces subclasses to implement a method that is called when a change 
 * to the width or height is detected.
 * @author https://github.com/AntonioBohne
 */
public abstract class ResizableCanvas extends Canvas{
    
    public ResizableCanvas(){
        this.widthProperty().addListener(e ->{
            this.onResizeUpdate();
        });
        this.heightProperty().addListener(e ->{
            this.onResizeUpdate();
        });
    }
    
    @Override 
    public boolean isResizable(){
        return true;
    }
        
    /**
     * Method that runs whenever the width/height of the canvas changes.
     */
    protected abstract void onResizeUpdate();
}
