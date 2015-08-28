/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikolaj
 */
public class RespondTask implements Runnable {

    private final Socket socket;
    private final List<Socket> clients;
    private final Map<String, Function<String, String>> actions;

    public RespondTask(Socket socket, List<Socket> clients, Map<String, Function<String, String>> actions) {
        this.socket = socket;
        this.clients = clients;
        this.actions = actions;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            String input;
            while (!(input = br.readLine()).equals("close")) {
                try {

                    String[] action = input.split("#");
                    String returnVal = actions.get(action[0].toUpperCase()).apply(action[1]);

                    for (Socket soc : clients) {
                        PrintStream output = new PrintStream(soc.getOutputStream());
                        output.printf("Client %d: %s \n", clients.indexOf(socket) + 1, returnVal);
                    }
                } catch (Exception ex)  {
                    
                    break;
                }
            }
            System.out.printf("Client %d: Disconnected \n", clients.indexOf(socket) + 1);
            clients.remove(clients.indexOf(socket));
        } catch (IOException ex) {
            Logger.getLogger(RespondTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
