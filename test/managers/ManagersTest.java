package managers;

import taskmanager.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    public void createInstanceOfManager() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        assertNotNull(inMemoryTaskManager.printEpics());
    }
}