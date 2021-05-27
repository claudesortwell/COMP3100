package Folder;

public class Server {

    public String type;
    public int limit;
    public int bootupTime;
    public float hourlyRate;
    public int coreCount;
    public int memory;
    public int disk;

    public Server(String type, int limit, int bootupTime, float hourlyRate, int coreCount, int memory, int disk) {
        this.type = type;
        this.limit = limit;
        this.bootupTime = bootupTime;
        this.hourlyRate = hourlyRate;
        this.coreCount = coreCount;
        this.memory = memory;
        this.disk = disk;
    }
}
