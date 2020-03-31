package io.ideas.engineering.ioc.persistence;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Datastore {

    private final Map<String, String> store = new ConcurrentHashMap<>();
    private final List<String> blacklist = Arrays.asList("Arsch", "Dummkopf", "düdelü");

    public Optional<String> findValue(String key) {
        return Optional.ofNullable(store.get(key));
    }

    public void saveValue(String key, String value) {
        if(blacklist.contains(value)){
            throw new IllegalArgumentException();
        }
        this.store.put(key, value);
    }

    public boolean deleteValue(String key) {
        return store.remove(key) != null;
    }

}
