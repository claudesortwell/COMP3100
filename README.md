# Simple Job Dispatcher for Distributed Systems

Developed for the COMP3100 unit at Macquarie University by:
* **Bharosha Poudel** – 45705879
* **Claude Sortwell** – 45634947
* **Nathan Lecompte** – 45423725

### About this Project:
This project involves the design and development of a simple job scheduler for a simulated
distributed system. The key parts responsible for facilitating this client-server simulation can be
separated into two components: a client-side simulator and a server-side simulator. This document
describes stage one of the project, which involves the design and development of a simple
client-side simulator which can communicate using the ‘ds-sim’ simulation protocol and dispatch
jobs using the ‘allToLargest’ algorithm (jobs are dispatched to the server with the largest core
count).

### Important Files:
* `./COMP3100 - Group 44 - Simple Job Dispatcher for Distributed Systems.pdf` - A detailed report on the design and implementation of the simple job dispatcher.
* `/src/Client.java` - The client-side simulator developed during the project.