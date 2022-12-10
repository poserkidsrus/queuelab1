import java.util.TimerTask;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Write a description of class RingUp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RingUp extends TimerTask
{
    private Customer customer;
    RingUp(Customer customer)
    {
        this.customer = customer;
    }
    public void run()
    {
        for(int i = 0; i < customer.getItems(); i--)
        {
            customer.setItems(i);
            System.out.println("1 item scanned");
        }
    }
}
