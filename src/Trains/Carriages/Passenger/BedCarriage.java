package Trains.Carriages.Passenger;

public class BedCarriage extends PassengerCarriage
{
    int numberOfBeds;

    public BedCarriage(int carriageLength, int numberOfBeds)
    {
        super(carriageLength);
        this.numberOfBeds = numberOfBeds;
    }
}
