package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModCommandTest {

    private Command testCommand = new ModCommand("dummy", "placeholder");
    private TaskList testTaskList = new TaskList("T | 0 | Send even more Help\n"
            + "R | 0 | Deliver Help | Day\n"
            + "A | 0 | Send less help | Sending Enough\n"
            + "E | 0 | Sleeping | 01-01-1970 2200");
    private UI testUi = new UI();
    private Storage testStorage = new Storage("dummy.txt", "dummydegree.txt");
    private DegreeList testList = new DegreeList();
    //Variable to catch system.out.println, must be converted to string to be usable
    private ByteArrayOutputStream systemOutput = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private DegreeManager degreesManager = new DegreeManager();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(systemOutput));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    ModCommandTest() throws DukeException, IOException {
    }

    @Test
    void testDone() throws DukeException {
        testCommand = new ModCommand("done", "3");
        testCommand.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Nice! I've marked this task as done:\n"
                + "  [A][Y] Send less help (After: Sending Enough)\r\n", systemOutput.toString());
    }

    @Test
    void testSnooze() throws DukeException {
        testCommand = new ModCommand("snooze", "4 /to 12-12-2013 2345");
        testCommand.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Noted. I've snoozed this task:\n"
                + "  [E][N] Sleeping (At: 12-12-2013 2345)\r\n", systemOutput.toString());
    }

    @Test
    void testDelete() throws DukeException {
        testCommand = new ModCommand("delete", "1");
        testCommand.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Noted. I've removed this task:\n"
                + "  [T][N] Send even more Help\r\n"
                + "Now you have 3 tasks in the list.\r\n", systemOutput.toString());
    }

    /*
    @Test
    void testSelect() throws DukeException {
        ByteArrayOutputStream freshOutput = new ByteArrayOutputStream();
        testTaskList.add("event", "Sleep /at 01-01-1970 2200");
        testCommand = new ModCommand("select", "5 1");
        System.setOut(new PrintStream(freshOutput)); //sets the system output to a different stream
        testCommand.execute(testTaskList, testUi, testStorage, testList, this.degreesManager);
        assertEquals("Tentative Date selected successfully\r\n", freshOutput.toString());
    }

     */
}