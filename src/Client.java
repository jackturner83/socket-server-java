package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.*;
import java.net.*;

/**
 * A simple client class that connects to a server and sends and receives messages.
 */
public class Client {
    
    private Socket socket; 
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    /**
     * Constructs a new client with the given socket and username.
     *
     * @param socket the socket to use for communication with the server
     * @param username the username to use for identification in the chat
     */
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Sends a message to the server.
     * Reads input from the user and sends it to the server.
     */
    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                scanner.close();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * Listens for messages from the server.
     * Runs on a separate thread to avoid blocking the main thread.
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    /**
     * Closes all open resources.
     *
     * @param socket the socket to close
     * @param bufferedReader the buffered reader to close
     * @param bufferedWriter the buffered writer to close
     */
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Main method to run the client.
     * Prompts the user for a username and connects to the server.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        scanner.close();

        try {
            Socket socket = new Socket("localhost", 1234);
            Client client = new Client(socket, username);
            client.listenForMessage();
            client.sendMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
