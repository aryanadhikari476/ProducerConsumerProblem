class SharedResource{
    private int value = 0; 
    private boolean available = false;
    public synchronized void produce(int val) {
    while (available) {
        try {
            wait();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    value = val;
    available = true;
    System.out.println("Produced: " + value);
    notify();
}
public synchronized void consume() {
    while (!available) {
        try {
            wait();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    System.out.println("Consumed: " + value);
    available = false;
    notify();
}
}
public class main{
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.produce(i);
            }
        });
        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                resource.consume();
            }
        });
        producerThread.start();
        consumerThread.start();
    }
}  