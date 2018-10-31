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
package chemistry.defStage;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Default window used all troughout the program in order to display the app´s
 * icon.
 * @author https://github.com/AntonioBohne
 */
public class DefaultStage extends Stage{
    
    public DefaultStage(){
        /*Image used was gathered from:
        https://www.iconfinder.com/icons/3775427/atom_atomic_molecule_science_icon_icon
        and licensed under Creative Commons 3.0
        No changes were made to the image.*/
        this.getIcons().add(new Image(this.getClass().getResource(
            "AppIcon.png").toExternalForm()));
    }
}
