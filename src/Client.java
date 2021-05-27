import java.net.*;
import java.io.*;

import Folder.Job;
import Folder.Server;
import Folder.ServerCollection;

public class Client {

    // Server class required to find a server.

    private static Socket socket;
    private static BufferedReader inputBuffer;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

    public Client() {
        try {
            socket = new Socket("localhost", 50000);
            inputStream = new DataInputStream(socket.getInputStream());
            inputBuffer = new BufferedReader(new InputStreamReader(inputStream));
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client initialized!");
        } catch (Exception e) {
            System.out.println("Error: Client failed to initialize");
            closeConnection();
        }
    }

    public static void main(String args[]) throws Exception {
        String output = "";
        Client client = new Client();

        output = client.sendMessage("HELO");
        output = client.sendMessage("AUTH " + System.getProperty("user.name"));
        output = client.sendMessage("REDY");

        if (!output.contains("NONE")) {
            boolean processing = true;

            while (processing) {
                if (output.contains("OK")) {
                    output = client.sendMessage("REDY");
                }
                if (output.contains("NONE") || output.contains("ERR")) {
                    processing = false;
                    break;
                }
                if (output.contains("JCPL")) {
                    output = client.sendMessage("REDY");
                } else {
                    try {

                        Job job = new Job(output);
                        output = client.sendMessage("GETS Capable " + job.core + " " + job.memory + " " + job.disk);
                        output = client.sendMessage("OK");

                        ServerCollection servers = new ServerCollection(output.split("\n"));
                        Server largest = servers.getServer(job);
                        output = client.sendMessage("OK");

                        output = client.sendMessage("SCHD " + job.id + " " + largest.type + " " + largest.id);
                    } catch (Exception e) {
                    }
                }
            }
        }

        output = client.sendMessage("QUIT");
        if (output.contains("QUIT")) {
            client.closeConnection();
        }
    }

    /**
     * Send a message to the output stream (to the server)
     * 
     * @param message the message to be sent
     * @return response from the input buffer (from the server)
     */
    public String sendMessage(String message) {
        String output = "";
        try {
            message = message + "\n";
            outputStream.write(message.getBytes());
            outputStream.flush();
            output = getResponse();
        } catch (Exception e) {
            System.out.println("Error: Client failed to send message");
        }
        return output;
    }

    /**
     * Listen for a message from the input buffer (from the server)
     * 
     * @return the response from the input buffer (from the server)
     */
    public String getResponse() {
        String message = "";

        try {
            while (!inputBuffer.ready()) {
            }
            while (inputBuffer.ready()) {
                message += (char) inputBuffer.read();
            }
        } catch (Exception e) {
            System.out.println("Error: Client failed to receive message");
        }

        return message;
    }

    /**
     * Quits the client
     */
    public void closeConnection() {
        try {
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error: Client failed to close connection");
        }
    }
}
