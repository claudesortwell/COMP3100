package Folder;

public class Server implements Comparable<Server> {

    public String type;
    public int id;
    public int timeSinceBoot;
    public int coreCount;
    public int memory;
    public int disk;
    public String available;
    public int waitingJobs;
    public int runningJobs;

    public Server(String serverString) {
        String[] serverData = serverString.split("\\s+");

        this.type = serverData[0];
        this.id = Integer.parseInt(serverData[1]);
        this.available = serverData[2];
        this.timeSinceBoot = Integer.parseInt(serverData[3]);
        this.coreCount = Integer.parseInt(serverData[4]);
        this.memory = Integer.parseInt(serverData[5]);
        this.disk = Integer.parseInt(serverData[6]);
        this.waitingJobs = Integer.parseInt(serverData[7]);
        this.runningJobs = Integer.parseInt(serverData[8]);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + this.id + " type=" + this.type + " timeSinceBoot="
                + this.timeSinceBoot + " cores=" + this.coreCount + " disk=" + this.disk + " memory=" + this.memory
                + " status=" + this.available + " waitingJobs=" + this.waitingJobs + " runningJobs=" + this.runningJobs
                + "]";
    }

    @Override
    public int compareTo(Server s) {
        return this.coreCount - s.coreCount;
    }
}
