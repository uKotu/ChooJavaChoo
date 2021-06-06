package Vehicles;

import Tiles.Tile;

public class Car extends Vehicle
{
    int numberOfDoors;

    public Car(String type, String model, int buildYear, int numberOfDoors, Tile[][] map, Tile entryPoint, Tile exitPoint)
    {
        super(type, model, buildYear, map, entryPoint, exitPoint);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public String toString()
    {
        return "C" + VehicleID;
    }
}
