package Vehicles;

import Tiles.Tile;
import Util.RailwayCrossing;

public class Car extends Vehicle
{
    int numberOfDoors;

    public Car(String type, String model, int buildYear, int numberOfDoors, Tile[][] map, Tile entryPoint, Tile exitPoint, int allowedSpeed, RailwayCrossing railwayCrossing)
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
