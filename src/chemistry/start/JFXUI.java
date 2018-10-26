package chemistry.start;

import chemistry.atoms.SQLReader;
import chemistry.atomicSheet.JFXSheet;
import chemistry.defStage.DefaultStage;
import chemistry.game.GameWindow;
import java.util.Random;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author https://github.com/AntonioBohne
 */
public class JFXUI extends Application{

    DefaultStage window;
    Scene sc;
    GridPane bodyPanel;
    
    Label headerTxt;
    TextField searchFld;
    
    ObservableList<String> defData;
    ListView<String> listVw;
    
    Button randomBtn;
    Button gameBtn;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = new DefaultStage();
        bodyPanel = new GridPane();
        bodyPanel.setAlignment(Pos.CENTER);
        
        headerTxt = new Label();
        headerTxt.setText("Atomic models");
        headerTxt.setId("header");
        
        searchFld = new TextField();
        searchFld.setPrefWidth(250);
        searchFld.setPromptText("Search for atom");
        searchFld.setOnMouseClicked(e ->{
            listVw.getSelectionModel().clearSelection();
        });
        searchFld.setOnKeyReleased(e ->{
            if(e.getCode() == KeyCode.DOWN){
                listVw.requestFocus();
                listVw.getSelectionModel().select(0);
                return;
            }
            this.filterEvt();
        });
        
        defData = SQLReader.getAtomNames();
        
        listVw = new ListView<>();
        listVw.setPrefSize(250, Integer.MAX_VALUE);
        listVw.setItems(defData);
        listVw.setOnMouseClicked(e ->{
            if(e.getClickCount() > 1){
                try{
                    JFXSheet sheet = new JFXSheet(
                            listVw.getSelectionModel().getSelectedItem());
                    sheet.display();
                }catch(Exception ex){
                }
            }
        });
                
        randomBtn = new Button();
        randomBtn.setText("Random atom");
        randomBtn.setPrefWidth(150);
        randomBtn.setOnAction(e ->{
            Random random = new Random();
            try {
                JFXSheet sheet = new JFXSheet(
                        listVw.getItems().get(random.nextInt(118)));
                sheet.display();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        gameBtn = new Button();
        gameBtn.setText("Play");
        gameBtn.setPrefWidth(randomBtn.getPrefWidth());
        gameBtn.setOnAction(e ->{
            GameWindow gmWin = new GameWindow();
            gmWin.display();
        });
        
        Node components[] = new Node[5];
        components[0] = headerTxt;
        components[1] = searchFld;
        components[2] = listVw;
        components[3] = randomBtn;
        components[4] = gameBtn;
            
        for(int x = 0; x < components.length; x++){
            GridPane.setConstraints(components[x], 0, x);
            GridPane.setHalignment(components[x], HPos.CENTER);
            GridPane.setHgrow(components[x], Priority.ALWAYS);
            bodyPanel.getChildren().add(components[x]);
        }
        
        bodyPanel.setVgap(10);
        bodyPanel.setPadding(new Insets(10, 15, 10, 15));

        sc = new Scene(bodyPanel, 280, 530);
        sc.getStylesheets().add(this.getClass().getResource(
                "JFXStyle.css").toExternalForm());
        
        window.setScene(sc);
        window.setTitle("Atomic Models");
        window.setWidth(280);
        window.setHeight(530);
        window.setResizable(true);
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });        
        
        //Following code is all for resizing purposes.
        window.setMinWidth(200);
        window.setMinHeight(290);
 
        searchFld.setMinWidth(window.getMinWidth() - 50);        
        searchFld.setMinHeight(35);
        
        listVw.setMinWidth(searchFld.getMinWidth());
        listVw.setMinHeight(40);
        
        randomBtn.setMinWidth(searchFld.getMinWidth());
        randomBtn.setMinHeight(searchFld.getMinHeight());
        
        gameBtn.setMinWidth(searchFld.getMinWidth());
        gameBtn.setMinHeight(searchFld.getMinHeight());
        
        
        //Display window and request focus so prompt text appears on text field.
        window.show();
        listVw.requestFocus();
    }
    
    private void filterEvt() {
        if(searchFld.getText().isEmpty()){
            listVw.setItems(defData);
            return;
        }
        ObservableList<String> filterData = FXCollections.observableArrayList();
        for(String str : defData){
            if(str.toLowerCase().contains(searchFld.getText().toLowerCase())){
                filterData.add(str);
            } 
        }
        if(filterData.isEmpty())
            filterData.add("No results found");
        listVw.setItems(filterData);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
