package HomeWork;

import Examples.notify_all.Parking;

public class Clock {

    private int time;
    private int end;
    private final Object monitor = new Object();

    public Clock(int time, int end) {
        this.time = 0;
        this.end = end;
    }

    public void counting() throws InterruptedException {
        synchronized (monitor){
            while (time > 0 && time < end) {

                System.out.println("секунда " + this.time);
                this.time++;
                monitor.wait();
            }
            monitor.notifyAll();
        }
    }

    public void printMessage() throws InterruptedException {
        synchronized (monitor) {
            if (time % 5 == 0) {
                System.out.println("Прошло 5 секунд");
                monitor.wait();
            }

            monitor.notifyAll();
        }
    }
}

class Test {
    Clock clock = new Clock(0, 15);

    public static void main(String[] args) {
        Test test1 = new Test();

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() ->{
                try {
                    test1.chronometr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        }
    }

    private void chronometr() throws InterruptedException {

        clock.counting();
        Thread.sleep(5000);
        clock.printMessage();
        //System.out.println("Car " + Thread.currentThread().getName() + "is gone from parking");
    }
}
