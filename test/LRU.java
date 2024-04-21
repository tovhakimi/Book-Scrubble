package test;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy{
    private LinkedList<String> cache;

    public LRU(){
        cache = new LinkedList<>();
    }

    @Override
    public void add(String word){
        if(cache.contains(word)){
            cache.remove(word);
            return;
        }
        cache.add(word);
    }

    @Override
    public String remove(){
      return cache.removeFirst();
    }
}
