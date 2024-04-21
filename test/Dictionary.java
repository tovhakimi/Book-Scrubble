package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Dictionary {
    CacheReplacementPolicy lru;
    CacheReplacementPolicy lfu;
    final private CacheManager existingWords;
    final private CacheManager nonExistingWords;
    private BloomFilter bloomFilter;
    private final String[] fileNames;

    public Dictionary(String... fileNames){
        lru = new LRU();
        existingWords = new CacheManager(400, lru);
        lfu = new LFU();
        nonExistingWords = new CacheManager(100, lfu);
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");

        this.fileNames = fileNames.clone();
        
        for(String fileName : fileNames){
            addWordsToBloomFilter(fileName);
        }

    }

    private void addWordsToBloomFilter(String fileName){
        try{
            Scanner scanner = new Scanner(new File(Paths.get(fileName).toUri()));
            while(scanner.hasNext()){
                bloomFilter.add(scanner.next());
            }
        } catch(FileNotFoundException e){
            System.err.println("File wasn't found!");
        }
    }

    public boolean query(String word){
        
        boolean result = existingWords.query(word);
        if(result){
            return true;
        }

        result = nonExistingWords.query(word);
        if(result){
            return false;
        }

        if(bloomFilter.contains(word)){
            existingWords.add(word);
            return true;
        }else{
            nonExistingWords.add(word);
            return false;
        }
    }

    public boolean challenge(String word){
        try{
            boolean found = IOSearcher.search(word, fileNames);
            if(found){
                existingWords.add(word);
            }else{
                nonExistingWords.add(word);
            }
            return found;
        } catch(IOException e){
            System.err.println("ERROR!");
            return false;
        }
    }
}
