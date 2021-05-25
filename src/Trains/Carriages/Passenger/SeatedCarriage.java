package Trains.Carriages.Passenger;

public class SeatedCarriage extends PassengerCarriage
{
    int numberOfSeats;

    public SeatedCarriage(int carriageLength, int numberOfSeats)
    {
        super(carriageLength);
        this.numberOfSeats = numberOfSeats;
    }

}
