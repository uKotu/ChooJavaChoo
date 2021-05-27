package Util;

import FXML.MapController;
import Main.Main;
import Tiles.StationTile;
import Tiles.Tile;
import Tiles.TrainTrack;
import Trains.Connectable;
import Trains.Train;
import Trains.TrainBuilder;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.logging.Level;

import static java.nio.file.StandardWatchEventKinds.*;

public  class Simulation
{
    private final static String trainFolder = "";
    private final static String carFolder = "";

    LinkedList<Train> trains;
    LinkedList<RailroadStation> stations;
    Tile[][] map;
    MapController mapController;






    public Simulation(MapController controller)
    {
        this.map = controller.getPlayField();
        trains = new LinkedList<>();
        stations = new LinkedList<>();
        this.mapController = controller;

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

    private LinkedList<RailroadStation> initializeRailroadStations()
    {
        StationTile a1 = new StationTile("A",2,29,30,30);
        StationTile a2 = new StationTile("A",2,28,30,30);
        StationTile a3 = new StationTile("A",3,28,30,30);
        StationTile a4 = new StationTile("A",3,29,30,30);

        mapController.addTile(a1,2,29);
        mapController.addTile(a2,2,28);
        mapController.addTile(a3,3,29);
        mapController.addTile(a4,3,28);

        LinkedList<StationTile> stationATiles = new LinkedList<>();
        stationATiles.add(a1); stationATiles.add(a2); stationATiles.add(a3); stationATiles.add(a4);
        LinkedList<TrainTrack> stationAExits = new LinkedList<>();
        stationAExits.add((TrainTrack) map[3][29]); stationAExits.add((TrainTrack) map[3][26]);

        RailroadStation stationA = new RailroadStation("A",stationATiles, stationAExits);

        StationTile b1 = new StationTile("B",6,6,30,30);
        StationTile b2 = new StationTile("B",6,7,30,30);
        StationTile b3 = new StationTile("B",7,6,30,30);
        StationTile b4 = new StationTile("B",7,7,30,30);
        mapController.addTile(b1,6,6);
        mapController.addTile(b2,6,7);
        mapController.addTile(b3,7,6);
        mapController.addTile(b4,7,7);


        LinkedList<StationTile> stationBTiles = new LinkedList<>();
        stationBTiles.add(b1); stationBTiles.add(b2); stationBTiles.add(b3); stationBTiles.add(b4);
        LinkedList<TrainTrack> stationBExits = new LinkedList<>();
        stationAExits.add((TrainTrack) map[6][7]); stationAExits.add((TrainTrack) map[9][7]);

        RailroadStation stationB = new RailroadStation("B",stationBTiles, stationBExits);

        StationTile c1 = new StationTile("C",20,13,30,30);
        StationTile c2 = new StationTile("C",20,13,30,30);
        StationTile c3 = new StationTile("C",21,14,30,30);
        StationTile c4 = new StationTile("C",21,14,30,30);
        mapController.addTile(c1,20,13);
        mapController.addTile(c2,20,13);
        mapController.addTile(c3,21,14);
        mapController.addTile(c4,21,14);

        LinkedList<StationTile> stationCTiles = new LinkedList<>();
        stationCTiles.add(c1); stationCTiles.add(c2); stationCTiles.add(c3); stationCTiles.add(c4);
        LinkedList<TrainTrack> stationCExits = new LinkedList<>();
        stationAExits.add((TrainTrack) map[3][29]); stationAExits.add((TrainTrack) map[3][26]);

        RailroadStation stationC = new RailroadStation("C",stationCTiles, stationCExits);

        StationTile d1 = new StationTile("D",27,3,30,30);
        StationTile d2 = new StationTile("D",27,2,30,30);
        StationTile d3 = new StationTile("D",28,2,30,30);
        StationTile d4 = new StationTile("D",28,3,30,30);
        mapController.addTile(d1,28,3);
        mapController.addTile(d2,28,2);
        mapController.addTile(d3,27,2);
        mapController.addTile(d4,27,3);

        StationTile e1 = new StationTile("E",26,26,30,30);
        StationTile e2 = new StationTile("E",27,26,30,30);
        StationTile e3 = new StationTile("E",26,27,30,30);
        StationTile e4 = new StationTile("E",27,27,30,30);
        mapController.addTile(e1,26,27);
        mapController.addTile(e2,26,26);
        mapController.addTile(e3,27,27);
        mapController.addTile(e4,27,26);

        LinkedList<Tile> stationTiles = new LinkedList<>();
        stationTiles.add(a1); stationTiles.add(a2); stationTiles.add(a3); stationTiles.add(a4);
        stationTiles.add(b1); stationTiles.add(b2); stationTiles.add(b3); stationTiles.add(b4);
        stationTiles.add(c1); stationTiles.add(c2); stationTiles.add(c3); stationTiles.add(c4);
        stationTiles.add(d1); stationTiles.add(d2); stationTiles.add(d3); stationTiles.add(d4);

    }
    public void start()
    {
        try
        {
            //start filewatcher
            Watcher trainWatcher = new Watcher(trainFolder, this.getClass().getDeclaredMethod("addTrain",String.class));
            trainWatcher.start();


            //create railroad stations
            stations = initializeRailroadStations();



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
