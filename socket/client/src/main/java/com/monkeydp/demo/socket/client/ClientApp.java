package com.monkeydp.demo.socket.client;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author iPotato
 * @date 2019/8/1
 */
public class ClientApp {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    /**
     * When the TERMINATOR is received, the client will terminate
     */
    private static final String TERMINATOR = "Bye";

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Start client app...");
        while (true) {
            Socket server = new Socket(SERVER_HOST, SERVER_PORT);

            try (
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
            ) {
                // # Send message to server
                System.out.println("Please entry: \t");
                // ## Entry by keyboard
                String outputMessage = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(outputMessage);

                // # Received message from server
                String inputMessage = in.readUTF();
                System.out.println(String.format("\n%s: %s", server.getRemoteSocketAddress(), inputMessage));
                if (TERMINATOR.equals(inputMessage)) {
                    System.out.println("Stop client app..");
                    Thread.sleep(500);
                    break;
                }
            } finally {
                if (server != null) {
                    server.close();
                    server = null;
                }
            }
        }
    }
}
