package storage;

import exception.DukeException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageTest {

    Storage testStorage = new Storage("dummy.txt", "dummydegree.txt");

    StorageTest() throws IOException {
    }

    @Test
    void testLoad() throws DukeException {
        //String buffer = testStorage.load();
        //assertEquals("", buffer);
    }

    @Test
    void testStore() {
    }
}