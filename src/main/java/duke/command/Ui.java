package duke.command;

import duke.excaptions.IllegalDukeArgumentException;
import duke.excaptions.IllegalDukeFormatException;

import java.text.ParseException;

/**
 *The Ui class is one of the class in command package which used to deal the interaction with users
 */
public class Ui {
    private Storage storage;

    /**
     * The constructor to write in Storage object
     * @param storage
     */
    public Ui(Storage storage) {
        this.storage = storage;
    }

    /**
     * This method print out the welcome words
     */
    public String showWelcome() {
        String greet = "Hello! I'm Duke\n" +
                "What can I do for you?\n";
        return greet + this.showLine();
    }

    /**
     * this method is to read the command line by line and pass them to Parser
     */
    public String readCommand(String userInput) {
            String[] splitCommand = userInput.split(" ", 2);
            try {
                Parser parser = new Parser(splitCommand);
                return parser.parse(storage) + this.showLine();

            } catch (IllegalArgumentException e) {
                return e.getMessage();
            } catch (IllegalDukeFormatException e) {
                return e.getMessage();
            } catch (IllegalDukeArgumentException e) {
                return e.getMessage();
            } catch (ParseException e) {
                return "Error!!";
            }

    }

    /**
     * a method return splitting lines
     */
    public String showLine() {
        return "_______________________________________________________________";
    }

    /**
     * A method to print error message
     * @param message
     */
    public void showError(String message) {
        System.out.println(message);
    }
}
