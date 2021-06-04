package Vehicles;

import Tiles.Tile;

public class Truck extends Vehicle
{
    double weightAllowed;

    public Truck(String type, String model, int buildYear, double weightAllowed, Tile[][] map)
    {
        super(type, model, buildYear, map);
        this.weightAllowed = weightAllowed;
    }
}
