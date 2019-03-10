package com.wongnai.interview.movie;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class MovieInvertedIndexRepository {
    private Map<String, Set<Long>> map = new HashMap<>();

    public void saveWord(String key, Movie movie) {
        if (!map.containsKey(key.toLowerCase())) {
            map.put(key.toLowerCase(), new HashSet<>());
        }
        map.get(key.toLowerCase()).add(movie.getId());
    }

    public Set<Long> findWord(String queryText) {
        if(map.containsKey(queryText.toLowerCase())){
            return map.get(queryText.toLowerCase());
        }
        return null;
    }
}
