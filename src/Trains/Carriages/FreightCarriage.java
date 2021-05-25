package Trains.Carriages;

public class FreightCarriage extends Carriage
{
    private double maximumWeightAllowed;

    public FreightCarriage(int carriageLength, double maximumWeightAllowed)
    {
        super(carriageLength);
        this.maximumWeightAllowed = maximumWeightAllowed;
    }
}
