package HomeWork;

//Напишите программу, которая каждую секунду отображает на экране данные о времени,
// прошедшем от начала сессии (запуска программы).
// Другой ее поток выводит каждые 5 секунд сообщение "Прошло 5 секунд".
// Предусмотрите возможность ежесекундного оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время.

public class TaskFirst {
    public void app(int lifetime) throws InterruptedException {
            Counter counter = new Counter();
            Thread0 t1 = new Thread0(counter);
            Thread1 t2 = new Thread1(counter, "Прошло 5 секунд!");

            t1.start();
            t2.start();

            Thread.sleep(lifetime * 1000+100);

            t1.interrupt();
            t2.interrupt();
        }

        public static void main(String[] args) throws InterruptedException {
            TaskFirst task = new TaskFirst();
            task.app(15);
        }

        class Thread0 extends Thread {

            private Counter app;

            public Thread0(Counter app) {
                this.app = app;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        int n = app.last();
                        n++;
                        Thread.sleep(1000);
                       System.out.println(n);

                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    this.app.add();
                }
            }
        }

        class Thread1 extends Thread {

            private Counter app;
            private String message;

            public Thread1(Counter app, String message) {
                this.app = app;
                this.message = message;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    int n = 0;
                    //                        n = app.last();
//                        System.out.println(n);
                    if (n % 5 == 0)

                        System.out.println(Thread.currentThread().getName() + message);
                }
            }
        }

        class Counter {

            private int number = 0;

            public synchronized void add() {
                this.number++;
                System.out.println(Thread.currentThread().getName());
//                notifyAll();
                notify();
            }

            public synchronized int last() throws InterruptedException {
                wait();
                return this.number;
            }
        }
    }

