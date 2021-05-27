import java.net.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Folder.Job;
import Folder.Server;

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
        }
    }

    public void getCapable() {

    }

    public static void main(String args[]) throws Exception {
        String output = "";
        Client client = new Client();

        output = client.sendMessage("HELO");
        System.out.printf("RCVD %s \n", output);
        output = client.sendMessage("AUTH " + System.getProperty("user.name"));
        System.out.printf("RCVD %s \n", output);
        output = client.sendMessage("REDY");
        System.out.printf("RCVD %s \n", output);

        if (!output.contains("NONE")) {
            boolean processing = true;
            Server[] servers = client.getServers("ds-system.xml");
            Server largestServer = client.getLargestServer(servers);
            while (processing) {
                // if(output.contains("JOBN")) {
                //     System.out.println("Hi");
                    
                //         String[] jobData = output.split("\\s+");
                //         output = client.sendMessage("GETS Capable " + Integer.parseInt(jobData[4]) + " " + Integer.parseInt(jobData[5]) + " " + Integer.parseInt(jobData[6]));
                //         System.out.printf("RCVD %s \n", output);
                        
                //         output = client.sendMessage("OK");
                //         System.out.printf("RCVD %s \n", output);
                //         String[] serverData = output.split("\n");
                //         output = client.sendMessage("OK");

                //         int jobCount = Integer.parseInt(jobData[2]);
                //         output = client.sendMessage("SCHD " + jobCount + " " + serverData[0].split("\\s+")[0] + " " + serverData[0].split("\\s+")[1]);
                //         System.out.printf("RCVD %s \n", output);
                    
                // }
                if (output.contains("OK")) {
                    output = client.sendMessage("REDY");
                    System.out.printf("RCVD %s \n", output);
                }
                if (output.contains("NONE") || output.contains("ERR")) {
                    processing = false;
                    break;
                }
                if (output.contains("JCPL")) {
                    output = client.sendMessage("REDY");
                    System.out.printf("RCVD %s \n", output);
                } else {
                    try {
                        String[] jobData = output.split("\\s+");
                        output = client.sendMessage("GETS Capable " + Integer.parseInt(jobData[4]) + " " + Integer.parseInt(jobData[5]) + " " + Integer.parseInt(jobData[6]));
                        System.out.printf("RCVD %s \n", output);
                        
                        output = client.sendMessage("OK");
                        System.out.printf("RCVD %s \n", output);
                        String[] serverData = output.split("\n");
                        output = client.sendMessage("OK");

                        int jobCount = Integer.parseInt(jobData[2]);
                        output = client.sendMessage("SCHD " + jobCount + " " + serverData[0].split("\\s+")[0] + " " + serverData[0].split("\\s+")[1]);
                        System.out.printf("RCVD %s \n", output);
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
            System.out.printf("SENT %s \n", message);
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
     * Determine the largest server
     */
    public Server getLargestServer(Server[] servers) {
        Server largestServer = servers[0];
        for (int i = 1; i < servers.length; i++) {
            if (largestServer.coreCount < servers[i].coreCount) {
                largestServer = servers[i];
            }
        }

        return largestServer;
    }

    /**
     * Closes the connection
     */
    public void closeConnection() {
        try {
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Error: Client failed to close connection");
        }
    }

    /**
     * Retrieves all the servers available in the given config path
     * 
     * @param configPath path to the XML file which holds the config for the servers
     * @return array of server objects contained in the config file
     */
    public Server[] getServers(String configPath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document config = builder.parse(new File(configPath));
            config.getDocumentElement().normalize();

            NodeList serverNodeList = config.getElementsByTagName("server");
            Server[] servers = new Server[serverNodeList.getLength()];

            for (int i = 0; i < serverNodeList.getLength(); i++) {
                Element server = (Element) serverNodeList.item(i);
                servers[i] = new Server(server.getAttribute("type"), Integer.parseInt(server.getAttribute("limit")),
                        Integer.parseInt(server.getAttribute("bootupTime")),
                        Float.parseFloat(server.getAttribute("hourlyRate")),
                        Integer.parseInt(server.getAttribute("coreCount")),
                        Integer.parseInt(server.getAttribute("memory")), Integer.parseInt(server.getAttribute("disk")));
            }

            return servers;
        } catch (Exception e) {
            System.out.println("Error: Client failed to get servers from config");
        }

        return new Server[0];
    }
}
