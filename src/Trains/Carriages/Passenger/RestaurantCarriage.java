package Trains.Carriages.Passenger;

public class RestaurantCarriage extends PassengerCarriage
{
    private String description;

    public RestaurantCarriage(int carriageLength, String description)
    {
        super(carriageLength);
        this.description = description;
    }
}
