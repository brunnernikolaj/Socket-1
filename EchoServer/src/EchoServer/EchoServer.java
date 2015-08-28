/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EchoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import static java.lang.System.out;

/**
 *
 * @author Nikolaj
 * This Class acts the server
 */
public class EchoServer {

    private static Map<String,String> translate = new HashMap<>();
    
    //A Map containing functions for transforming input, indexed by function name 
    private static Map<String,Function<String,String>> actions = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        translate.put("hund", "dog");
        translate.put("cykel", "bike");
        translate.put("fisk", "fish");
        translate.put("mand", "man");
        translate.put("kvinde", "woman");
        
        //Add functions
        actions.put("UPPER", (String x) -> { return x.toUpperCase();});
        actions.put("LOWER", (String x) -> { return x.toLowerCase();});
        actions.put("REVERSE", (String x) -> { return new StringBuilder(x).reverse().toString();});
        actions.put("TRANSLATE", (String x) -> {return translate.get(x) == null ? "Not Found" : translate.get(x); });      
        
        EchoServer Server = new EchoServer();
        Server.run();        
    }
   
    private final List<Socket> clients = new ArrayList<>();
    private final int PORT = 1111;
    
    public void run() throws IOException {
        ServerSocket SSocket = new ServerSocket(PORT);
        out.println("Server Started.");
        
        //Wait for clients to connect    
        while(true) {
            Socket currentSocket = SSocket.accept();
            clients.add(currentSocket);
            
            out.printf("Ip: %s Connected | Current client count: %d \n",currentSocket.getInetAddress(),clients.size());
            
            new Thread( new RespondTask(currentSocket,clients, actions)).start();
        }
    }

}
