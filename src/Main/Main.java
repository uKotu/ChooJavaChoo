package Main;

import FXML.MapController;
import Tiles.Tile;
import Util.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application
{

    private double sceneWidth = 1024;
    private double sceneHeight = 768;
    static MapController mapController;

    public static final Logger logger = Logger.getLogger("MyLogger");

    static
    {
        try
        {
            FileHandler fh = new FileHandler("error.log");
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
        } catch (IOException e)
        {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        try
        {

            FXMLLoader fxmlLoader = new FXMLLoader();
            var x = fxmlLoader.load(getClass().getResource("/FXML/Map.fxml").openStream());
            mapController = fxmlLoader.getController();

            primaryStage.setTitle("Map");
            primaryStage.setScene(new Scene((Parent) x, sceneWidth, sceneHeight));
            primaryStage.show();
            Simulation simulation = new Simulation(mapController);

            simulation.start();


        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE,ex.getMessage(),ex);

        }
    }

    public static MapController getMapController()
    {
        return mapController;
    }

    public static void main(String[] args)
    {
        launch(args);

    }

}