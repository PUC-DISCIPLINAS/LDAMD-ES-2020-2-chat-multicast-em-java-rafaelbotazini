package chatmulticast.cli;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import chatmulticast.networking.ReceivetHandler;
import chatmulticast.networking.UDPServer;

public class Main {

    public static void main(String[] args) {
        String command = args[0];

        try {

            switch (command) {
                case "connect": {
                    startClient(args[1], args[2]);
                    break;
                }
                case "serve": {
                    startServer(args[1]);
                    break;
                }
                default:
                    System.out.println("Commando não reconhecido");
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Número de argumentos insuficiente.");
        }
    }

    private static void startServer(String port) {
        try {
            UDPServer server = new UDPServer();

            server.setRequestHandler(new ReceivetHandler() {
                @Override
                public String reply(String message) {
                    return "Received: " + message;
                }
            });

            server.listen(Integer.parseInt(port));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR]: Porta invalida.");
        } catch (IOException e) {
            System.out.println("[ERROR | IO]: " + e.getMessage());
        }
    }

    private static void startClient(String host, String port) {
        try {
            System.out.println("Conectando...");
            Client.create(host, Integer.parseInt(port));
        } catch (NumberFormatException e) {
            System.out.println("[ERROR]: Porta invalida.");
        } catch (UnknownHostException | SocketException e) {
            System.out.println("Erro ao tentar conectar: " + e.getMessage());
        }
    }
}
