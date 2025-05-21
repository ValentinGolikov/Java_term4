package lab1;

public class Engine {
    public static void main(String[] args) {
        AbstractProgram program = new AbstractProgram(1000, 3000);
        Thread programThread = new Thread(program);

        Supervisor supervisor = new Supervisor(program);
        Thread supervisorThread = new Thread(supervisor);

        supervisorThread.start();
        programThread.start();

        try {
            supervisorThread.join();
            programThread.join();
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Engine terminated: " + e);
        }

    }
}
