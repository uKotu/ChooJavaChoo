package Trains.Carriages.Passenger;

public class SeatedCarriage extends PassengerCarriage
{
    private int numberOfSeats;

    public SeatedCarriage(int carriageLength, int numberOfSeats)
    {
        super(carriageLength);
        this.numberOfSeats = numberOfSeats;
    }

}
