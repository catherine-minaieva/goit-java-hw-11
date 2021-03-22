package HomeWork;

public class TaskFirstTimer implements Runnable {

    private final int period;
    private Thread t = new Thread(this);

    TaskFirstTimer(int period) {
        this.period = period;
        t.start();
    }

    @Override
    public void run() {
        try {
            while (Message.count < 15) {
                Thread.sleep(period * 1000 + 100);
                synchronized (Message.class) {
                    Message.sendMessage(Integer.toString(++Message.count));
                    Message.class.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Waiter implements Runnable {
    private final int period;
    private String mes;
    private Thread t = new Thread(this);

    Waiter(int period, String mes) {
        this.period = period;
        this.mes = mes;
        t.start();
    }

    @Override
    public void run() {
        try {
            while (Message.count < 15)
                synchronized (Message.class) {
                    Message.class.wait();
                    if (Message.count % period == 0)
                        Message.sendMessage(mes);
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Message {
    volatile static int count = 0;
    synchronized static void sendMessage(String s) {
        System.out.println(Thread.currentThread().getName() + " " + s);
    }
}

class TimerTest {
    public static void main(String[] args) {
        new TaskFirstTimer(1);
        new Waiter(5, "Прошло 5 секунд");
    }
}