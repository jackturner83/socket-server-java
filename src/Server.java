package src;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.SSLServerSocket;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

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

    public void closeServerSocket () {
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e ) {
            e.printStackTrace();
        }
    }
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