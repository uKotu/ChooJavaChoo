package Util;

import Main.Main;
import Tiles.Tile;
import Trains.Connectable;
import Trains.Train;
import Trains.TrainBuilder;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

public  class Simulation
{
    private final static String trainFolder = "";
    private final static String carFolder = "";

    LinkedList<Train> trains;
    LinkedList<RailroadStation> stations;
    Tile[][] map;






    public Simulation(Tile[][] map)
    {
        this.map=map;
        trains = new LinkedList<>();
        stations = new LinkedList<>();

    }
    private WatchKey startTrainWatcher()
    {
        WatchKey watchKey = null;

        try
        {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path pathToTrainFolder = Paths.get(trainFolder);

            watchKey = pathToTrainFolder.register(watchService,
                    ENTRY_CREATE,
                    ENTRY_MODIFY);

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
        return watchKey;

    }
    private void addTrain(String filename)
    {
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(filename));
            String trainDefinitionLine = reader.readLine();
            String trainSpeedLine = reader.readLine();
            String trainRoute = reader.readLine();

            LinkedList<Connectable> trainParts = TrainBuilder.trainBuilder(trainDefinitionLine);
            double trainSpeed = TrainBuilder.speedBuilder(trainSpeedLine);
            trainRoute = TrainBuilder.routeBuilder(trainRoute);

            reader.close();
            trains.add(new Train(trainParts,trainSpeed,trainRoute,map,stations));

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }
    public void start()
    {
        try
        {
            Watcher trainWatcher = new Watcher(trainFolder, this.getClass().getDeclaredMethod("addTrain",String.class));
            trainWatcher.start();
            //start logger
            //start filewatcher
            //create railroad stations
            //create trains/cars
            //start trains
            //start carts
        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }


}
