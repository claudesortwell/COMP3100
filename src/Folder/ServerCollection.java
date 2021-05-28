package Folder;

import java.util.Arrays;

public class ServerCollection {

    public Server[] servers;
    public static int MAX_WAITING_JOBS = 0;

    public ServerCollection(String[] arrayOfServerStrings) {
        this.servers = new Server[arrayOfServerStrings.length];

        for (int i = 0; i < arrayOfServerStrings.length; i++) {
            this.servers[i] = new Server(arrayOfServerStrings[i]);
        }

        Arrays.sort(servers);
    }

    /**
     * Algorithm for return A Server for the Job
     */
    public Server getServer(Job job) {

        Server chosenServer = null;

        // If one matches job and its not backlogged
        for (int i = servers.length - 1; i >= 0; i--) {
            if (servers[i].coreCount >= job.core && servers[i].disk >= job.disk && servers[i].memory >= job.memory
                    && servers[i].waitingJobs == 0) {
                chosenServer = servers[i];
                break;
            }
        }

        if (chosenServer == null) {
            for (int i = servers.length - 1; i >= 0; i--) {
                if (servers[i].disk >= job.disk && servers[i].memory >= job.memory && servers[i].waitingJobs == 0) {
                    chosenServer = servers[i];
                    break;
                }
            }

        }

        if (chosenServer == null) {
            chosenServer = servers[0];
        }

        return chosenServer;
    }
}
