/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikolaj
 * This Class is used to listen to output from the server
 */
public class ClientListnerThread extends Thread{
    BufferedReader reader;

    public ClientListnerThread(BufferedReader reader) {
        this.reader = reader;
    }
    
    @Override
    public void run() {
        while(true){
            try { 
                System.out.println(reader.readLine());
                Thread.sleep(20);
            } catch (IOException | InterruptedException ex) {
                break;
            }
        }
    }
}
