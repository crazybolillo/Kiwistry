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

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * This class draws text on a Canvas and hears for changes in its width
 * or height. If any change in these two properties is dected it will recalculate
 * the adecuate font size and repaint the text.
 * @author https://github.com/AntonioBohne
 */
public class ResizableLabel extends Label{

    private String text;
    private Font fontSize;
    private double sizeToHeight = 2;
    
    public ResizableLabel(){
        super();
        this.fontSize = new Font("Roboto", 1);
        this.setFont(fontSize);
        this.setListeners();
    }
    
    public ResizableLabel(String text){
        super(text);
        this.fontSize = new Font("Roboto", 1);
        this.setFont(fontSize);
        this.setListeners();
    }
    
    /**
     * Sets the height to font size ratio. The division between the component's
     * height and the ratio will be the font size.
     * @param ratio New ratio to calculate font size. Will divide the height
     * to set the font size.
     */
    public void setSizeToHeightRatio(double ratio){
        this.sizeToHeight = ratio;
    }
    
    /**
     * Adds listeners to the width and height of the label. Whenever the width
     * or height changes the <code>onResizeUpdate()</code> method will be called.
     */
    private void setListeners(){
        this.widthProperty().addListener(e ->{
            this.onResizeUpdate();
        });
        this.heightProperty().addListener(e ->{
            this.onResizeUpdate();
        });
    }


    /**
     * Utilitarian method that calculates the adecuate font size based on the
     * current width and height of the canvas.
     */
    private void calculateFontSize(){
        double size = this.getHeight() / sizeToHeight;
        fontSize = new Font("Roboto", size);
        this.setFont(fontSize);
    }
 
    /**
     * The width/height property will call this method when their listeners
     * detect any change.
     */
    private void onResizeUpdate() {
        this.calculateFontSize();
    }
}
