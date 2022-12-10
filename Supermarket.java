import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Write a description of class Supermarket here.
 *
 * @keil barracliffe
 *@jeffrey ackah
 * @version (a version number or a date)
 */
public class Supermarket
{
    // instance variables - replace the example below with your own
    private int numSuper;
    private int numExp;
    private int numStandLines;
    private int arrivalRate;
    private int maxItems;
    private int maxSimTime;
    private int input;
    private int clock;
    private int totalTime;
    private int timeDone;
    private char z;
    private boolean showAll;
    private Scanner scanner;
    private Checkout checkout;
    private ArrayList<Customer> customers;
    private ArrayList<Checkout> checkoutLanes;
    private Queue <Customer> customersInStore;
    private Queue <Customer> superCheckout;
    private Queue <Customer> expressCheckout1;
    private Queue <Customer> expressCheckout2;
    private Queue <Customer> checkoutLane;
    private Queue <Customer> standLane;
    private SuperExpressCheckout super1;
    private ExpressCheckout ec1;
    private ExpressCheckout ec2;
    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket()
    {
        // initialise instance variables
        scanner = new Scanner(System.in);
        customers = new ArrayList<>();

        numSuper = 10;
        numExp = 20;
        maxItems = 50;
        this.expressCheckout1 = new LinkedList<>();
        this.expressCheckout2 = new LinkedList<>();
        this.superCheckout = new LinkedList<>();
        super1 = new SuperExpressCheckout(superCheckout);
        ec1 = new ExpressCheckout(expressCheckout1);
        ec2 = new ExpressCheckout(expressCheckout2);
    }
    //takes in user input for total number of shoppers
    public void fillMarket()
    {
        System.out.println("How many customers are in the store today?");
        input = scanner.nextInt();
        System.out.println("How long are we simulating?");
        totalTime = scanner.nextInt();
        System.out.println("Would you like to print a trace of this simulation?");
        z = this.scanner.next().charAt(0);
        if(Character.toLowerCase(z) == 'y')
        {
            showAll = true;
        }
        
        shoppingCart(showAll, input);
            
   
    }
    //adds shoppers
    public void shoppingCart(boolean showAll, int input)
    {
        customersInStore = new LinkedList<Customer>();

        if (Math.random() < input/60) {
            customersInStore.add(new Customer(clock));
            if (showAll) {
                System.out.println("Time is "
                    + clock + "seconds: "
                    + " arrival, new queue size is "
                    + customersInStore.size());
            }

        }
    }
    //return queue of customers with items
    public Queue<Customer> getQueue()
    {
        return this.customersInStore;
    }

    public void runSim()
    {
        for(clock = 0; clock < totalTime; clock++)
        {
            super1.newCustomer(clock,showAll);
            ec1.newCustomer(clock,showAll);
            ec2.newCustomer(clock,showAll);
            for(Checkout checkout : checkoutLanes)
            {
                checkout.newCustomer(clock,showAll);
            }
            if(clock >= timeDone)
            {
                countDown();
            }
        }
    }

    public void countDown()
    {
        if(!super1.getQueue().isEmpty())
        {
            timeDone = super1.update(clock, showAll);
        }
        else if(!ec1.getQueue().isEmpty())
        {
            timeDone = ec1.update(clock,showAll);
        }
        else if(!ec2.getQueue().isEmpty())
        {
            timeDone = ec2.update(clock,showAll);
        }
        else
        {
            for(Checkout checkout : checkoutLanes)
            {
                if(!checkout.getQueue().isEmpty())
                {
                    timeDone = checkout.update(clock,showAll);
                }
            }
        }
    }

    //creates checkout lanes and sends customers to correct lane based on item count
    public void createCheckouts(Queue<Customer> input)
    {
        System.out.println("Your store has 1 super express lane and 2 express lanes. How many standard lanes would you like to use?");
        checkoutLanes = new ArrayList<>();
        numStandLines = scanner.nextInt();
        createNumStand(numStandLines);

        while(!input.isEmpty())
        {
            Customer temp = input.peek();
            if(temp.getItems() <= numSuper)
            {
                if(superCheckout.isEmpty())
                {
                    superCheckout.add(input.poll());

                }
                else if(expressCheckout1.isEmpty())
                {
                    expressCheckout1.add(input.poll());

                }
                else if(expressCheckout2.isEmpty())
                {
                    expressCheckout2.add(input.poll());

                }
                else if(emptyStandard() > -1)
                {
                    checkoutLanes.get(emptyStandard()).addToQueue(input.poll());
                }
                else
                {
                    superCheckout.add(input.poll());

                }
            }

            else if(temp.getItems() <=numExp)
            {
                if(expressCheckout1.isEmpty())
                {
                    expressCheckout1.add(input.poll());

                }
                else if(expressCheckout2.isEmpty())
                {
                    expressCheckout2.add(input.poll());

                }
                else if(emptyStandard() > -1)
                {
                    checkoutLanes.get(emptyStandard()).addToQueue(input.poll());
                }
                else
                {
                    shortestExpress().add(input.poll());

                }
            }
            else
            {

                checkoutLanes.get(shortestStandard()).addToQueue(input.poll());
            }

        }

        numItemsInLines();
    }
    //print method to verify customers are being sent to correct line
    public void numItemsInLines()
    {
        SuperExpressCheckout super1 = new SuperExpressCheckout(superCheckout);
        System.out.println("Super1 data: ");
        for(Customer SEcustomer : superCheckout)
        {
            SEcustomer.getItems();
        }

        ExpressCheckout ec1 = new ExpressCheckout(expressCheckout1);
        System.out.println("EC1 data: ");
        for(Customer Ecustomer : expressCheckout1)
        {
            Ecustomer.getItems();
        }

        ExpressCheckout ec2 = new ExpressCheckout(expressCheckout2);
        System.out.println("EC2 data: ");
        for(Customer Ecustomer : expressCheckout2)
        {
            Ecustomer.getItems();
        }

        for(Checkout checkout : checkoutLanes)
        {
            System.out.println(checkout.getName());
            for(Customer customer : checkout.getQueue())
            {
                customer.getItems();
            }
        }
    }
    //calculates which queue is shortest between the express checkouts
    public Queue<Customer> shortestExpress()
    {
        if(expressCheckout1.size() <= expressCheckout2.size())
        {
            return expressCheckout1;
        }
        return expressCheckout2;
    }
    //calculates which queue is the shortest in the standard checkouts
    public int shortestStandard()
    {
        int shortest = 0;
        for(int i = 1; i < checkoutLanes.size(); i++)
        {
            if(checkoutLanes.get(i).getQueue().size() < checkoutLanes.get(shortest).getQueue().size())
            {
                shortest=i;
            }
        }
        return shortest;
    }
    //returns position of first empty standard checkout lane
    public int emptyStandard()
    {

        for(int i = 0; i < checkoutLanes.size(); i++)
        {
            if(checkoutLanes.get(i).getQueue().isEmpty())
            {
                return i;
            }
        }
        return -1;
    }
    //creates standard checkout lines based on user input
    public void createNumStand(int input)
    {
        for(int i = 0; i < input; i++)
        {
            Queue<Customer> standard1 = new LinkedList<>();
            Checkout checkout1 = new Checkout(standard1);
            checkout1.setName(i+1);
            checkoutLanes.add(checkout1);

        }
        for(Checkout checkout : checkoutLanes)
        {
            System.out.println(checkout.getName());
        }
        System.out.println("There are currently " +input+ " standard lanes open");
    }
    //average processing time
    public void averageTime(int i)
    {
        System.out.println("Average processing time is " +(i/this.input)+ " seconds per customer");
    }
}