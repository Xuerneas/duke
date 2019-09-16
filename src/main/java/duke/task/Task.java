package duke.task;

/**
 * overall task class with task properties
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * A constructor to write in content of the task.
     * @param description content
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * the method to determine if the task is done
     * @return String that represent different situations
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * method to set the task as done
     */
    public void setDone() {
        this.isDone = true;
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * the Overriding method
     * @return String that in task format
     */
    @Override
    public String toString() {
        return  this.getStatusIcon() + " -- " + this.description;
    }
}
