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

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Class that adds extra functionality to PreviewWrappers by implementing an 
 * action listener to them. This method is prefered over simply adding an 
 * EventHandler to an already existing PreviewWrapper as you can not 
 * access local variables from Lambdas. Using this class ensures that you 
 * can access the local variables from the class.
 * @author https://github.com/AntonioBohne
 */
public abstract class ClickablePreviewWrapper extends PreviewWrapper 
        implements EventHandler{

    public ClickablePreviewWrapper(Preview preview) {
        super(preview);
        this.getLayout().setOnMouseClicked(this);
    }

    /**
     * Method that will be called whenever the object is clicked on.
     * @param t
     */
    @Override
    public abstract void handle(Event t);

}
