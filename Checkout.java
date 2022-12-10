

//@keil barracliffe
//@jeffrey ackah

import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Checkout
{
    private Queue<Customer> queue;
    private int numServed;
    private String name;
    private int totalWait;
    private double arrivalRate;
    
    
    public Checkout(Queue queue)
    {
        this.queue = new LinkedList<>();
        this.totalWait = 0;
        this.numServed = 0;
    }
    
    public void newCustomer(int clock, boolean showAll)
    {
        if (Math.random() < arrivalRate)
        {
            queue.add(new Customer(clock));
            if(showAll)
            {
                System.out.println("Time is " +clock+ ": " +getName()+ "line has " +queue.size()+ " customers");
            }
        }
    }
    
    public int update(int clock, boolean showAll)
    {
        Customer nextCustomer = queue.remove();
        int timeStamp = nextCustomer.getArrivalTime();
        int wait = clock - timeStamp;
        totalWait += wait;
        numServed++;
        if (showAll) {
            System.out.println("Time is " + clock
                + ": Serving "
                + getName()
                + " with time stamp "
                + timeStamp);
        }
        return clock + nextCustomer.getTimeInCheckout();
    }

    //make sure checkout is seeing correct number of items from customer
    public void processItems(int i)
    {
        System.out.println(i);
    }

    public Queue<Customer> getQueue()
    {
        return this.queue;
    }

    public void addToQueue(Customer customer)
    {
        queue.add(customer);
    }

    public void setName(int i)
    {
        this.name = ("Checkout Lane " +i+ ".");
    }

    public String getName()
    {
        return name;
    }

    public void removeCustomer()
    {
    for(Customer customer : getQueue())
    {
        customer.setCheckoutTime();
        getQueue().remove();
    }   
    }
    public void createQueue(Customer customer)
    {
        Queue tempQueue = new LinkedList<>();
        tempQueue.add(customer);
    }

    

    public int numServed()
    {
        return numServed;
    }
    
    public void setArrivalRate(double arrivalSpeed)
    {
        arrivalRate = arrivalSpeed/60;
    }
    
    // public void emptyLanes(Queue<Customer> queue)
    // {
        // while(!queue.isEmpty())
        // {
            // Customer customerFront = queue.peek();
            // elapsedTime(customerFront);
            // queue.poll();
        // }
    // }

    // public void elapsedTime(Customer customer)
    // {  
        // int i = customer.getItems();
        // while(i > 0)
        // {
            // Timer timer = new Timer();
            // timer.schedule(new RingUp(customer),0,5*1000);        
        // }
    // }
}
