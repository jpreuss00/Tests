package io.ideas.engineering.ioc.logic;

import io.ideas.engineering.ioc.persistence.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class KeyValueService {

    private final Datastore datastore;

    @Autowired
    public KeyValueService(Datastore datastore) {
        this.datastore = datastore;
    }

    public String fetchValue(String key) {
        this.requireValidKey(key);
        return this.datastore.findValue(key).orElseThrow(NoSuchElementException::new);
    }

    public void persistValue(String key, String value) {
        this.requireValidKey(key);
        try {
            this.datastore.saveValue(key, value);
        } catch (IllegalArgumentException e){
            datastore.saveValue(key, "*****");
        }
    }

    public void deleteValue(String key) {
        this.requireValidKey(key);

        if (!this.datastore.deleteValue(key)) {
            throw new NoSuchElementException();
        }
    }

    private void requireValidKey(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
