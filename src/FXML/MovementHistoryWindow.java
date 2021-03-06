package FXML;

import Main.Main;
import Util.MovementHistory;
import Util.Simulation;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;

public class MovementHistoryWindow
{
    @FXML
    TextArea textArea;

    @FXML
    public void initialize()
    {
        textArea.setEditable(false);

        FileChooser filePicker = new FileChooser();
        Stage stage = new Stage();

        if(Simulation.configurationReadSuccess)
        {
            //if the configuration has been read, set the initial directory of filepicker to movement folder
            filePicker.setInitialDirectory(new File(Simulation.movementFolder));
        }
        File file = filePicker.showOpenDialog(stage);

        if (file != null)
        {
            try
            {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

                MovementHistory movementHistory;

                movementHistory = (MovementHistory)inputStream.readObject();

                inputStream.close();
                fileInputStream.close();

                textArea.setText(movementHistory.toString());

            }

            catch (Exception ex)
            {
                Main.logger.log(Level.SEVERE, ex.getMessage(),ex);
            }

        }

    }
}
