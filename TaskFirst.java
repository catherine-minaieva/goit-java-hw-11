package HomeWork;

//Напишите программу, которая каждую секунду отображает на экране данные о времени,
// прошедшем от начала сессии (запуска программы).
// Другой ее поток выводит каждые 5 секунд сообщение "Прошло 5 секунд".
// Предусмотрите возможность ежесекундного оповещения потока, воспроизводящего сообщение, потоком, отсчитывающим время.

public class TaskFirst {
    public void app(int lifetime) throws InterruptedException {
            Counter counter = new Counter();
            Thread1 t1 = new Thread1(counter);
            Thread2 t2 = new Thread2(counter, "Прошло 5 секунд!");

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

        class Thread1 extends Thread {

            private Counter app;

            public Thread1(Counter app) {
                this.app = app;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    this.app.add();
                }
            }
        }

        class Thread2 extends Thread {

            private Counter app;
            private String message;

            public Thread2(Counter app, String message) {
                this.app = app;
                this.message = message;
            }

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    int n;
                    try {
                        n = app.last();
                        System.out.println(n);
                        if (n % 5 == 0)
                            System.out.println(message);
                        } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        class Counter {

            private int number = 0;

            public synchronized void add() {
                this.number++;
                notify();
            }

            public synchronized int last() throws InterruptedException {
                wait();
                return this.number;
            }
        }
    }

