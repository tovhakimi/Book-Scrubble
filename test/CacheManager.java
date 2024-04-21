package test;

import java.util.Set;
import java.util.HashSet;

public class CacheManager {
	private final int size;
    private final CacheReplacementPolicy crp;
    private final Set<String> cache;

    public CacheManager(int size, CacheReplacementPolicy crp){
        this.size = size;
        this.crp = crp; 
        this.cache = new HashSet<>();
    }
	
    public boolean query(String word){
        return cache.contains(word);
    }

    public void add(String word){
        crp.add(word);

        if(cache.size() >= size){
            String removedWord = crp.remove();//get the word to remove from the crp
            cache.remove(removedWord);//remove the word from the cache
        }
        cache.add(word);
    }
}
