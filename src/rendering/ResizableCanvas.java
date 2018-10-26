
package rendering;

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
