package Folder;

public class ServerCollection {

    public Server[] servers;

    public ServerCollection(String[] arrayOfServerStrings) {
        this.servers = new Server[arrayOfServerStrings.length];

        for (int i = 0; i < arrayOfServerStrings.length; i++) {
            this.servers[i] = new Server(arrayOfServerStrings[i]);
        }
    }

    /**
     * Set the servers
     */
    public void setServers(Server[] array) {
        this.servers = array;
    }

    /**
     * Return the largest server
     */
    public Server getLargestServer() {
        Server largestServer = servers[0];
        for (int i = 1; i < servers.length; i++) {
            if (largestServer.coreCount < servers[i].coreCount) {
                largestServer = servers[i];
            }
        }

        return largestServer;
    }
}
