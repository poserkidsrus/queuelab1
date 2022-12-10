import java.util.Random;

//@keil barracliffe
//@jeffrey ackah


public class Customer
{
    
    private int cartItems;
    private int maxItems;
    private int timeInCheckout;
    private static int maxCheckout = 250;
    private int arrivalTime;
    
    public Customer(int arrivalTime)
    {
        
        Random rand = new Random();
        int minItems = 1;
        maxItems = 50;
        cartItems = rand.nextInt(this.maxItems-minItems)+1;
        this.arrivalTime = arrivalTime;
        timeInCheckout = (cartItems*5);
    }

    public int getItems()
    {
        return cartItems;
    }

    public void setItems(int x)
    {
        this.cartItems = x;
    }

    public void setCheckoutTime()
    {
        this.timeInCheckout = getItems()*5;
    }

    public int getTimeInCheckout()
    {
        return timeInCheckout;
    }

    public int getMaxCheckoutTime()
    {
        return maxCheckout;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }
}
