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
package chemistry.kiwiGUI.sidemenu;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 *
 * @author https://github.com/AntonioBohne
 * @param <E>
 */
public class PaneGroup <E extends Pane> {
    
    private List<E> elements;
    
    public PaneGroup(){
        elements = new ArrayList<>();
    }
    
    public E get(int index){
        return this.elements.get(index);
    }
    
    public void add(E element){
        element.setOnMouseClicked(e ->{
            this.setSelected(element);
        });
        this.elements.add(element);
    }
    
    public void remove(E element){
        this.elements.remove(element);
    }
    
    public void setSelected(E element){
        for(E items : elements){
            if(!items.equals(element))
                items.setId("unselectedItem");
            else
                items.setId("selectedItem");
        }
    }
    
}
