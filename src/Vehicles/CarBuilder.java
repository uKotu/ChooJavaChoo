package Vehicles;

import Main.Main;
import Tiles.Tile;
import Util.RailwayCrossing;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;

public class CarBuilder implements Runnable
{
    private volatile int track1VehicleCount, track2VehicleCount, track3VehicleCount;
    private volatile int track1Speed, track2Speed, track3Speed;
    Tile track1LStartLocation, track2LStartLocation, track3LStartLocation,
            track1RStartLocation, track2RStartLocation, track3RStartLocation;

    Tile track1LEndLocation, track2LEndLocation, track3LEndLocation,
            track1REndLocation, track2REndLocation, track3REndLocation;
    Tile[][] map;
    LinkedList<RailwayCrossing> railwayCrossings;

    Random randomGenerator;
    private boolean isAlive ;

    ArrayDeque<Vehicle> track1Queue, track2Queue, track3Queue;

    private void addVehicle(int carTrackNumber)
    {
        if(carTrackNumber>3 || carTrackNumber<0)
            throw new IllegalArgumentException("CarTrackTile not defined");
        try
        {
            String carManufacturerName = "BMW";
            String carModelName = "M3";

            String truckManufacturerName = "Volvo";
            String truckModelName = "S5";

            int carOrVan = 1 + randomGenerator.nextInt(2);
            int leftOrRight = 1 + randomGenerator.nextInt(2);

            switch (carTrackNumber)
            {
                case 1 ->
                        {
                            if(carOrVan==1)
                            {
                                if (leftOrRight==1)
                                    track1Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                            1+randomGenerator.nextInt(3),map,track1LStartLocation,track1LEndLocation,track1Speed, railwayCrossings.get(0)));
                                else
                                    track1Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                            1+randomGenerator.nextInt(3),map,track1RStartLocation,track1REndLocation,track1Speed, railwayCrossings.get(0)));
                            }
                            else
                                if (carOrVan==2)
                            {
                                if (leftOrRight==1)
                                    track1Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track1LStartLocation,track1LEndLocation,track1Speed, railwayCrossings.get(0)));
                                else
                                    track1Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track1RStartLocation,track1REndLocation,track1Speed, railwayCrossings.get(0)));

                            }
                        }
                            case 2 -> {
                                if (carOrVan == 1)
                                {
                                    if (leftOrRight == 1)
                                        track2Queue.add(new Car(carManufacturerName, carModelName, 2000 + randomGenerator.nextInt(20),
                                                1 + randomGenerator.nextInt(3), map, track2LStartLocation, track2LEndLocation,track2Speed, railwayCrossings.get(1)));
                                    else track2Queue.add(new Car(carManufacturerName, carModelName, 2000 + randomGenerator.nextInt(20),
                                            1 + randomGenerator.nextInt(3), map, track2RStartLocation, track2REndLocation,track2Speed, railwayCrossings.get(1)));
                                } else if (carOrVan == 2)
                                {
                                    if (leftOrRight == 1)
                                        track2Queue.add(new Truck(truckManufacturerName, truckModelName, 2000 + randomGenerator.nextInt(20),
                                                3500 + randomGenerator.nextInt(2500), map, track2LStartLocation, track2LEndLocation,track2Speed, railwayCrossings.get(1)));
                                    else track2Queue.add(new Truck(truckManufacturerName, truckModelName, 2000 + randomGenerator.nextInt(20),
                                            3500 + randomGenerator.nextInt(2500), map, track2RStartLocation, track2REndLocation,track2Speed, railwayCrossings.get(1)));

                                }
                            }
                            case 3 ->
                            {
                                if(carOrVan==1)
                                {
                                    if (leftOrRight==1)
                                        track3Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                                1+randomGenerator.nextInt(3),map,track3LStartLocation,track3LEndLocation,track3Speed, railwayCrossings.get(2)));
                                    else
                                        track3Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                                1+randomGenerator.nextInt(3),map,track3RStartLocation,track3REndLocation,track3Speed, railwayCrossings.get(2)));
                                }
                                else
                                if (carOrVan==2)
                                {
                                    if (leftOrRight==1)
                                        track3Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                                3500+randomGenerator.nextInt(2500),map,track3LStartLocation,track3LEndLocation,track3Speed, railwayCrossings.get(2)));
                                    else
                                        track3Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                                3500+randomGenerator.nextInt(2500),map,track3RStartLocation,track3REndLocation,track3Speed, railwayCrossings.get(2)));

                                }

                        }

            }
        }
        catch (Exception ex)
        {
           Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
        }

    }

    public CarBuilder(int track1CarCount, int track2CarCount, int track3CarCount, int track1Speed, int track2Speed, int track3Speed, Tile[][] map, LinkedList<RailwayCrossing> railwayCrossings)
    {
        this.track1VehicleCount = track1CarCount;
        this.track2VehicleCount = track2CarCount;
        this.track3VehicleCount = track3CarCount;

        this.track1Speed = track1Speed;
        this.track2Speed = track2Speed;
        this.track3Speed = track3Speed;
        this.map = map;

        track1LStartLocation = map[0][21]; track1LEndLocation = map[7][29];
        track1RStartLocation = map[8][29]; track1REndLocation = map[0][20];

        track2LStartLocation = map[13][0]; track2LEndLocation = map[13][29];
        track2RStartLocation = map[14][29]; track2REndLocation = map[14][0];

        track3LStartLocation = map[29][20]; track3LEndLocation = map[21][29];
        track3RStartLocation = map[22][29]; track3REndLocation = map[29][21];

        randomGenerator = new Random();
        this.railwayCrossings = new LinkedList<>();
        track1Queue = new ArrayDeque<>();
        track2Queue = new ArrayDeque<>();
        track3Queue = new ArrayDeque<>();
        isAlive = true;

        this.railwayCrossings = railwayCrossings;

    }

    @Override
    public void run()
    {
        //initial creation of vehicles

            int numberOfCreatedVehiclesOnTrack1 = 0;
            for(int i = 0; i<track1VehicleCount;i++)
            {
                addVehicle(1);
                numberOfCreatedVehiclesOnTrack1++;
            }
            int numberOfCreatedVehiclesOnTrack2 = 0;
            for(int i = 0; i< track2VehicleCount; i++)
            {
                addVehicle(2);
                numberOfCreatedVehiclesOnTrack2++;
            }
            int numberOfCreatedVehiclesOnTrack3 = 0;
            for(int i = 0; i< track3VehicleCount; i++)
            {
                addVehicle(3);
                numberOfCreatedVehiclesOnTrack3++;
            }

        while(isAlive)
        {
            try
            {
                Thread.sleep(2000);
                if(!track1Queue.isEmpty())
                {
                    Vehicle vehicle = track1Queue.pollFirst();
                    vehicle.setAlive(true);
                    Thread vehicleThread = new Thread(vehicle, vehicle.toString());
                    vehicleThread.start();
                }
                if(!track2Queue.isEmpty())
                {
                    Vehicle vehicle = track2Queue.pollFirst();
                    vehicle.setAlive(true);
                    Thread vehicleThread = new Thread(vehicle, vehicle.toString());
                    vehicleThread.start();
                }
                if(!track3Queue.isEmpty())
                {
                    Vehicle vehicle = track3Queue.pollFirst();
                    vehicle.setAlive(true);
                    Thread vehicleThread = new Thread(vehicle, vehicle.toString());
                    vehicleThread.start();
                }

                for(int i = numberOfCreatedVehiclesOnTrack1; i<track1VehicleCount;i++)
                {
                    addVehicle(1);
                    numberOfCreatedVehiclesOnTrack1++;
                }

                for(int i = numberOfCreatedVehiclesOnTrack2; i< track2VehicleCount; i++)
                {
                    addVehicle(2);
                    numberOfCreatedVehiclesOnTrack2++;
                }

                for(int i = numberOfCreatedVehiclesOnTrack3; i< track3VehicleCount; i++)
                {
                    addVehicle(3);
                    numberOfCreatedVehiclesOnTrack3++;
                }

            }
            catch (Exception ex)
            {
               Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }

    }
    public void updateSpeed(int newTrack1Speed, int newTrack2Speed, int newTrack3Speed)
    {
        track1Speed = newTrack1Speed; track2Speed = newTrack2Speed; track3Speed = newTrack3Speed;
    }
    public void updateVehicleCount(int newTrack1VehicleCount, int newTrack2VehicleCount, int newTrack3VehicleCount)
    {
        track1VehicleCount = newTrack1VehicleCount;
        track2VehicleCount = newTrack2VehicleCount;
        track3VehicleCount = newTrack3VehicleCount;
    }
}
