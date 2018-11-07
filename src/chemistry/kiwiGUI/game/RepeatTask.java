package chemistry.kiwiGUI.game;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Class which executes the code in the abstract method n times, where n 
 * is an integer passed trough the constructor. All code is executed in the
 * main JavaFX thread. The class contains a BooleanProperty which changes
 * to true when the task has finished executing. This BooleanProperty can
 * be used to stop code execution in the main JavaFX thread and restart it
 * with a listener that is triggered when the hasFinished BooleanProperty 
 * changes and is true.
 * @author https://github.com/AntonioBohne
 */
public abstract class RepeatTask {

    private Timer timer;
    private TimerTask task;
    
    private long delay;
    private int count;
    private int countLimit;
    public BooleanProperty hasFinished;
    
    /**
     * Creates a new RepeatTask object ready to be started with the <code>
     * startTimer</code> methdo.
     * @param countLimit How many times the task will run before stopping.
     * @param delayMillis Milliseconds between each task.
     */
    public RepeatTask(int countLimit, long delayMillis){
    
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        runTask();
                    }
                });
            };
        };
        
        delay = delayMillis;
        count = 0;
        this.countLimit = countLimit;
        hasFinished = new SimpleBooleanProperty(false);
    }
    
    /**
     * Method called each time the task runs. It ensures that the counter
     * increases by one each time the method is called before calling the
     * user's defined method.
     */
    private void runTask(){
        
        count++;
        if(count >= countLimit){
            timer.cancel();
            hasFinished.set(true);
        }
        setTask();
    }
    
    /**
     * User defined task that will run every time the task repeats.
     */
    public abstract void setTask();
    
    /**
     * Starts the timer. The task will be repeated every n milliseconds. Where
     * n is the long passed trough the constructor. It will stop running once
     * the amount of times the task has been run is equal than the limit, also
     * passed trough the constructor. 
     */
    public void startTimer(){
        timer.scheduleAtFixedRate(task, 0, delay);
    }
}

