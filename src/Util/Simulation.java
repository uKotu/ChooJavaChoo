package Util;

import FXML.MapController;
import Main.Main;
import Tiles.StationTile;
import Tiles.Tile;
import Tiles.TrainTrackTile;
import Trains.Connectable;
import Trains.Train;
import Trains.TrainBuilder;
import Vehicles.CarBuilder;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.logging.Level;

public class Simulation
{
    private final static String configFile = "C:\\Users\\Lenovo\\IdeaProjects\\ChooJavaChoo\\config.cfg";
    private String trainFolder = "";
    public static String movementFolder = "";
    public static boolean configurationReadSuccess = false;
    private final LinkedList<Train> trains;
    private final LinkedList<RailroadStation> stations;
    private LinkedList<RailwayCrossing> railwayCrossings;
    private final Tile[][] map;
    private final MapController mapController;
    private volatile int carCountTrack1, carCountTrack2, carCountTrack3;
    private volatile int track1SpeedLimit, track2SpeedLimit, track3SpeedLimit;

    private CarBuilder carBuilder;

    public Simulation(MapController controller)
    {
        this.map = controller.getPlayField();
        trains = new LinkedList<>();
        stations = new LinkedList<>();
        this.mapController = controller;

    }

    public void addTrain(String filename)
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
            trainRoute = TrainBuilder.routeValidator(trainRoute);

            reader.close();

            Train newTrain = new Train(trainParts,trainSpeed,trainRoute,map,stations, movementFolder,trainDefinitionLine);
            trains.add(newTrain);

