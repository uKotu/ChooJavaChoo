package Trains;

import Main.Main;
import Trains.Carriages.FreightCarriage;
import Trains.Carriages.Passenger.BedCarriage;
import Trains.Carriages.Passenger.RestaurantCarriage;
import Trains.Carriages.Passenger.SeatedCarriage;
import Trains.Carriages.Passenger.SleepCarriage;
import Trains.Carriages.SpecialCarriage;
import Trains.Locomotives.FreightLocomotive;
import Trains.Locomotives.ManeuverLocomotive;
import Trains.Locomotives.PassengerLocomotive;
import Trains.Locomotives.UniversalLocomotive;

import java.util.LinkedList;
import java.util.logging.Level;

public class TrainBuilder
{

    //creates a train from a definition f.e
    // L.F.4000.D.E-C.25.P.B.15
    //Locomotive.Freight.4000hp.Drive.Electric - Carriage.25m.Passenger.Bed.15n
    public static LinkedList<Connectable> trainBuilder(String trainStringDefinition)
    {
        LinkedList<Connectable> trainParts = new LinkedList<>();

        int numberOfPassengerLocomotives = 0, numberOfUniversalLocomotives = 0, numberOfFreightLocomotives = 0;
        int numberOfPassengerCarriages = 0, numberOfFreightCarriages = 0;
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
                            case "E" -> powerType = Power.ELECTRIC;
                            case "S" -> powerType = Power.STEAM;
                            case "D" -> powerType = Power.DIESEL;
                            default -> throw new IllegalStateException("Unexpected value at power type generation: " + characteristics[4]);
                        }
                    }
                    switch (characteristics[1])
                    {
                        case "F" -> {
                            trainParts.add(new FreightLocomotive(horsePower, powerType));
                            numberOfFreightLocomotives++;
                        }
                        case "M" -> trainParts.add(new ManeuverLocomotive(horsePower, powerType));
                        case "P" -> {
                            trainParts.add(new PassengerLocomotive(horsePower, powerType));
                            numberOfPassengerLocomotives++;
                        }
                        case "U" -> {
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
                        case "F":
                        {
                            double allowedWeight = Double.parseDouble(characteristics[3]);
                            trainParts.add(new FreightCarriage(carriageLength, allowedWeight));
                            numberOfFreightCarriages++;
                            break;
                        }
                        case "S":
                        {
                            trainParts.add(new SpecialCarriage(carriageLength));
                            break;
                        }
                        case "P":
                        {
                            switch (characteristics[3])
                            {
                                case "B" -> {
                                    int numberOfBeds = Integer.parseInt(characteristics[4]);
                                    trainParts.add(new BedCarriage(carriageLength, numberOfBeds));
                                    numberOfPassengerCarriages++;
                                }
                                case "R" -> {
                                    trainParts.add(new RestaurantCarriage(carriageLength, characteristics[4]));
                                    numberOfPassengerCarriages++;
                                }
                                case "S" -> {
                                    int numberOfSeats = Integer.parseInt(characteristics[4]);
                                    trainParts.add(new SeatedCarriage(carriageLength, numberOfSeats));
                                    numberOfPassengerCarriages++;
                                }
                                case "Z" -> {
                                    trainParts.add(new SleepCarriage(carriageLength));
                                    numberOfPassengerCarriages++;
                                }
                                default -> throw new IllegalArgumentException("Unknown argument");
                            }
                            break;
                        }
                        default:
                            throw new IllegalArgumentException("Unknown argument");
                    }
                } else
                    throw new IllegalArgumentException("Illegal argument");

            }
            if (numberOfFreightLocomotives > 0 && numberOfPassengerLocomotives > 0)
                throw new IllegalArgumentException("Mixing locomotives types not allowed");
            if ((numberOfFreightLocomotives > 0 && numberOfPassengerCarriages > 0) || (numberOfFreightLocomotives > 0 && numberOfPassengerCarriages > 0))
                throw new IllegalArgumentException("Mixing locomotives types not allowed");



        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return trainParts;
    }

    public static double speedBuilder(String speedDefinition)
    {
        Double speed = null;
        try
        {
            speed = Double.parseDouble(speedDefinition);
            if (speed < 0.5 || speed > 5)
                throw new IllegalArgumentException("Illegal train speed");

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return speed;

    }

    public static String routeBuilder(String routeDefinition)
    {
        char[] routeDef = routeDefinition.toCharArray();
        for(char x : routeDef)
        {
            try
            {
                if (x != 'A' && x != 'B' && x != 'C' && x != 'D' && x != 'E')
                    throw new IllegalArgumentException("Unknown route");
            }
            catch(Exception ex)
            {
                Main.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        return routeDefinition;

    }


}
