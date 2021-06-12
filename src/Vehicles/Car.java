package Vehicles;

import Tiles.Tile;
import Util.RailwayCrossing;

public class Car extends Vehicle
{
    private final int numberOfDoors;

    public Car(String type, String model, int buildYear, int numberOfDoors, Tile[][] map, Tile entryPoint, Tile exitPoint, double allowedSpeed, RailwayCrossing railwayCrossing)
    {
        super(type, model, buildYear, map, entryPoint, exitPoint, allowedSpeed, railwayCrossing);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString()
    {
        return "A" + VehicleID;
    }
}
