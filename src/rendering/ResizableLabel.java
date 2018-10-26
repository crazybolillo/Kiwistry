package rendering;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class draws text on a Canvas and hears for changes in its width
 * or height. If any change in these two properties is dected it will recalculate
 * the adecuate font size and repaint the text.
 * @author https://github.com/AntonioBohne
 */
public class ResizableLabel extends ResizableCanvas{

    private String text;
    private Font fontSize;
    private GraphicsContext gc;
    
    public ResizableLabel(){
        super();
        gc = this.getGraphicsContext2D();
        fontSize = new Font("Roboto", 1);
    }
    
    /**
     * Creates a new ResizableCanvas and sets its text to the one passed
     * trough the parameters. Using this constructor will leave the default
     * size of the canvas to 0 which means the text wont be seen. To also
     * set the initial width and height look at the different constructor.
     * @param text Text that will be drawn on the screen.
     */
    public ResizableLabel(String text){
        super();
        this.text = text;
        gc = this.getGraphicsContext2D();
        fontSize = new Font("Roboto", 1);
    }
    
    /**
     * Creates a new ResizableCanvas, sets its text and also its initial
     * width and height. 
     * @param text Text that will be drawn on the canvas.
     * @param width Initial width that the canvas will have.
     * @param height Initial height that the canvas will have.
     */
    public ResizableLabel(String text, double width, double height){
        super();
        this.text = text;
        gc = this.getGraphicsContext2D();
        fontSize = new Font("Roboto", 1);
        this.setWidth(width);
        this.setHeight(height);
    }
    
    /**
     * Creates a new ResizableCanvas, sets its text and also its initial
     * width and height. 
     * @param width Initial width that the canvas will have.
     * @param height Initial height that the canvas will have.
     */
    public ResizableLabel(double width, double height){
        super();
        gc = this.getGraphicsContext2D();
        fontSize = new Font("Roboto", 1);
        this.setWidth(width);
        this.setHeight(height);
    }
    
    /**
     * Sets the text that will be displayed in the Canvas. Calling this 
     * function automatically updates the canvas, erases any previous text
     * and draws the new one. 
     * @param text Text that will be displayed.
     */
    public void setText(String text){
        this.text = text;
    }
    
    /**
     * Returns the texdt that is currently being displayed on the canvas.
     * @return String that equals the text being displayed on this canvas.
     */
    public String getText(){
            return text;
    }
    
    /**
     * Utilitarian method that clears the rectangle. Called everytime the 
     * canvas has to be updated.
     */
    private void clearCanvas(){
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    /**
     * Utilitarian method that calculates the adecuate font size based on the
     * current width and height of the canvas.
     */
    private void calculateFontSize(){
        double size = ((this.getWidth() + this.getHeight()) / 6) - 
                (this.getWidth() * .15);
        fontSize = new Font("Roboto", size);
    }
    
    /**
     * Draws the current String field "text" on the canvas with the current
     * font size. To update the font size that the Text will have call
     * <code>calculateFontSize()</code> first.
     */
    private void drawText(){
        gc.setFont(fontSize);
        gc.setFill(Color.BLACK);
        gc.fillText(text, this.getWidth() / 2, this.getHeight() /1.5);
    }
    
    
    /**
     * The width/height property will call this method when their listeners
     * detect any change.
     */
    @Override
    protected void onResizeUpdate() {
        this.clearCanvas();
        this.calculateFontSize();
        this.drawText();
        System.out.println("DRAWED: " + fontSize.getSize() + "----" + this.getWidth());
    }
    
    public void setPrefWidth(double width){
        
    }
    

}
