package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DictionaryManager {
    Map <String, Dictionary> dictionaryMap;
    static DictionaryManager instance = null;

    DictionaryManager(){
        dictionaryMap = new HashMap<>();
    }

    public boolean query(String...args){
        int found = 0;
        String word = args[args.length - 1];

        for(String bookName : args){
            if(Objects.equals(word, bookName)){
                break;
            }
            if(dictionaryMap.containsKey(bookName)){
                if(dictionaryMap.get(bookName).query(word)){
                    found++;
                }
            }
            else{
                Dictionary newDictionary = new Dictionary(bookName);
                dictionaryMap.put(bookName, newDictionary);
                if(newDictionary.query(word)){
                    found++;
                }
            }
        }
        return found != 0;
    }

    public boolean challenge(String...args){
        int found = 0;
        String word = args[args.length - 1];

        for(String bookName : args){
            if(Objects.equals(word, bookName)){
                break;
            }
            if(dictionaryMap.containsKey(bookName)){
                if(dictionaryMap.get(bookName).challenge(word)){
                    found++;
                }
            }
            else{
                Dictionary newDictionary = new Dictionary(bookName);
                dictionaryMap.put(bookName, newDictionary);
                if(newDictionary.query(word)){
                    found++;
                }
            }
        }
        return found != 0;
    }

    public int getSize(){
        return dictionaryMap.size();
    }

    public static DictionaryManager get(){
        if(instance == null){
            instance = new DictionaryManager();
        }
        return instance;
    }
}
