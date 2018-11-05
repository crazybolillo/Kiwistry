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
package chemistry.kiwiGUI.menu;

import chemistry.kiwiGUI.VisualMessageQeue;
import chemistry.kiwiGUI.VisualMessageQeue.MESSAGE_TYPE;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

/**
 * Stores a set of Nodes that act as RadioButtons. This class ensures
 * that only one can be clicked at any time. Once added to the list it can
 * not be removed.
 * @author https://github.com/AntonioBohne
 * @param <E> 
 */
public class NodeGroup <E extends Node> {
    
    /**
     * Keeps track of all Panes inside the group.
     */
    private List<E> elements;
    private int previousSelectedIndex;
    
    public NodeGroup(){
        elements = new ArrayList<>();
    }
    
    /**
     * Returns the Element in the list with the selected index.
     * @param index Index that will be returned.
     * @return Element in the list belonging to the selected index. The 
     * element returned is ensured to extend the Pane class.
     */
    public E get(int index){
        return this.elements.get(index);
    }
    
    /**
     * Adds an element to the group and sets the message sent to the main
     * controller when its clicked.
     * @param element Element that will be added to the group.
     * @param message Message that will be sent to the main controller whenever
     * the element is clicked.
     * @param varargs Optional extra arguments which can be sent together
     * with the message.
     */
    public void add(E element, MESSAGE_TYPE message, String... varargs){
        element.setOnMouseClicked(e ->{
            this.setSelected(element);
            VisualMessageQeue.sendMessage(message, varargs);
        });
        this.elements.add(element);
    }
    
    /**
     * Unselects any other items and selects the one passed trough the 
     * parameters.
     * @param element Element that gets selected.
     */
    public void setSelected(E element){
        previousSelectedIndex = this.getSelectedIndex();
        for(E items : elements){
            if(!items.equals(element))
                items.setId("unselectedItem");
            else
                items.setId("selectedItem");
        }
    }
    
    /**
     * Returns the index of the item that is currently selected. Returns
     * -1 if no Node has been selected yet.
     * @return Index of the selected item.
     */
    public int getSelectedIndex(){
        for(int x = 0; x < elements.size(); x++){
            if(elements.get(x).getId().equals("selectedItem"))
                return x;
        }
        return -1;
    }
    
    /**
     * Returns the index that was previously selected. Useful when wanting
     * to restore the layout to the way it was before.
     * @return Returns the index that was previously selected or -1 if no
     * index has been set selected yet.
     */
    public int getPreviousSelectedIndex(){
        return previousSelectedIndex;
    }
    
}
