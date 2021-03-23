package HomeWork;

public class TaskFirstTimer implements Runnable {

    private final int second =  1;
    private Thread t = new Thread(this);

    TaskFirstTimer() {
          t.start();
    }

    @Override
    public void run() {
        try {
            while (Message.count < Waiter.time) {
                Thread.sleep(second * 1000 + 100);
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
    public static int time;
    private Thread t = new Thread(this);

    Waiter(int period, int time) {
        this.period = period;
        this.time = time;
        t.start();
    }

    @Override
    public void run() {
        try {
            while (Message.count < time)
                synchronized (Message.class) {
                    Message.class.wait();
                    if (Message.count % period == 0)
                        Message.sendMessage("Прошло 5 секунд");
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
        new TaskFirstTimer();
        new Waiter(5, 15);
    }
}