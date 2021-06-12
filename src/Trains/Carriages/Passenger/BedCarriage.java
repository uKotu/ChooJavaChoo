package Trains.Carriages.Passenger;

public class BedCarriage extends PassengerCarriage
{
    private final int numberOfBeds;

    public BedCarriage(int carriageLength, int numberOfBeds)
    {
        super(carriageLength);
        this.numberOfBeds = numberOfBeds;
    }
}
