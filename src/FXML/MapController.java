package FXML;

import Tiles.*;
import Util.RailPath;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;

public class MapController
{

    private double sceneWidth = 600;
    private double sceneHeight = 600;

    private static final int numberOfTiles = 30;
    double tileWidth = (sceneWidth / numberOfTiles);
    double tileHeight = (sceneHeight / numberOfTiles);
    Tile[][] playField = new Tile[numberOfTiles][numberOfTiles];
    LinkedList<RailPath> railPaths = new LinkedList<>();

    @FXML
    public GridPane pane;
    private int spots = 30;
    @FXML
    public void initialize()
    {
        ////////car tracks///
        {
            //left bottom corner cartrack

            for (int i=20, j = 0; j < 7; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,j,i);
            }

            for (int i=21, j = 0; j < 7; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,j,i);
            }

            for (int i = 8, j = 20; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i = 7, j = 20; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

            //middle cartrack

            for (int i = 14, j = 0; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i=13, j = 0; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r, i, j);
            }
            //right bottom corner cartrack

            for (int i=21, j = 20; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

            for (int i=22, j = 20; j < 30; j++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }


            for (int i = 21, j = 21; i < 30; i++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i = 21,j = 20; i < 30; i++)
            {
                CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

        }

        RailPath AB = new RailPath("AB");
        /////////A2B/////////
        {
            for (int j = 16, i = 2; j < 30; j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);

            }
            for (int i = 3, j = 16; i < 6; i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);
            }
            for (int i = 6, j = 6; j <= 16; j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);
            }
        }
        RailPath BC = new RailPath("BC");

        /////////B2C/////////
        {
            for (int j = 6, i = 7; i < 20; i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                BC.addTile(r);
            }
            for(int i=19,j=7;j<=12;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                BC.addTile(r);
            }


        }
        RailPath CE = new RailPath("CE");

        /////////C2E/////////
        {
            for(int i=20, j=12;j<=18;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int i=20,j=18;i<27;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int i=26,j=18;j<26;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int j=25,i=27;i<30;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
        }

        RailPath CD = new RailPath("CD");
        /////////C2D/////////
        {
            for(int i =21,j=12;i<=26;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=26,j=9;j<=12;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=26,j=9;i<29;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=28,j=6;j<9;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=23,j=5;i<29;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=23,j=3;j<6;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int j=1,i=22;j<=3;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int j=1,i=22;i<26;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
        }

        ////////railwayX/////
        {
            //left crossing
            RailwayCrossing leftX1 = new RailwayCrossing("", 2, 20, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(leftX1, 2, 20);
            AB.addTile(leftX1);

            RailwayCrossing leftX2 = new RailwayCrossing("", 2, 21, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(leftX2, 2, 21);
            AB.addTile(leftX2);

            /*for (int i = 2, j = 20; j <= 21; j++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
                AB.addTile(r);
            }*/
            RailwayCrossing midX1 = new RailwayCrossing("", 13, 6, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(midX1, 13, 6);
            BC.addTile(midX1);

            RailwayCrossing midX2 = new RailwayCrossing("", 14, 6, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(midX2, 14, 6);
            BC.addTile(midX2);/*
            for (int i = 13, j = 6; i <= 14; i++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
                BC.addTile(r);
            }*/
            RailwayCrossing rightX1 = new RailwayCrossing("", 26, 20, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(rightX1, 26, 20);
            CE.addTile(rightX1);

            RailwayCrossing rightX2 = new RailwayCrossing("", 26, 21, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(rightX2, 26, 21);
            CE.addTile(rightX2);
            /*
            for (int i = 26, j = 20; j <= 21; j++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
                CE.addTile(r);
            }*/
            railPaths.add(AB); railPaths.add(BC); railPaths.add(CE); railPaths.add(CD);
        }
    }

    public void addTile(Tile r, int x, int y)
    {
        pane.getChildren().remove(playField[x][y]);
        pane.add(r, x, y);
        r.getTextProperty().addListener((observableValue, s, t1) -> updateGridPane());
        playField[x][y] = r;
    }

    public double getTileHeight()
    {
        return tileHeight;
    }

    public double getTileWidth()
    {
        return tileWidth;
    }

    public LinkedList<RailPath> getRailPaths()
    {
        return railPaths;
    }

    public Tile[][] getPlayField()
    {
        return playField;
    }

    public void updateGridPane()
    {

            for (int i = 0; i < spots; i++)
            {
                for (int j = 0; j < spots; j++)
                {
                    if (playField[i][j] != null) //updatedMatrix
                    {
                        pane.getChildren().remove(playField[i][j]);
                        pane.add(playField[i][j], i, j); //updatedMatrix
                    }

                }
            }


    }




}
