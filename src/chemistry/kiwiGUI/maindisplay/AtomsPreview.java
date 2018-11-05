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
package chemistry.kiwiGUI.maindisplay;

import chemistry.dataModel.Atom;
import chemistry.kiwiGUI.ClickablePreviewWrapper;
import chemistry.kiwiGUI.VisualMessageQeue;
import chemistry.kiwiGUI.VisualMessageQeue.MESSAGE_TYPE;
import chemistry.kiwiGUI.Preview;
import chemistry.rendering.AtomicModelWrapper;
import chemistry.resourceloader.AtomBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.Event;

/**
 * Layout that shows a 3x3 grid of wrapped previews. By default the application
 * starts in the Atoms' section so this PreviewPane displays random atomic
 * models upon start.
 * @author https://github.com/AntonioBohne
 */
public class AtomsPreview extends PreviewsBase{

    /**
     * Creates a new layout that displays a 9x9 of atom previews. 
     * @param topSize Percentage the atom's model will take.
     * @param bottomSize Precentage the atom's name will take.
     * @throws Exception 
     */
    public AtomsPreview(double topSize, double bottomSize) throws Exception {
        super(topSize, bottomSize);
    }
        
    /**
     * 
     * @param top
     * @param bottom
     * @throws Exception 
     */
    @Override
    public void setPreviews(double top, double bottom) throws Exception{

        for(int x = 0; x < 9; x++){
            Atom atom = new Atom(new Random().nextInt(108) + 1 + x);
            Preview preview = new Preview(new AtomicModelWrapper(10, atom),
                    atom.getName(), 80, 20);
            ClickablePreviewWrapper wrapper = new ClickablePreviewWrapper(preview) {
                @Override
                public void handle(Event t) {
                    VisualMessageQeue.sendMessage(
                            MESSAGE_TYPE.SHOW_ATOM_SHEET_SCR,
                                this.getPreview().getText());
                }
            };
            this.wrappedPreviews.add(wrapper);
        }
    }
    
    /**
     * 
     * @param text 
     * @throws java.lang.Exception 
     */
    @Override
    public void filterEvt(String text) throws Exception{
        List<String> filterList = new ArrayList<>();
        for(String str : AtomBuffer.getAllAtomNames()){
            if(str.toLowerCase().contains(text.toLowerCase()))
                filterList.add(str);
        }
        int x = 0;
        for(; x < filterList.size() && x < this.wrappedPreviews.size(); x++){
            Atom atom = new Atom(filterList.get(x));
            this.wrappedPreviews.get(x).getLayout().setVisible(true);
            this.wrappedPreviews.get(x).getPreview().setDisplayedNode(
                    new AtomicModelWrapper(10, atom));
            this.wrappedPreviews.get(x).getPreview().setLabelText(atom.getName());
        }
        for(; x < this.wrappedPreviews.size(); x++){
            this.wrappedPreviews.get(x).getLayout().setVisible(false);
        }
    }
}
