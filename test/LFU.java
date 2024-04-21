package test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy{
    private final LinkedHashMap<String, Integer> cache;

    public LFU(){
        cache = new LinkedHashMap<>();
    }

    @Override
    public void add(String word){
        int counter = 0;
        if(cache.containsKey(word)){
            counter = cache.get(word) + 1;
            cache.replace(word, counter);
        }else{
            cache.put(word, 1);
        }
    }

    @Override
    public String remove(){
        int count = Integer.MAX_VALUE;
        String toBeRemoved = null;

        for(Map.Entry<String, Integer> entry : cache.entrySet()){
            if(entry.getValue() < count){
                toBeRemoved = entry.getKey();
                count = entry.getValue();
            }
        }
        return toBeRemoved;
    }
}
