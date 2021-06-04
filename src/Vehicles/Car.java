package Vehicles;

import Tiles.Tile;

public class Car extends Vehicle
{
    int numberOfDoors;

    public Car(String type, String model, int buildYear, int numberOfDoors, Tile[][] map)
    {
        super(type, model, buildYear, map);
        this.numberOfDoors = numberOfDoors;
    }
}
