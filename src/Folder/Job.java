package Folder;

public class Job {

  public int id;
  public int submitTime;
  public int estRunTime;
  public int core;
  public int memory;
  public int disk;

  // Put the string received from the ds-server
  public Job(String jobString) {

    String[] jobData = jobString.split("\\s+");

    this.submitTime = Integer.parseInt(jobData[1]);
    this.id = Integer.parseInt(jobData[2]);
    this.estRunTime = Integer.parseInt(jobData[3]);
    this.core = Integer.parseInt(jobData[4]);
    this.memory = Integer.parseInt(jobData[5]);
    this.disk = Integer.parseInt(jobData[6]);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[id=" + this.id + " submitTime=" + this.submitTime + " estRunTime="
        + this.estRunTime + " core=" + this.core + " disk=" + this.disk + " memory=" + this.memory + "]";
  }

}
