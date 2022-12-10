import java.util.Queue;
import java.util.LinkedList;

//@keil barracliffe
//@jeffrey ackah

public class SuperExpressCheckout extends Checkout
{
    private Queue queue;

    public SuperExpressCheckout(Queue<Customer> queue)
    {
        super(queue);
    }

}
