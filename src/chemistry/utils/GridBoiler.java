package chemistry.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javafx.collections.ObservableList;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ConstraintsBase;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * This class contains useful objects that reduce code lines involved
 * when having to manage and edit layout managers in JavaFX.
 * @author https://github.com/AntonioBohne
 */
public class GridBoiler {

    /**
     * This method creates and adds the Column/Row constraints to the GridPane
     * passed trough the parameters with the respective width/height percentage
     * passed also trough the parameters. This does not delete any existing
     * constraints. It just adds new ones.
     * @param <T> Type that will added to the GridPane. Must be a ColumnConstraints
     * or RowConstraints
     * @param pane GridPane where the constraints will be added to. 
     * @param constraintType Specifies the type of constraint that will be added
     * to the GridPane. ColumnConstraints or RowConstraints.
     * @param percents Specifies the width/height percentage each constraint
     * will have. The amount of doubles passed here is the amount of rows added
     * each one which its respective width/height percentage. 
     * <code>
 GridBoiler.setConstraints(yourPane, ColumnConstraints.class, 10, 10, 10)
 </code>
     * Will add 3 ColumnConstraints to the pane, each one with 10 width percentage.
     * @throws Exception 
     * @deprecated This method uses reflection which is generally slower. It has
     * been left here because reflection is cool and this method inspired
     * this library. The addColumn/Row constraints methods are prefered.
     */
    public static <T extends ConstraintsBase> void addConstraints(GridPane pane, 
            Class<T> constraintType, double... percents) throws Exception{
        
        String methodName = "";
        String getterName = "";
        if(constraintType == RowConstraints.class){
            methodName = "setPercentHeight";
            getterName = "getRowConstraints";
        }
        
        else if(constraintType == ColumnConstraints.class) { 
            methodName = "setPercentWidth";
            getterName = "getColumnConstraints";
        }
        
        Constructor constructor = constraintType.getConstructor();
        Method setPercent = constraintType.getDeclaredMethod(
                methodName, double.class);
        Method getConstraint = pane.getClass().getDeclaredMethod(getterName);
        
        for(int x = 0; x < percents.length; x++){
            T constraint = (T)constructor.newInstance();
            setPercent.invoke(constraint, percents[x]);
            ObservableList<T> list = (ObservableList<T>)getConstraint.invoke(pane);
            list.add(constraint);
        }
    } 
    
    /**
     * Creates an adds ColumnConstraints to the GridPane passed trough the
     * parameters. The ColumnConstraints will have the width percentage
     * passed trough the "percentages" arguments. This does not delete any 
     * existing ColumnsConstraints. It just adds new ones.
     * @param pane GridPane that the ColumnConstraints will be added to
     * @param percentages The width percentage for each ColumnConstraint
     * added. For example
     * <code>addColumnConstraints(yourPane, 10, 10)</code>
     * Will add two ColumnConstraints to the GridPane. Each one with a 
     * width percentage of 10%.
     */
    public static void addColumnConstraints(GridPane pane, double... percentages){
        for(double dbl : percentages){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(dbl);
            pane.getColumnConstraints().add(column);
        }
    }
    
    /**
     * Creates an adds RowConstraints to the GridPane passed trough the
     * parameters. The RowConstraints will have the width percentage
     * passed trough the "percentages" arguments. This does not delete any 
     * existing RowConstraints. It just adds new ones.
     * @param pane GridPane that the ColumnConstraints will be added to
     * @param percentages The width percentage for each ColumnConstraint
     * added. For example
     * <code>addColumnConstraints(yourPane, 10, 10)</code>
     * Will add two ColumnConstraints to the GridPane. Each one with a 
     * width percentage of 10%.
     */
    public static void addRowConstraints(GridPane pane, double... percentages){
        for(double dbl : percentages){
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(dbl);
            pane.getRowConstraints().add(row);
        }
    }
}
