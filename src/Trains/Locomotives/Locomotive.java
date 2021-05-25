package Trains.Locomotives;

import Trains.Connectable;
import Trains.Power;

public abstract class Locomotive implements Connectable
{
    static int locomotiveCount;
    int locomotiveID;
    int power;
    Power powerType;

    Locomotive(int power, Power powerType)
    {
        this.locomotiveID=++locomotiveCount;
        this.power = power;


    }

}
