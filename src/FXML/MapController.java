package FXML;

import Tiles.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class MapController
{

    private double sceneWidth = 600;
    private double sceneHeight = 600;

    private static final int numberOfTiles = 30;
    double tileWidth = (sceneWidth / numberOfTiles);
    double tileHeight = (sceneHeight / numberOfTiles);
    Tile[][] playField = new Tile[numberOfTiles][numberOfTiles];

    @FXML
    public GridPane pane;
    private int spots = 30;
    @FXML
    public void initialize()
    {
        ////////car tracks///
        {
            //left bottom corner cartrack
            for (int i = 20; i <= 21; i++)
            {
                for (int j = 0; j < 7; j++)
                {
                    CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight);

                    addTile(r,j,i);
                }
            }
            for (int i = 7; i <= 8; i++)
            {
                for (int j = 20; j < 30; j++)
                {
                    CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight);

                    addTile(r,i,j);
                }
            }

            //middle cartrack
            for (int i = 13; i <= 14; i++)
            {
                for (int j = 0; j < 30; j++)
                {
                    CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight);

                    addTile(r,i,j);
                }
            }

            //right bottom corner cartrack
            for (int i = 21; i <= 22; i++)
            {
                for (int j = 20; j < 30; j++)
                {
                    CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight);

                    addTile(r,i,j);
                }
            }
            for (int i = 21; i < 30; i++)
            {
                for (int j = 20; j <= 21; j++)
                {
                    CarTrack r = new CarTrack("", i, j, tileWidth, tileHeight);

                    addTile(r,i,j);
                }
            }
        }

        /////////A2B/////////
        {
            for (int j = 16, i = 2; j < 30; j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);

            }
            for (int i = 3, j = 16; i < 6; i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for (int i = 6, j = 6; j <= 16; j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
        }

        /////////B2C/////////
        {
            for (int j = 6, i = 7; i < 20; i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=19,j=7;j<=12;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }


        }

        /////////C2E/////////
        {
            for(int i=20, j=12;j<=18;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=20,j=18;i<27;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=26,j=18;j<26;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int j=26,i=27;i<30;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
        }

        /////////C2D/////////
        {
            for(int i =21,j=12;i<=26;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=26,j=9;j<=12;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=26,j=9;i<29;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=28,j=6;j<9;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=23,j=5;i<29;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int i=23,j=3;j<6;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int j=1,i=22;j<=3;j++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
            for(int j=1,i=22;i<26;i++)
            {
                TrainTrack r = new TrainTrack("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
            }
        }

        ////////railwayX/////
        {
            for (int i = 2, j = 20; j <= 21; j++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
            }
            for (int i = 13, j = 6; i <= 14; i++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
            }
            for (int i = 26, j = 20; j <= 21; j++)
            {
                RailwayCrossing r = new RailwayCrossing("", i, j, tileWidth, tileHeight);

                addTile(r, i, j);
            }
        }


    }
    public void addTile(Tile r, int x, int y)
    {
        pane.getChildren().remove(playField[x][y]);
        pane.add(r, x, y);
        r.getTextProperty().addListener((observableValue, s, t1) -> updateGridPane(playField));
        playField[x][y] = r;
    }


    public Tile[][] getPlayField()
    {
        return playField;
    }

    public void updateGridPane(Tile[][] updatedMatrix)
    {
        for(int i = 0; i<spots; i++)
        {
            for (int j = 0; j< spots;j++)
            {
                if(updatedMatrix[i][j]!=null)
                {
                    pane.getChildren().remove(playField[i][j]);
                    pane.add(updatedMatrix[i][j], i, j);
                }

            }
        }

    }



}
