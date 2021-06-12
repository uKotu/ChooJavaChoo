package Trains.Carriages;

public class FreightCarriage extends Carriage
{
    private int maximumWeightAllowed;

    public FreightCarriage(int carriageLength, int maximumWeightAllowed)
    {
        super(carriageLength);
        this.maximumWeightAllowed = maximumWeightAllowed;
    }
}
