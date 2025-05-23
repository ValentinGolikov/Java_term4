package lab1;

public class Supervisor implements Runnable {
    private final AbstractProgram program;
    private boolean running = true;

    public Supervisor(AbstractProgram program) {
        this.program = program;
    }

    public void startAbstractProgram() {
        synchronized (program.monitor){
            System.out.println("Supervisor: Перезапускаю...");
            program.setState(ProgramState.RUNNING);
        }
    }

    public void stopAbstractProgram() {
        synchronized (program.monitor){
            program.setState(ProgramState.FATAL_ERROR);
        }
    }

    public void stopSupervisor() {
        synchronized (program.monitor){
            this.running = false;
            program.running = false;
            program.monitor.notifyAll();
        }
    }

    @Override
    public void run() {
        System.out.println("Supervisor: Запущен");
        while (running) {
            synchronized (program.monitor) {
                ProgramState state = program.getState();
                System.out.println("Supervisor: Текущее состояние: " + state);
                try {
                    switch(state) {
                        case STOPPING:
                            startAbstractProgram();
                            //stopAbstractProgram();
                            break;
                        case FATAL_ERROR:
                            System.out.println("Supervisor: Завершение работы абстрактной программы...");
                            stopSupervisor();
                            break;
                    }
                    program.monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Supervisor: Прерывание...");
                    return;
                }
            }
        }
        System.out.println("Supervisor: Завершение работы...");
    }
}
