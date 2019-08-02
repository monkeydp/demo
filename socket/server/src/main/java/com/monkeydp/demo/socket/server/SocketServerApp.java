package com.monkeydp.demo.socket.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author iPotato
 * @date 2019/8/1
 */
@Slf4j
public class SocketServerApp {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("Start server app...");
        SocketServerApp app = new SocketServerApp();
        app.init();
    }

    @SneakyThrows
    public void init() {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for client to connect...");
        while (true) {
            Socket client = serverSocket.accept();
            new Handler(client);
        }
    }

    private static class Handler implements Runnable {

        private Socket client;

        public Handler(Socket client) {
            this.client = client;
            executor().execute(this);
        }

        private Executor executor() {
            BasicThreadFactory threadFactory = new BasicThreadFactory
                    .Builder()
                    .namingPattern("scheduled-thread-pool-%d")
                    .build();
            ScheduledThreadPoolExecutor executor =
                    new ScheduledThreadPoolExecutor(1, threadFactory);
            return executor;
        }

        @Override
        @SneakyThrows
        public void run() {
            try (
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    DataOutputStream out = new DataOutputStream(client.getOutputStream());
            ) {
                // # Receive message from client
                System.out.println(String.format("\n%s: %s", client.getRemoteSocketAddress(), in.readUTF()));

                // # Send message to client
                System.out.print("Please entry: ");
                // ## Entry by keyboard
                String message = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(message);
            } finally {
                if (client != null) {
                    client.close();
                    client = null;
                }
            }
        }
    }
}
