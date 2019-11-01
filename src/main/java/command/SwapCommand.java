package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import list.DegreeListStorage;
import storage.Storage;
import task.TaskList;
import ui.UI;

import java.io.IOException;

public class SwapCommand extends Command{
    private String command;
    private String input;
    private DegreeListStorage dd = new DegreeListStorage();
    private int listType = 0;

    public SwapCommand(String command, String input) {
        this.command = command;
        this.input = input;
    }

    /**
     * Overwrites default execute to modify task in tasks.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI prints messages
     * @param storage Storage loads and saves files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException DukeException throws exception
     */
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        DegreeList degreesBuffer;
        TaskList tasksBuffer;

        switch (this.command) {
        case "swap":
            this.listType = 1; //1 for degree list

            degreesBuffer = lists.deepClone();
            memento = new Memento(degreesBuffer);
            lists.swap(this.input, this.dd);
            break;
        default:
            throw new DukeException("Invalid SwapCommand");
        }
    }
}
