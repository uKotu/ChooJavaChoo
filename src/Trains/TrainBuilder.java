package Trains;

import Main.Main;
import Trains.Carriages.FreightCarriage;
import Trains.Carriages.Passenger.BedCarriage;
import Trains.Carriages.Passenger.RestaurantCarriage;
import Trains.Carriages.Passenger.SeatedCarriage;
import Trains.Carriages.Passenger.SleepCarriage;
import Trains.Carriages.SpecialCarriage;
import Trains.Locomotives.*;

import java.util.LinkedList;
import java.util.logging.Level;

public class TrainBuilder
{
    //creates a train from a definition f.e
    // L.P.4000.D.E-C.25.P.B.15
    //Locomotive.Passenger.4000hp.Drive.Electric - Carriage.25m.Passenger.Bed.15n
    /*
        L-Locomotive                        C-Carriage
            F-Freight                           F-Freight.Weight
            M-Maneuver                          S-Special
            P-Passenger                         P-Passenger
            U-Universal                             B-Bedded.NumberOfBeds
            D-Drive type                            R-Restaurant.description
                E-Electric                          S-Seated.numberOfSeats
                S-Steam                             Z-SleepCarriage
                D-Diesel
     */
    public static LinkedList<Connectable> trainBuilder(String trainStringDefinition)
    {
        LinkedList<Connectable> trainParts = new LinkedList<>();

        int numberOfPassengerLocomotives = 0, numberOfUniversalLocomotives = 0, numberOfFreightLocomotives = 0, numberOfManueverLocomotives = 0;
        int numberOfPassengerCarriages = 0, numberOfFreightCarriages = 0, numberOfSpecialCarriages = 0;

        int numberOfDieselPoweredLocomotives = 0, numberOfSteamPoweredLocomotives = 0, numberOfElectricPoweredLocomotives = 0;

        String[] trainPartsDefinition = trainStringDefinition.split("-");
        try
        {
            for (String part : trainPartsDefinition)
            {
                String[] characteristics = part.split("\\.");

                if (characteristics[0].equals("L"))
                {
                    int horsePower = Integer.parseInt(characteristics[2]);
                    Power powerType = null;
                    if (characteristics[3].equals("D"))
                    {
                        switch (characteristics[4])
                        {
                            case "E" ->
                                    {
                                        powerType = Power.ELECTRIC;
                                        numberOfElectricPoweredLocomotives++;
                                    }
                            case "S" ->
                                    {
                                        powerType = Power.STEAM;
                                        numberOfSteamPoweredLocomotives++;
                                    }
                            case "D" ->
                                    {
                                        powerType = Power.DIESEL;
                                        numberOfDieselPoweredLocomotives++;
                                    }
                            default -> throw new IllegalStateException("Unexpected value at power type generation: " + characteristics[4]);
                        }
                    }
                    switch (characteristics[1])
                    {
                        case "F" ->
                        {
                            trainParts.add(new FreightLocomotive(horsePower, powerType));
                            numberOfFreightLocomotives++;
                        }
                        case "M" ->
                        {
                            trainParts.add(new ManeuverLocomotive(horsePower, powerType));
                            numberOfManueverLocomotives++;
                        }
                        case "P" ->
                        {
                            trainParts.add(new PassengerLocomotive(horsePower, powerType));
                            numberOfPassengerLocomotives++;
                        }
                        case "U" ->
                        {
                            trainParts.add(new UniversalLocomotive(horsePower, powerType));
                            numberOfUniversalLocomotives++;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + characteristics[1]);
                    }

                } else if (characteristics[0].equals("C"))
                {
                    int carriageLength = Integer.parseInt(characteristics[1]);
                    switch (characteristics[2])
                    {
                        case "F" ->
                        {
                            int allowedWeight = Integer.parseInt(characteristics[3]);
                            trainParts.add(new FreightCarriage(carriageLength, allowedWeight));
                            numberOfFreightCarriages++;
                        }
                        case "S" ->
                        {
                            trainParts.add(new SpecialCarriage(carriageLength));
                            numberOfSpecialCarriages++;
                        }
                        case "P" ->
                        {
                            switch (characteristics[3])
                            {
                                case "B" ->
                                {
                                    int numberOfBeds = Integer.parseInt(characteristics[4]);
                                    trainParts.add(new BedCarriage(carriageLength, numberOfBeds));
                                    numberOfPassengerCarriages++;
                                }
                                case "R" ->
                                {
                                    trainParts.add(new RestaurantCarriage(carriageLength, characteristics[4]));
                                    numberOfPassengerCarriages++;
                                }
                                case "S" ->
                                {
                                    int numberOfSeats = Integer.parseInt(characteristics[4]);
                                    trainParts.add(new SeatedCarriage(carriageLength, numberOfSeats));
                                    numberOfPassengerCarriages++;
                                }
                                case "Z" ->
                                {
                                    trainParts.add(new SleepCarriage(carriageLength));
                                    numberOfPassengerCarriages++;
                                }
                                default -> throw new IllegalArgumentException("Unknown argument");
                            }
                        }
                        default -> throw new IllegalArgumentException("Unknown argument");
                    }
                }
                else
                    throw new IllegalArgumentException("Illegal argument");

            }
            if ((numberOfFreightLocomotives > 0 && numberOfPassengerLocomotives > 0)
            || (numberOfFreightLocomotives > 0 && numberOfManueverLocomotives>0)
            || (numberOfManueverLocomotives > 0 && numberOfPassengerLocomotives>0))
                throw new IllegalArgumentException("Mixing locomotives types not allowed");

            if ((numberOfFreightLocomotives > 0) && (numberOfPassengerCarriages > 0 || numberOfSpecialCarriages>0)
                || ((numberOfPassengerLocomotives > 0) && (numberOfFreightCarriages > 0 || numberOfSpecialCarriages > 0))
                || ((numberOfManueverLocomotives > 0) && (numberOfFreightCarriages > 0 || numberOfPassengerCarriages > 0)))
                throw new IllegalArgumentException("Mixing locomotives with different carriage types not allowed");

            if((numberOfElectricPoweredLocomotives>0 && numberOfDieselPoweredLocomotives>0)
            || (numberOfElectricPoweredLocomotives>0 && numberOfSteamPoweredLocomotives>0)
            || (numberOfSteamPoweredLocomotives>0 && numberOfDieselPoweredLocomotives>0))
                throw new IllegalArgumentException("Mixing engine types not allowed");

            if(numberOfSpecialCarriages>0 && (numberOfFreightLocomotives>0 || numberOfPassengerLocomotives>0))
                throw new IllegalArgumentException("Special carriage must be run by a maneuver or a universal locomotive");

            //first part of the train must be a locomotive
            if (!(trainParts.getFirst() instanceof Locomotive))
                throw new IllegalArgumentException("Locomotive must be the first part of the train");

            return trainParts;
        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;

    }

    public static double speedBuilder(String speedDefinition)
    {
        Double speed = null;
        try
        {
            speed = Double.parseDouble(speedDefinition);
            if (speed < 1 || speed > 5)
                throw new IllegalArgumentException("Illegal train speed");

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return speed;

    }

    public static String routeValidator(String routeDefinition)
    {
        char[] routeDef = routeDefinition.toCharArray();
        String definedStations = "ABCDE";
        for(char x : routeDef)
        {
            try
            {
                if (!definedStations.contains(x+""))
                    throw new IllegalArgumentException("Unknown route");
            }
            catch(Exception ex)
            {
                Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        for(int i = 0;i<routeDefinition.length()-2;i++)
        {
            if(routeDefinition.toCharArray()[i]=='A')
            {
                if(i==0)
                {
                    if(routeDefinition.toCharArray()[i+1]!='B')
                    {
                        return "";
                    }
                }
                else
                    if(routeDefinition.toCharArray()[i-1] != 'B' || routeDefinition.toCharArray()[i+1]!='B')
                        return "";
            }
            if(routeDefinition.toCharArray()[i]=='B')
            {
                if(i==0)
                {
                    if(routeDefinition.toCharArray()[i+1]!='A' && routeDefinition.toCharArray()[i+1]!='C')
                    {
                        return "";
                    }
                }
                else
                if((routeDefinition.toCharArray()[i-1] != 'A' && routeDefinition.toCharArray()[i-1]!='C')
                || (routeDefinition.toCharArray()[i+1] != 'A' && routeDefinition.toCharArray()[i+1]!='C'))
                {
                    return "";
                }
            }
            if(routeDefinition.toCharArray()[i]=='C')
            {
                if(i==0)
                {
                    if(routeDefinition.toCharArray()[i+1]!='B' && routeDefinition.toCharArray()[i+1]!='D' && routeDefinition.toCharArray()[i+1]!='E')
                    {
                        return "";
                    }
                }
                else
                if((routeDefinition.toCharArray()[i - 1] != 'B' && routeDefinition.toCharArray()[i - 1] != 'D' && routeDefinition.toCharArray()[i - 1] != 'E')
                || (routeDefinition.toCharArray()[i + 1] != 'B' && routeDefinition.toCharArray()[i + 1] != 'D' && routeDefinition.toCharArray()[i + 1] != 'E'))
                {
                    return "";
                }
            }
            if(routeDefinition.toCharArray()[i]=='E' || routeDefinition.toCharArray()[i]=='D')
            {
                if(i==0)
                {
                    if(routeDefinition.toCharArray()[i+1]!='C')
                    {
                        return "";
                    }
                }
                else
                if(routeDefinition.toCharArray()[i-1] != 'C' || routeDefinition.toCharArray()[i+i]!='C')
                    return "";
            }

        }
        return routeDefinition;
    }

}
