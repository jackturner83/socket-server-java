package src;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.net.Socket;

public class ClientHandler implements Runnable{
    
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler (Socket socket) {
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter (new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
        }catch (IOException e) {

        }
    }

    @Override
    public void run(){

    }

}
