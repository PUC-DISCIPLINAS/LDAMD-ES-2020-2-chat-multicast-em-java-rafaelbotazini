package chatmulticast.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;

import chatmulticast.networking.MessageReceivedHandler;
import chatmulticast.networking.MulticastPeer;
import chatmulticast.networking.UDPClient;

public class Client {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final UDPClient udpClient;

    private Thread multicastThread;
    private MulticastPeer multicastPeer;

    private Client(String host, int port) throws UnknownHostException, SocketException {
        this.udpClient = new UDPClient(host, port);
        connect();
    }

    public static Client create(String host, int port) throws UnknownHostException, SocketException {
        return new Client(host, port);
    }

    private void connect() {
        String username = getUsername();
        System.out.println("Bem vindo, " + username + "!");

        startChat("");
    }

    private String getUsername() {
        while (true) {
            try {
                System.out.println("Escolha um nome de usuário:");
                String username = reader.readLine().trim();

                String response = udpClient.send("CONNECT " + username);
                System.out.println("[SERVER]: " + response);

                return username;
            } catch (IOException e) {
                System.out.println("Ocorreu um erro. Tente novamente. " + e.getMessage());
            }
        }
    }

    private void startChat(String groupId) {
        // Start multicast receive thread
//        multicastPeer = new MulticastPeer(groupId);
//
//        multicastPeer.setReceiveHandler(new MessageReceivedHandler() {
//            @Override
//            public void handle(String message) {
//                System.out.println(message);
//            }
//        });
//
//        multicastThread = new Thread(multicastPeer);
//        multicastThread.run();

        // await message send input
        while (true) {
            try {
                String message = reader.readLine().trim();
                String response = udpClient.send(message);
                System.out.println("[SERVER]: " + response);
            } catch (IOException e) {
                System.out.println("[ERRO]: Erro ao processar a mensagem.\n\t" + e.getMessage());
            }
        }
    }
}
