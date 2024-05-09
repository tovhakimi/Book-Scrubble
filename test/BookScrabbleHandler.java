package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class BookScrabbleHandler implements ClientHandler {
    private final DictionaryManager dictionaryManager;

    public BookScrabbleHandler(){
        this.dictionaryManager = new DictionaryManager();
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inFromclient));
            PrintWriter writer = new PrintWriter(outToClient))

        {    
            String message = reader.readLine();
            String[] splittedMessage = message.split(","); //String.split(delimiter)
            String type = splittedMessage[0];
            String[] booksName = Arrays.copyOfRange(splittedMessage, 1, splittedMessage.length - 1);
            String wordQueried = splittedMessage[splittedMessage.length - 1];
            boolean wordCheck = false;
            
            if(type.equals("Q")){
                for(String bookName : booksName){
                    wordCheck = dictionaryManager.query(bookName, wordQueried);
                    if(wordCheck){
                        break;
                    }
                }
            }else if(type.equals("C")){
                for(String bookName : booksName){
                    wordCheck = dictionaryManager.challenge(bookName, wordQueried);
                    if(wordCheck){
                        break;
                    }
                }
            }
            if(wordCheck){
                writer.println("true" + "\n");
            }
            else{
                writer.println("false" + "\n");
            }

            writer.flush();
        }catch(IOException e){}
    }
    
    @Override
    public void close(){}
}
