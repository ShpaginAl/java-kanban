package taskManager;

import task.*;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> listOfHistoryOfWatches = new ArrayList<Task>();

    @Override
    public ArrayList<Task> getHistory() {
        return listOfHistoryOfWatches;
    }

    @Override
    public void addValueInHistory(Task task) {
        if (listOfHistoryOfWatches.size() == 10) {
            for (int i = 0; i < listOfHistoryOfWatches.size()-1; i++) {
                listOfHistoryOfWatches.set(i, listOfHistoryOfWatches.get(i + 1));
            }
            listOfHistoryOfWatches.removeLast();
        }
        listOfHistoryOfWatches.add(task);
    }
}