            Thread newlyCreatedTrainThread = new Thread(newTrain);
            newlyCreatedTrainThread.start();

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }
    }
    private void initializeRailroadStations()
    {
        
        StationTile a1 = new StationTile("A",1,27,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile a2 = new StationTile("A",1,28,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile a3 = new StationTile("A",2,28,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile a4 = new StationTile("A",2,27,mapController.getTileHeight(), mapController.getTileWidth());

        mapController.addTile(a1,1,27);
        mapController.addTile(a2,1,28);
        mapController.addTile(a3,2,27);
        mapController.addTile(a4,2,28);

        LinkedList<StationTile> stationATiles = new LinkedList<>();
        stationATiles.add(a1); stationATiles.add(a2); stationATiles.add(a3); stationATiles.add(a4);
        LinkedList<TrainTrackTile> stationAExits = new LinkedList<>();
        stationAExits.add((TrainTrackTile) map[2][29]); stationAExits.add((TrainTrackTile) map[2][26]);

        RailroadStation stationA = new RailroadStation("A",stationATiles, stationAExits, trains);
        for(var x : mapController.getRailPaths())
        {
            if(x.stationsConnected.contains("A"))
                stationA.railPaths.add(x);
        }
        stations.add(stationA);

        StationTile b1 = new StationTile("B",8,6,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile b2 = new StationTile("B",8,5,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile b3 = new StationTile("B",7,6,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile b4 = new StationTile("B",7,5,mapController.getTileHeight(), mapController.getTileWidth());
        mapController.addTile(b1,8,6);
        mapController.addTile(b2,8,5);
        mapController.addTile(b3,7,6);
        mapController.addTile(b4,7,5);


        LinkedList<StationTile> stationBTiles = new LinkedList<>();
        stationBTiles.add(b1); stationBTiles.add(b2); stationBTiles.add(b3); stationBTiles.add(b4);
        LinkedList<TrainTrackTile> stationBExits = new LinkedList<>();
        stationBExits.add((TrainTrackTile) map[6][6]); stationBExits.add((TrainTrackTile) map[9][6]);

        RailroadStation stationB = new RailroadStation("B",stationBTiles, stationBExits, trains);
        for(var x : mapController.getRailPaths())
        {
            if(x.stationsConnected.contains("B"))
                stationB.railPaths.add(x);
        }
        stations.add(stationB);

        StationTile c1 = new StationTile("C",20,12,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile c2 = new StationTile("C",20,13,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile c3 = new StationTile("C",19,12,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile c4 = new StationTile("C",19,13,mapController.getTileHeight(), mapController.getTileWidth());
        mapController.addTile(c1,20,12);
        mapController.addTile(c2,20,13);
        mapController.addTile(c3,19,12);
        mapController.addTile(c4,19,13);

        LinkedList<StationTile> stationCTiles = new LinkedList<>();
        stationCTiles.add(c1); stationCTiles.add(c2); stationCTiles.add(c3); stationCTiles.add(c4);
        LinkedList<TrainTrackTile> stationCExits = new LinkedList<>();
        stationCExits.add((TrainTrackTile) map[19][11]); stationCExits.add((TrainTrackTile) map[21][12]); stationCExits.add((TrainTrackTile)map[20][14]);

        RailroadStation stationC = new RailroadStation("C",stationCTiles, stationCExits, trains);
        for(var x : mapController.getRailPaths())
        {
            if(x.stationsConnected.contains("C"))
                stationC.railPaths.add(x);
        }
        stations.add(stationC);

        StationTile d1 = new StationTile("D",27,1,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile d2 = new StationTile("D",27,2,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile d3 = new StationTile("D",26,2,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile d4 = new StationTile("D",26,1,mapController.getTileHeight(), mapController.getTileWidth());
        mapController.addTile(d1,26,1);
        mapController.addTile(d2,26,2);
        mapController.addTile(d3,27,2);
        mapController.addTile(d4,27,1);

        LinkedList<StationTile> stationDTiles = new LinkedList<>();
        stationDTiles.add(d1); stationDTiles.add(d2); stationDTiles.add(d3); stationDTiles.add(d4);
        LinkedList<TrainTrackTile> stationDExits = new LinkedList<>();
        stationDExits.add((TrainTrackTile) map[25][1]);

        RailroadStation stationD = new RailroadStation("D",stationDTiles, stationDExits, trains);
        for(var x : mapController.getRailPaths())
        {
            if(x.stationsConnected.contains("D"))
                stationD.railPaths.add(x);
        }
        stations.add(stationD);

        StationTile e1 = new StationTile("E",26,26,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile e2 = new StationTile("E",25,26,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile e3 = new StationTile("E",26,25,mapController.getTileHeight(), mapController.getTileWidth());
        StationTile e4 = new StationTile("E",25,25,mapController.getTileHeight(), mapController.getTileWidth());
        mapController.addTile(e1,26,25);
        mapController.addTile(e2,26,26);
        mapController.addTile(e3,25,25);
        mapController.addTile(e4,25,26);

        LinkedList<StationTile> stationETiles = new LinkedList<>();
        stationETiles.add(e1); stationETiles.add(e2); stationETiles.add(e3); stationETiles.add(e4);
        LinkedList<TrainTrackTile> stationEExits = new LinkedList<>();
        stationEExits.add((TrainTrackTile) map[27][25]); stationEExits.add((TrainTrackTile) map[26][24]);
        //[26][24]          [27][25]
        RailroadStation stationE = new RailroadStation("E",stationETiles, stationEExits, trains);
        for(var x : mapController.getRailPaths())
        {
            if(x.stationsConnected.contains("E"))
                stationE.railPaths.add(x);
        }
        stations.add(stationE);

    }

    private void initializeRailwayCrossings()
    {
        this.railwayCrossings = mapController.getRailwayCrossings();
        for(var crossing: railwayCrossings)
        {
            crossing.giveTrainInfo(trains);
        }

    }
    private boolean readConfiguration()
    {
        //configuration file:
        /*
        cartrack1Count-cartrack2Count-cartrack3Count
        track1speedlimit-track2speedlimit-track3speedlimit
        trainfolderLocation
        movementfolderLocation
         */
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader(configFile));

            String readLine = reader.readLine(); //Left/1-Middle/2-Right/3
            String[] carTrackCount = readLine.split("-");
            if(carTrackCount.length>=3)
            {
                carCountTrack1 = Integer.parseInt(carTrackCount[0]);
                carCountTrack2 = Integer.parseInt(carTrackCount[1]);
                carCountTrack3 = Integer.parseInt(carTrackCount[2]);
            }
            readLine = reader.readLine(); //speed1-speed2-speed3
            String[] speedPerTrack = readLine.split("-");
            {
                if(speedPerTrack.length>=3)
                {
                    track1SpeedLimit = Integer.parseInt(speedPerTrack[0]);
                    track2SpeedLimit = Integer.parseInt(speedPerTrack[1]);
                    track3SpeedLimit = Integer.parseInt(speedPerTrack[2]);
                }
            }
            trainFolder = reader.readLine();//trainFolder
            movementFolder = reader.readLine();

            reader.close();

            return Files.isDirectory(Path.of(trainFolder)) && Files.isDirectory(Path.of(movementFolder));
            //success while reading config file


        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
            return false;
        }

    }

    public void vehicleConfigUpdate()
    {
        int oldCarCountTrack1 = carCountTrack1; int oldCarCountTrack2 = carCountTrack2; int oldCarCountTrack3 = carCountTrack3;
        //int oldTrack1SpeedLimit = track1SpeedLimit; int oldTrack2SpeedLimit = track2SpeedLimit; int oldTrack3SpeedLimit = track3SpeedLimit;

        readConfiguration();
        if(oldCarCountTrack1>carCountTrack1)
            carCountTrack1 = oldCarCountTrack1;
        if(oldCarCountTrack2>carCountTrack2)
            carCountTrack2 = oldCarCountTrack2;
        if(oldCarCountTrack3>carCountTrack3)
            carCountTrack3 = oldCarCountTrack3;

        carBuilder.updateVehicleCount(carCountTrack1,carCountTrack2,carCountTrack3);
        carBuilder.updateSpeed(track1SpeedLimit,track2SpeedLimit,track3SpeedLimit);

    }

    public void start()
    {
        try
        {
            //read simulation configuration
            if(!readConfiguration())
                throw new InaccessibleObjectException("Reading config file failed!");
            else
                configurationReadSuccess = true;

            //create railroad stations and crossings
            initializeRailroadStations();
            initializeRailwayCrossings();

            //start FS watchers
            Watcher trainWatcher = new Watcher(trainFolder, this.getClass().getDeclaredMethod("addTrain", String.class),this);
            trainWatcher.start();

            carBuilder = new CarBuilder(
                    carCountTrack1,carCountTrack2,carCountTrack3,
                    track1SpeedLimit,track2SpeedLimit,track3SpeedLimit, map, railwayCrossings);
            Thread carBuilderThread = new Thread(carBuilder,"carBUILDER");
            carBuilderThread.start();

            Watcher configWatcher = new Watcher(new File(configFile).getParent(), this.getClass().getDeclaredMethod("vehicleConfigUpdate",null),this);
            Thread configWatcherThread = new Thread(configWatcher);
            configWatcherThread.start();

        }

        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
            if(ex instanceof InaccessibleObjectException)
                System.exit(-1);
        }
    }


}
