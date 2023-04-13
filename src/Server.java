package src;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try{
            while (!serverSocket.isClosed()) { // while server is open

                Socket socket = serverSocket.accept(); // Create a new Socket object named "socket"
                System.out.println("A new client has connected!"); // print to terminal that a new client has appeared
                ClientHandler clientHandler = new ClientHandler(socket); // New client object (normally only able to have one client at a time, this allows us to have more than one client.)

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

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