import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BlockchainServer {
    private final Set<ClientHandler> clients = new HashSet<>();
    private final int port;
    private final Blockchain blockchain;


    public BlockchainServer(int port, Blockchain blockchain) {
        this.port = port;
        this.blockchain = blockchain;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                System.out.println("New peer connected: " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BlockchainServer server;
    private PrintWriter out;
    private BufferedReader in;
    private final Gson gson = new Gson();


    public ClientHandler(Socket socket, BlockchainServer server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Validator validator = new Validator();
        try {
            // Send current blockchain to new peer
            sendMessage("CHAIN:" + gson.toJson(Blockchain.chain));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("CHAIN:")) {
                    String chainJson = inputLine.substring(6);
                    ArrayList<Block> receivedChain = gson.fromJson(chainJson, new TypeToken<List<Block>>(){}.getType());

                    if (validator.Validator(receivedChain)) {
                        Blockchain.chain = receivedChain;
                        server.broadcast(inputLine); // rebroadcast valid chain
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void sendMessage(String message) {
        out.println(message);
    }
}