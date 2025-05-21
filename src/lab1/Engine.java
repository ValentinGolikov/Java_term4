package lab1;

enum ProgramState {
    UNKNOWN, STOPPING, RUNNING, FATAL_ERROR
}

public class Engine {
    public static void main(String[] args) {
        AbstractProgram program = new AbstractProgram(100, 1000);
        Thread programThread = new Thread(program);
        programThread.setDaemon(true);
        programThread.start();

        Supervisor supervisor = new Supervisor(program);
        Thread supervisorThread = new Thread(supervisor);
        supervisorThread.start();
        try {
            supervisorThread.join();
            //programThread.join();
        } catch (InterruptedException e){
            System.out.println("Engine terminated:");
        }
    }
}
