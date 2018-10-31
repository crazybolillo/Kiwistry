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
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class AtomicModelFX extends ResizableCanvas{
    
    /**
     * The following variables can be tweaked to change the proportions. By 
     * default the nucleus takes 25% percent of the area, energy levels take
     * 75% and electrons 5% percent. 
     */
    private float nuclPercent = 0.30f;
    private float lvlPercent = 0.70f;
    private float electPercent = 0.05f;
    private float fontPercent = 0.08f;
    
    /**
     * Width that can be painted. It is always 10% smaller than the actual 
     * canvas size. This is to leave some padding for the outer electrons so they
     * are not cutoff. 
     */
    private double internalWidth;
 
    private double radius; //Nucleus radius
    private double separation; //Amount of pixels between each energy level. 
    
    private double electRad; //Electron´s radius
    
    private List<Integer> levels;//Levels of energy (outside rings) the atom has
    
    private String symbol;
    
    /**
     * Creates an AtomicModelFX object which extends the JavaFX Canvas class and
     * is designed to draw a 2D atomic model of an atom.
     * @param width Width of the canvas. Since the canvas is a square the height
     * will be equal to the internalWidth.
     * @param atomo Atom which will be painted on the canvas.
     */
    public AtomicModelFX(double width, Atom atomo) {
        
        super();
        this.setAtom(atomo);
        
        //For the atomic model to keep correct proportions it must be a square.
        this.setWidth(width);
        this.setHeight(width);
        this.setInternalWidth(width);
        
    }
    
    /**
     * Sets the atom that will be displayed. This method allows the Canvas
     * to display an atom different from the one it was constructed with. This
     * method does not modify the painted canvas in any way.<h3>To show the new
     * atom's model call the paintModel method.</h3>
     * @param atomo Atom's model that will be displayed next time the 
     * paintModel method is called.
     */
    public void setAtom(Atom atomo){
        levels = atomo.getElectronicConfig();
        symbol = atomo.getSymbol();
    }
      
    /**
     * Paints the atomic model of the atom. Must be called before it is displayed
     * to the user as the constructor does not automatically paint the model. 
     */
    public void paintModel() {
        this.utilProportions();
        this.clearCanvas();
        this.drawNucleus(symbol);
        this.drawEnergyLevels();
        this.drawElectrons();
    }
    
    /**
     * Set the proportions for the different components of the atomic model. 
     * @param nucl Amount of area the nucleus will take.
     * @param lvl Amount of area the electron levels will take
     * @param elec Amount of area the electrons themselves will take.
     */
    public void setProportions(float nucl, float lvl, float elec) {
        nuclPercent = nucl;
        lvlPercent = lvl;
        electPercent = elec;
    }
    
    /**
     * Erases the whole canvas.
     */
    private void clearCanvas() {
        GraphicsContext painter = this.getGraphicsContext2D();
        painter.clearRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    /**
     * Sets the size for all the components of the drawing based on the
     * percentages stored as floats from the class.
     */
    private void  utilProportions() {
        
        if(levels.size() > 5 && electPercent > 0.04f){
            electPercent -= 0.01f;
        }
        
        radius = internalWidth / 2 * nuclPercent;
        separation = internalWidth / 2 * lvlPercent / levels.size();
        electRad = internalWidth / 2 * electPercent;
    }
    
    /**
     * Draws the atom´s nucleus. 
     * @param symbol Atom´s symbol. 
     */
    private void drawNucleus(String symbol) {
        
        //Nucleu´s color.
        Color center = Color.rgb(92, 179, 245);
       
        GraphicsContext painter = this.getGraphicsContext2D();
        
        //Paint circle - nucleus
        painter.setFill(center);
        painter.fillOval((this.getWidth() / 2) - radius, 
                (this.getHeight() / 2) - radius,
                (radius * 2), (radius * 2));
        
        //Write atom´s symbol inside nucleus
        painter.setFill(Color.BLACK);
        painter.setFont(new Font("Arial", internalWidth * fontPercent));
        painter.setTextAlign(TextAlignment.CENTER);
        painter.setTextBaseline(VPos.CENTER);
        painter.fillText(symbol, (this.getWidth() / 2), (this.getHeight() / 2));
    }
    
    /**
     * Draws the energy levels, basically circle circumferences. 
     */
    private void drawEnergyLevels() {
        
        double temprad = radius + separation;
        
        GraphicsContext painter = this.getGraphicsContext2D();
        painter.setStroke(Color.GRAY);
        painter.setLineWidth(2);
        
        for(int x = 0; x < levels.size(); x++) {
            painter.strokeOval((this.getWidth() / 2) - temprad, 
                    (this.getHeight() / 2) - temprad,
                    temprad * 2, temprad * 2);
            temprad += separation;
        }
    }

    /**
     * Uses the circunference equation of the circle to find the point
     * a point in the electron`s level circumference and draws an electron.
     */
    private void drawElectrons() {
        
        GraphicsContext painter = this.getGraphicsContext2D();
        painter.setFill(Color.INDIANRED);
        //Main for loop which dictates which shell will be painted
        for(int x = 1; x <= levels.size(); x++) {
            //Nested loop to write electrons per shell
            //Divide the circle into angles for each electron.
            double angles = 360 / levels.get(x - 1);
            
            for(int z = 1; z <= levels.get(x - 1); z++) {
                
                /*Gather point in the circunference to write electron.*/
                //xpoint = rcos(angle)
                //ypoint = rsin(anagle)
                double xpoint = (radius + (separation * x)) * 
                        (Math.cos(Math.toRadians(angles * z)));
                double ypoint = (radius + (separation* x)) * 
                        (Math.sin(Math.toRadians(angles * z)));
                
                if(angles * z == 180 || angles * z == 360){
                    ypoint = 0;
                }
                else{
                    ypoint -= electRad;
                }
                
                /*Java draws circles inside a square. Add circle radius to the 
                points to compensate for that. The methods above also return
                negative numbers since mathematically the window is divided 
                into a cartesian map where the left is negative and the right
                is positive. Negative values must be converted into their 
                respective positives*/
                if(xpoint < 0){
                    xpoint = (this.getWidth() / 2) + xpoint;
                    xpoint -= electRad;
                }
                else{
                    xpoint = xpoint + (this.getWidth() / 2);
                    xpoint -= electRad;
                }
                painter.fillOval(xpoint, ypoint + (this.getHeight() / 2)
                        , electRad * 2, electRad * 2);
            }
        }
    }
    
    /**
     * The internal width is the actual area that will be painted. This is done
     * so the electrons on the outer shell are fully displayed. If the Canvas
     * width is 300 for example the internal width will be 300 - 10% = 270.
     */
    private void setInternalWidth(double size) {
        this.internalWidth = size - (size * .1);
    }
    
    /**
     * This method is called by the change listeners whenever the width or
     * height of the canvas changes. Since the atomic model is based on a 
     * squared aspect ratio this method will set the canvas width and height to the 
     * smaller dimension (width or height) of the window. This is to make sure
     * a fully visible Canvas can be drawed upon.
     * 
     * It is recommended to bind the size of the container with the canvas. 
     * @param width New container size
     * @param height New container
     */
    private void resizeCanvas(double width, double height) {
        
        if(width <= height){
            this.setInternalWidth(width);
        }
        else{
           this.setInternalWidth(height);
        } 
    }
    
    @Override
    protected void onResizeUpdate(){
        resizeCanvas(getWidth(), getHeight());
        paintModel();
    }
}
