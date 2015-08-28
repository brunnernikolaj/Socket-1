
package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import static java.lang.System.out;

/**
 *
 * @author Nikolaj
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }
    
    private final int PORT = 1111;
    
    public void run() throws IOException {
        Socket socket = new Socket("localhost",PORT);
        out.printf("Connected to Server at #s \n",socket.getInetAddress());
                    
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);       
        ClientListnerThread clt = new ClientListnerThread(br);
        clt.start();
        out.println("ListnerThread Started");
        
        InputStreamReader consoleIn = new InputStreamReader(System.in);
        BufferedReader consoleBr = new BufferedReader(consoleIn);
        
        PrintStream ps = new PrintStream(socket.getOutputStream());
        
        //Read console input while input is not Exit
        String input = null;
        while (!(input = consoleBr.readLine()).equals("Exit")) {
            ps.println(input);
        }
        
        //Disconnect client from server
        ps.println("close");
        clt.interrupt();
        socket.close();
    }
}
