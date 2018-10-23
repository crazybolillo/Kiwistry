package chemistry.defStage;

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
