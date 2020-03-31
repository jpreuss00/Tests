package io.ideas.engineering.ioc;

import io.ideas.engineering.ioc.logic.KeyValueService;
import io.ideas.engineering.ioc.persistence.Datastore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

public class KeyValueServiceTest {

    private KeyValueService keyValueService;
    private Datastore datastore;

    @Before
    public void setUp() throws Exception {
        this.datastore = Mockito.mock(Datastore.class);
        this.keyValueService = new KeyValueService(datastore);
    }

    @Test (expected = NoSuchElementException.class)
    public void fetchValue_shouldThrow_NoSuchElement_onUnknownKey() throws NoSuchElementException{
        String actual = keyValueService.fetchValue("unknownKey");
    }

    @Test (expected = IllegalArgumentException.class)
    public void fetchValue_shouldThrow_IllegalArgument_onInvalidKey() throws IllegalArgumentException{
        String actual = keyValueService.fetchValue(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void persistValue_shouldThrow_IllegalArgument_onInvalidKey() throws IllegalArgumentException{
        keyValueService.persistValue(null, "Value");
    }

    @Test (expected = NullPointerException.class)
    public void persistValue_shouldThrow_IllegalArgument_onBlacklistValue(){
        Mockito.doThrow(IllegalArgumentException.class).when(datastore).saveValue("Key", "Arsch");
        Mockito.doThrow(NullPointerException.class).when(datastore).saveValue("Key", "*****");

        keyValueService.persistValue("Key", "Arsch");
    }

    @Test
    public void persistValue_shouldThrow_IllegalArgument_onBlacklistValue2(){
        Mockito.doThrow(IllegalArgumentException.class).when(datastore).saveValue("Key", "Arsch");
        keyValueService.persistValue("Key", "Arsch");
        Mockito.verify(datastore).saveValue("Key", "*****");
    }

    @Test (expected = IllegalArgumentException.class)
    public void deleteValue_shouldThrow_IllegalArgument_onInvalidKey() throws IllegalArgumentException{
        keyValueService.deleteValue(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void deleteValue_shouldThrow_NoSuchElement_onUnknownKey() throws NoSuchElementException{
        keyValueService.deleteValue("unknownKey");
    }

}
