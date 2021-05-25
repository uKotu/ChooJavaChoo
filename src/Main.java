import FXML.MapController;
import Tiles.Tile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    private double sceneWidth = 1024;
    private double sceneHeight = 768;
    static MapController mapController;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        /*Parent root = FXMLLoader.load(getClass().getResource("/FXML/Map.fxml"));
        primaryStage.setTitle("Map");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));


         */
        primaryStage.show();


        FXMLLoader fxmlLoader = new FXMLLoader();
        var x = fxmlLoader.load(getClass().getResource("/FXML/Map.fxml").openStream());
        mapController = (MapController) fxmlLoader.getController();

        primaryStage.setTitle("Map");
        primaryStage.setScene(new Scene((Parent) x, sceneWidth, sceneHeight));
        primaryStage.show();
    }

    public static MapController getMapController()
    {
        return mapController;
    }

    public static void main(String[] args)
    {
        launch(args);
        Tile[][] map = getMapController().getPlayField();
        System.out.println("bla");
    }

}