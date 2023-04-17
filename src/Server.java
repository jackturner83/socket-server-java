package src;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server class sets up a server socket on a specified port and listens for incoming client connections.
 * Once a client connects, a new thread is created to handle the client's requests.
 */
public class Server {

    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    /**
     * Constructs a new Server object with the specified ServerSocket.
     *
     * @param serverSocket the ServerSocket to use for the server
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    /**
     * Starts the server and listens for incoming client connections. Once a client connects, a new thread is created to handle the client's requests.
     */
    public void startServer() {

        try{
            while (!serverSocket.isClosed()) { // while server is open

                Socket socket = serverSocket.accept(); // Create a new Socket object named "socket"
                System.out.println("A new client has connected!"); // print to terminal that a new client has appeared
                ClientHandler clientHandler = new ClientHandler(socket); // New client object (normally only able to have one client at a time, this allows us to have more than one client.)

                threadPool.execute(clientHandler);
                /*
                 * By using a thread pool, you limit the number of threads that your server creates and instead reuse *a pool of threads to handle client requests. This can help avoid the overhead of creating and *destroying threads for each client request, which can be a costly operation.
                 */
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the server socket.
     */
    public void closeServerSocket () {
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e ) {
            e.printStackTrace();
        }
    }

    /**
     * The main method that starts the server on the specified port.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args){

        try{
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
