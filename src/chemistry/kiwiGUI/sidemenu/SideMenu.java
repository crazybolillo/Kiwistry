package chemistry.kiwiGUI.sidemenu;

import chemistry.kiwiGUI.Preview;
import chemistry.resourceloader.LanguageLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class SideMenu extends GridPane{
    
    protected PaneGroup<Preview> labels;
    
    public SideMenu(){
        
        this.setId("container");
        this.getStylesheets().add(this.getClass().getResource(
               "SideMenuStyle.css").toExternalForm());
        this.setMaxSize(150, Integer.MAX_VALUE);
        this.setMinSize(150, 0);
        
        DoubleStream.of(150).forEach(e-> this.
                getColumnConstraints().add(new ColumnConstraints(e)));
        DoubleStream.of(120, 120, 120, 120, 120).forEach(e-> this.
                getRowConstraints().add(new RowConstraints(e)));
        
        //Icons for the sidebar and their description
        List<ImageView> imgv = new ArrayList<>();
        imgv.add(new ImageView(new Image(this.getClass().getResource(
                "atomIcon.png").toExternalForm())));
        imgv.add(new ImageView(new Image(this.getClass().getResource(
                "moleculeIcon.png").toExternalForm())));
        imgv.add(new ImageView(new Image(this.getClass().getResource(
                "lewisIcon.png").toExternalForm())));
        imgv.add(new ImageView(new Image(this.getClass().getResource(
                "testIcon.png").toExternalForm())));
        imgv.add(new ImageView(new Image(this.getClass().getResource(
                "settingsIcon.png").toExternalForm())));
        
        labels = new PaneGroup<>();
        labels.add(new Preview(imgv.get(0), LanguageLoader.getAppTranslation(
                "atomLbl") + "s", 70, 30));
        labels.add(new Preview(imgv.get(1), LanguageLoader.getAppTranslation(
                "moleculelbl"), 70, 30));
        labels.add(new Preview(imgv.get(2), "Lewis", 70, 30));
        labels.add(new Preview(imgv.get(3), "Tests", 70, 30));
        labels.add(new Preview(imgv.get(4), LanguageLoader.getAppTranslation(
                "settingsLbl"), 70, 30));
        
        //Add icons and the descriptions to sidebar
       for(int x = 0; x < imgv.size(); x++){

           labels.get(x).setLabelRatio(1.5);
           labels.get(x).setId("unselectedItem");   
           labels.get(x).setAlignment(Pos.CENTER);
           
           GridPane.setConstraints(labels.get(x), 0, x);
           this.getChildren().add(labels.get(x));
       }    
    }
}
