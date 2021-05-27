package Folder;

import java.util.ArrayList;
import java.util.Arrays;

public class ServerCollection {

    public Server[] servers;
    public static int MAX_WAITING_JOBS = 1;

    public ServerCollection(String[] arrayOfServerStrings) {
        this.servers = new Server[arrayOfServerStrings.length];

        for (int i = 0; i < arrayOfServerStrings.length; i++) {
            this.servers[i] = new Server(arrayOfServerStrings[i]);
        }

        Arrays.sort(servers);
    }

    /**
     * Return A Server for the Job
     * 
     * Using an very simple Algorithm that just looks at all the capable servers
     * gets all the ones that have cores equal or greater than the job requirement
     * and assigns to to the largest server. Which has less than or equal to one
     * waiting waiting job, otherwise it will assign it to the
     * 
     */
    public Server getServer(Job job) {

        // System.out.println(job);

        Server chosenServer = servers[0];

        // for (int i = 0; i < servers.length; i++) {
        // System.out.println(servers[i]);
        // }

        ArrayList<Server> exactMatchServers = new ArrayList<Server>();

        // If one matches job and its not backlogged
        for (int i = 0; i < servers.length; i++) {
            if (servers[i].coreCount >= job.core) {
                exactMatchServers.add(servers[i]);
            }
        }

        if (exactMatchServers.size() > 0) {
            chosenServer = exactMatchServers.get(exactMatchServers.size() - 1);

            // If one matches job and its not backlogged
            for (int i = exactMatchServers.size() - 1; i > 0; i--) {
                if (exactMatchServers.get(i).waitingJobs <= MAX_WAITING_JOBS) {
                    chosenServer = exactMatchServers.get(i);
                    break;
                }
            }

        }

        // System.out.println("CHOSEN: " + chosenServer);

        return chosenServer;
    }
}
