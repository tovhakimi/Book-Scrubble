package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IOSearcher {
    public IOSearcher(){}

    public static boolean search(String word, String... fileNames) throws IOException{
        for(String bookName : fileNames){
            Stream<String> stream;
            try{
                stream = Files.lines(Paths.get(bookName));
                if(stream.anyMatch(line -> line.contains(word))){
                    return true;
                }
                stream.close();
            } catch(IOException e){
                System.out.println("File Error!");
            }
        }
        return false;
    }
}
