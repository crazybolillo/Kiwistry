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

import chemistry.kiwiGUI.ClickablePreviewWrapper;
import chemistry.kiwiGUI.Preview;
import chemistry.kiwiGUI.VisualMessageQeue;
import chemistry.kiwiGUI.VisualMessageQeue.MESSAGE_TYPE;
import chemistry.resourceloader.LanguageLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class displays all the available tests the user can take. It displays
 * them in the WrappedPreviews as 
 * @author https://github.com/AntonioBohne
 */
public class TestsPreview extends PreviewsBase{

    public TestsPreview(double topSize, double bottomSize) throws Exception {
        super(topSize, bottomSize);
    }

    /**
     * This method loads the three difficulty levels in the respective 
     * language the user has chosen and the images which represent those 
     * difficulty levels. It then proceeds to create the necessary
     * preview wrappers for atom based trivia and molecule based trivia. The
     * preview wrappers will send a message to the view controller with a 
     * MESSAGE_TYPE that states the type of game screen that should be shown 
     * and an argument that states the difficulty.
     * @param top
     * @param bottom
     * @throws Exception 
     */
    @Override
    public void setPreviews(double top, double bottom) throws Exception {
        
        List<String> tempDiff = new ArrayList<>(Arrays.asList(
                LanguageLoader.getAppTranslation("easyLbl"),
                    LanguageLoader.getAppTranslation("normalLbl"), 
                        LanguageLoader.getAppTranslation("hardLbl")));
        
        List<Image> img = new ArrayList<>();
        img.add(new Image("chemistry/kiwiGUI/res/easyIcon.png"));
        img.add(new Image("chemistry/kiwiGUI/res/normalIcon.png"));
        img.add(new Image("chemistry/kiwiGUI/res/hardIcon.png"));

            
        for(int x = 0; x < 3; x++){
            
            ImageView imgv = new ImageView(img.get(x));
            imgv.setFitHeight(88);
            imgv.setFitWidth(88);
            Preview preview = new Preview(imgv, x + 1 + " - " +
                    LanguageLoader.getAppTranslation("atomLbl") + "s",
                        top, bottom);
            
            final String diff = tempDiff.get(x);
            ClickablePreviewWrapper wrapper = new ClickablePreviewWrapper(preview) {
                @Override
                public void handle(Event t) {
                    VisualMessageQeue.sendMessage(
                            MESSAGE_TYPE.SHOW_ATOM_GAME_SCR, diff);
                }
            };
            this.wrappedPreviews.add(wrapper);
        } 
    }
    
    @Override
    public void filterEvt(String filterText) throws Exception {
        //DO NOTHING. 
    }

}
