package Vehicles;

import Main.Main;
import Tiles.Tile;

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


    CarBuilder(int track1CarCount, int track2CarCount, int track3CarCount, int track1Speed, int track2Speed, int track3Speed, Tile[][] map)
    {
        this.track1CarCount = track1CarCount;
        this.track2CarCount = track2CarCount;
        this.track3CarCount = track3CarCount;

        this.track1Speed = track1Speed;
        this.track2Speed = track2Speed;
        this.track3Speed = track3Speed;
        this.map = map;


        track1LStartLocation = map[0][21]; track1LEndLocation = map[7][30];
        track1RStartLocation = map[8][30]; track1REndLocation = map[0][20];

        track2LStartLocation = map[13][0]; track2LEndLocation = map[13][30];
        track2RStartLocation = map[14][30]; track2REndLocation = map[14][0];

        track3LStartLocation = map[30][20]; track3LEndLocation = map[22][30];
        track3RStartLocation = map[21][30]; track3REndLocation = map[30][21];



    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(2000);

            }
            catch (Exception ex)
            {
               Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
            }
        }

    }
}
