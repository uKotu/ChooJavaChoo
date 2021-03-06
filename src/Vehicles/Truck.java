package Vehicles;

import Tiles.Tile;
import Util.RailwayCrossing;

public class Truck extends Vehicle
{
    private final double weightAllowed;

    public Truck(String type, String model, int buildYear, double weightAllowed, Tile[][] map, Tile entryPoint, Tile exitPoint, double allowedSpeed, RailwayCrossing railwayCrossing)
    {
        super(type, model, buildYear, map,entryPoint,exitPoint, allowedSpeed, railwayCrossing);
        this.weightAllowed = weightAllowed;
    }

    @Override
    public String toString()
    {
        return "T" + VehicleID;
    }
}
