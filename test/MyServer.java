package test;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.net.ServerSocket;

public class MyServer {
    ClientHandler ch;
    int port;
    boolean stop;

    public MyServer(int port, ClientHandler ch){
        this.port = port;
        this.ch = ch;
    }

    public void start(){
        new Thread(()->startServer()).start();
    }

    private void startServer(){
        ServerSocket server = null;
        try{
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while(!stop){
                try{
                    Socket aClient = server.accept();
                    try{
                        ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                        //ch.close();
                        aClient.close();
                    }catch(IOException e){}
                    
                }catch(SocketTimeoutException e){}
            }
            server.close();
        }catch(IOException ignored){}
    }

    public void close(){
        stop = true;
    }
}
