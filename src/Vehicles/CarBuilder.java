package Vehicles;

import Main.Main;
import Tiles.Tile;

import java.util.ArrayDeque;
import java.util.Random;
import java.util.logging.Level;

public class CarBuilder implements Runnable
{
    private volatile int track1CarCount, track2CarCount,track3CarCount;
    private volatile int track1Speed, track2Speed, track3Speed;
    Tile track1LStartLocation, track2LStartLocation, track3LStartLocation,
            track1RStartLocation, track2RStartLocation, track3RStartLocation;

    Tile track1LEndLocation, track2LEndLocation, track3LEndLocation,
            track1REndLocation, track2REndLocation, track3REndLocation;
    Tile[][] map;
    Random randomGenerator;
    private boolean isAlive ;

    ArrayDeque<Vehicle> track1Queue, track2Queue, track3Queue;

    private void addVehicle(int carTrackNumber)
    {
        if(carTrackNumber>3 || carTrackNumber<0)
            throw new IllegalArgumentException("CarTrack not defined");
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
                                            1+randomGenerator.nextInt(3),map,track1LStartLocation,track1LEndLocation));
                                else
                                    track1Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                            1+randomGenerator.nextInt(3),map,track1RStartLocation,track1REndLocation));
                            }
                            else
                                if (carOrVan==2)
                            {
                                if (leftOrRight==1)
                                    track1Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track1LStartLocation,track1LEndLocation));
                                else
                                    track1Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track1RStartLocation,track1REndLocation));

                            }
                        }
                            case 2 -> {
                                if (carOrVan == 1)
                                {
                                    if (leftOrRight == 1)
                                        track2Queue.add(new Car(carManufacturerName, carModelName, 2000 + randomGenerator.nextInt(20),
                                                1 + randomGenerator.nextInt(3), map, track2LStartLocation, track2LEndLocation));
                                    else track2Queue.add(new Car(carManufacturerName, carModelName, 2000 + randomGenerator.nextInt(20),
                                            1 + randomGenerator.nextInt(3), map, track2RStartLocation, track2REndLocation));
                                } else if (carOrVan == 2)
                                {
                                    if (leftOrRight == 1)
                                        track2Queue.add(new Truck(truckManufacturerName, truckModelName, 2000 + randomGenerator.nextInt(20),
                                                3500 + randomGenerator.nextInt(2500), map, track2LStartLocation, track2LEndLocation));
                                    else track2Queue.add(new Truck(truckManufacturerName, truckModelName, 2000 + randomGenerator.nextInt(20),
                                            3500 + randomGenerator.nextInt(2500), map, track2RStartLocation, track2REndLocation));

                                }
                            }
                                        case 3 ->
                        {
                            if(carOrVan==1)
                            {
                                if (leftOrRight==1)
                                    track3Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                            1+randomGenerator.nextInt(3),map,track3LStartLocation,track3LEndLocation));
                                else
                                    track3Queue.add(new Car(carManufacturerName,carModelName,2000+randomGenerator.nextInt(20),
                                            1+randomGenerator.nextInt(3),map,track3RStartLocation,track3REndLocation));
                            }
                            else
                            if (carOrVan==2)
                            {
                                if (leftOrRight==1)
                                    track3Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track3LStartLocation,track3LEndLocation));
                                else
                                    track3Queue.add(new Truck(truckManufacturerName,truckModelName,2000+randomGenerator.nextInt(20),
                                            3500+randomGenerator.nextInt(2500),map,track3RStartLocation,track3REndLocation));

                            }

                        }

            }
        }
        catch (Exception ex)
        {
           Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
        }

    }

    public CarBuilder(int track1CarCount, int track2CarCount, int track3CarCount, int track1Speed, int track2Speed, int track3Speed, Tile[][] map)
    {
        this.track1CarCount = track1CarCount;
        this.track2CarCount = track2CarCount;
        this.track3CarCount = track3CarCount;

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
        track1Queue = new ArrayDeque<>();
        track2Queue = new ArrayDeque<>();
        track3Queue = new ArrayDeque<>();
        isAlive = true;

    }

    @Override
    public void run()
    {
        //initial creation of vehicles
        {
            for(int i = 0; i<track1CarCount;i++)
            {
                addVehicle(1);
            }
            for(int i = 0; i<track2CarCount;i++)
            {
                addVehicle(2);
            }
            for(int i = 0; i<track3CarCount;i++)
            {
                addVehicle(3);
            }
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


            }
            catch (Exception ex)
            {
               Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }

    }
}
