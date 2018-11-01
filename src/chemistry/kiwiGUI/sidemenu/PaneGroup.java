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
