package io.ideas.engineering.ioc;

import io.ideas.engineering.ioc.persistence.Datastore;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class DatastoreTest {

    private Datastore datastore;

    @Before
    public void setUp() throws Exception {
        this.datastore = new Datastore();
    }

    @Test
    public void findValue_shouldFind_existingKey(){
        datastore.saveValue("knownKey", "knownValue");

        Optional<String> actual = datastore.findValue("knownKey");
        Optional<String> expected = Optional.of("knownValue");

        assertEquals(actual, expected);
    }

    @Test
    public void findValue_shouldNotFind_unknownKey(){
        Optional<String> actual = datastore.findValue("unknownKey");
        Optional<String> expected = Optional.empty();

        assertEquals(actual, expected);
    }

    @Test (expected = NullPointerException.class)
    public void findValue_shouldThrow_nullPointer_onNull() throws NullPointerException {
        Optional<String> actual = datastore.findValue(null);
    }

    @Test
    public void saveValue_shouldSave_newKeyValuePair(){
        datastore.saveValue("Key", "Value");
        Optional<String> expected = Optional.of("Value");

        assertEquals(datastore.findValue("Key"), expected);
    }

    @Test (expected = NullPointerException.class)
    public void saveValue_shouldThrow_nullPointer_onNull() throws NullPointerException {
        datastore.saveValue(null, "Test");
    }

    @Test
    public void deleteValue_shouldDelete_existingKey(){
        datastore.saveValue("deletedKey", "foobar");

        boolean actual = datastore.deleteValue("deletedKey");

        assertTrue(actual);
    }

    @Test (expected = NullPointerException.class)
    public void deleteValue_shouldThrow_nullPointer_onNull() throws NullPointerException {
        boolean actual = datastore.deleteValue(null);
    }
}
