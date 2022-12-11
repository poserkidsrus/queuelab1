import java.util.LinkedList;
import java.util.Queue;

//@keil barracliffe
//@jeffrey ackah

public class Main
{
    public static void main(String[] args)
    {
        Supermarket kroger = new Supermarket(); //create supermarket object
        kroger.fillMarket(); //fill store with customers
        Queue<Customer> queue = kroger.getQueue(); //return queue of customers
        kroger.createCheckouts(queue); //enter this queue to the store program
        
    }
    
}
