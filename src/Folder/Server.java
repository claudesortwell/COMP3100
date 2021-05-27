package Folder;

public class Server {

    public String type;
    public int id;
    public int bootTime;
    public float hourlyRate;
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
        this.bootTime = Integer.parseInt(serverData[3]);
        this.coreCount = Integer.parseInt(serverData[4]);
        this.memory = Integer.parseInt(serverData[5]);
        this.disk = Integer.parseInt(serverData[6]);
        this.waitingJobs = Integer.parseInt(serverData[7]);
        this.runningJobs = Integer.parseInt(serverData[8]);
    }
}
