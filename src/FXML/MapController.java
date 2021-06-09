package FXML;

import Main.Main;
import Tiles.*;
import Util.RailPath;
import Util.RailwayCrossing;
import Util.Simulation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;
import java.util.logging.Level;

public class MapController
{

    private double sceneWidth = 600;
    private double sceneHeight = 600;

    private static final int numberOfTiles = 30;
    double tileWidth = (sceneWidth / numberOfTiles);
    double tileHeight = (sceneHeight / numberOfTiles);
    Tile[][] playField = new Tile[numberOfTiles][numberOfTiles];
    LinkedList<RailPath> railPaths = new LinkedList<>();
    LinkedList<RailwayCrossing> railwayCrossings = new LinkedList<>();
    private Simulation simulation;

    @FXML
    public Button movementHistoryWindowButton;
    @FXML
    public Button startSimButton;
    @FXML
    public GridPane pane;
    private int spots = 30;
    @FXML
    public void initialize()
    {
        ////////car tracks///
        {
            //left bottom corner cartrack

            for (int i = 0, j = 20; i <= 8; i++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);//j,i
            }

            for (int i = 0, j = 21; i < 7; i++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

            for (int i = 8, j = 20; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i = 7, j = 21; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

            //middle cartrack

            for (int i = 14, j = 0; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i=13, j = 0; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r, i, j);
            }
            //right bottom corner cartrack

            for (int i=21, j = 20; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

            for (int i=22, j = 21; j < 30; j++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i = 22, j = 21; i < 30; i++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.RIGHT);

                addTile(r,i,j);
            }

            for (int i = 21,j = 20; i < 30; i++)
            {
                CarTrackTile r = new CarTrackTile("", i, j, tileWidth, tileHeight, MovementSide.LEFT);

                addTile(r,i,j);
            }

        }

        RailPath AB = new RailPath("AB", playField);
        /////////A2B/////////
        {
            for (int j = 16, i = 2; j < 30; j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);

            }
            for (int i = 3, j = 16; i < 6; i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);
            }
            for (int i = 6, j = 6; j <= 16; j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                AB.addTile(r);
            }
        }
        RailPath BC = new RailPath("BC", playField);

        /////////B2C/////////
        {
            for (int j = 6, i = 7; i < 20; i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                BC.addTile(r);
            }
            for(int i=19,j=7;j<=12;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                BC.addTile(r);
            }


        }
        RailPath CE = new RailPath("CE", playField);

        /////////C2E/////////
        {
            for(int i=20, j=12;j<=18;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int i=20,j=18;i<27;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int i=26,j=18;j<26;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
            for(int j=25,i=27;i<30;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CE.addTile(r);
            }
        }

        RailPath CD = new RailPath("CD", playField);
        /////////C2D/////////
        {
            for(int i =21,j=12;i<=26;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=26,j=9;j<=12;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=26,j=9;i<29;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=28,j=6;j<9;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=23,j=5;i<29;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int i=23,j=3;j<6;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int j=1,i=22;j<=3;j++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
            for(int j=1,i=22;i<26;i++)
            {
                TrainTrackTile r = new TrainTrackTile("", i, j, tileWidth, tileHeight);

                addTile(r,i,j);
                CD.addTile(r);
            }
        }

        ////////railwayX/////
        {
            //left crossing
            RailwayCrossingTile leftX1 = new RailwayCrossingTile("", 2, 20, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(leftX1, 2, 20);
            AB.addTile(leftX1);

            RailwayCrossingTile leftX2 = new RailwayCrossingTile("", 2, 21, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(leftX2, 2, 21);
            AB.addTile(leftX2);

            LinkedList<Tile> leftCrossingTiles = new LinkedList<>(); leftCrossingTiles.add(leftX1); leftCrossingTiles.add(leftX2);
            RailwayCrossing leftCrossing = new RailwayCrossing(AB,leftCrossingTiles,1);


            RailwayCrossingTile midX1 = new RailwayCrossingTile("", 13, 6, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(midX1, 13, 6);
            BC.addTile(midX1);

            RailwayCrossingTile midX2 = new RailwayCrossingTile("", 14, 6, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(midX2, 14, 6);
            BC.addTile(midX2);

            LinkedList<Tile> midCrossingTiles = new LinkedList<>(); midCrossingTiles.add(midX1); midCrossingTiles.add(midX2);
            RailwayCrossing middleCrossing = new RailwayCrossing(BC,midCrossingTiles,2);


            RailwayCrossingTile rightX1 = new RailwayCrossingTile("", 26, 20, tileWidth, tileHeight,MovementSide.LEFT);
            addTile(rightX1, 26, 20);
            CE.addTile(rightX1);

            RailwayCrossingTile rightX2 = new RailwayCrossingTile("", 26, 21, tileWidth, tileHeight,MovementSide.RIGHT);
            addTile(rightX2, 26, 21);
            CE.addTile(rightX2);

            LinkedList<Tile> rightCrossingTiles = new LinkedList<>(); rightCrossingTiles.add(rightX1); rightCrossingTiles.add(rightX2);
            RailwayCrossing rightCrossing = new RailwayCrossing(CE,rightCrossingTiles,3);


            railPaths.add(AB); railPaths.add(BC); railPaths.add(CE); railPaths.add(CD);
            railwayCrossings.add(leftCrossing); railwayCrossings.add(middleCrossing); railwayCrossings.add(rightCrossing);
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

    public void setSimulation(Simulation sim) { this.simulation = sim;}
    public void startButtonClicked()
    {
        startSimButton.setDisable(true);
        simulation.start();

    }

    public void movementHistoryWindowButtonClicked()
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Movement window picker");
        fileChooser.showOpenDialog(stage);
        /*Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/MovementHistory.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Movement history");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)

        }
        catch (Exception ex)
        {
            Main.logger.log(Level.SEVERE,ex.getMessage(),ex);
        }*/
    }

    public LinkedList<RailwayCrossing> getRailwayCrossings()
    {
        return railwayCrossings;
    }
}
