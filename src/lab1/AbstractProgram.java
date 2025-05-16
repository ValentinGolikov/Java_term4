package lab1;

import java.util.Random;

public class AbstractProgram implements Runnable {
    private ProgramState state;                     //состояние абстрактной программы
    private final Random random = new Random();     //рандомайзер
    private final int minInterval;                  //минимальный интервал рандомайзера
    private final int maxInterval;                  //максимальный интервал рандомайзера
    private boolean running = true;                //работает ли абстрактная программа
    private boolean flag = false;
    public final Object monitor = new Object();

    //конструктор с параметрами в виде интервала
    public AbstractProgram(int minInterval, int maxInterval) {
        this.minInterval = minInterval;
        this.maxInterval = maxInterval;
        this.state = ProgramState.UNKNOWN;
    }

    //получение состояния программы
    public ProgramState getState() {
        synchronized (monitor) {
            return state;
        }
    }

    //установка состояния программы
    public void setState(ProgramState state) {
        synchronized (monitor) {
            this.state = state;
            flag = true;
            monitor.notifyAll();
        }
    }

    public void stopAbstractProgram() {
        synchronized (monitor) {
            running = false;
            monitor.notifyAll();
        }
    }

    @Override
    public void run(){
        while (running){
            try {
                int interval = minInterval + random.nextInt(maxInterval - minInterval + 1);

                Thread.sleep(interval);

                //случайное изменение состояния
                synchronized (monitor){
                    if (!flag) {
                        ProgramState newState = ProgramState.values()[random.nextInt(ProgramState.values().length)];
                        //предотвращаем изменение state на UNKNOWN
                        while (newState == ProgramState.UNKNOWN) {
                            newState = ProgramState.values()[random.nextInt(ProgramState.values().length)];
                        }

                        //записываем новое состояние программы
                        state = newState;
                        if (running) {
                            System.out.println("AbstractProgram: Состояние изменено");
                        }
                    }
                    flag = false;
                    monitor.notifyAll();        //уведомляем супервизор об изменении состояния
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("AbstractProgram: Прерывание...");
                return;
            }
        }
        System.out.println("AbstractProgram: Завершение работы...");
    }
}
