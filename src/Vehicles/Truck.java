package Vehicles;

import Tiles.Tile;

public class Truck extends Vehicle
{
    double weightAllowed;

    public Truck(String type, String model, int buildYear, double weightAllowed, Tile[][] map, Tile entryPoint, Tile exitPoint)
    {
        super(type, model, buildYear, map,entryPoint,exitPoint);
        this.weightAllowed = weightAllowed;
    }

    @Override
    public String toString()
    {
        return "T" + VehicleID;
    }
}
