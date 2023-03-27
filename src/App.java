import java.util.concurrent.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Shared 
{
    static int count = 0;
}
  
class MyThread extends Thread
{
    Semaphore sem;
    String threadName;
    Integer time;
    public MyThread(Semaphore sem, String threadName, Integer time) 
    {
        super(threadName);
        this.sem = sem;
        this.threadName = threadName;
        this.time = time;
    }
  
    @Override
    public void run() {
          
        System.out.println("Se inicio " + threadName);
        try 
        {

            System.out.println(threadName + " Se encuentra esperando un permiso");
            
            sem.acquire();
            
            System.out.println(threadName + " obtiene el permiso de pasar");

            for(int i=0; i < time; i++)
            {
                Shared.count++;
                System.out.println(threadName + ": " + time);

                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }

        System.out.println(threadName + " termino de utilizar su permiso.");
        sem.release();
    }
}
  
// Driver class
public class App 
{
    public static void main(String args[]) throws InterruptedException 
    {
        Scanner reader = new Scanner(System.in);
        Semaphore sem = new Semaphore(1);

        System.out.println("Introduce el numero de automoviles");
        int numerCars = reader.nextInt();

        System.out.println("Introduce el tiempo de espera del semaforo");
        int time = reader.nextInt();

        List<MyThread> myThreads = new ArrayList<MyThread>(numerCars);

        for(int i = 0; i < numerCars; i++)
        {
            MyThread NewCar = new MyThread(sem, "Car-" + i , time);
            myThreads.add(NewCar);
        }

        for (MyThread carIniciet : myThreads) {
            carIniciet.start();    
        }

        for (MyThread carIniciet : myThreads) {
            carIniciet.join();    
        }
          
        // count will always remain 0 after
        // both threads will complete their execution
        System.out.println("count: " + Shared.count);
    }
}