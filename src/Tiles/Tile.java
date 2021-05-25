package Tiles;

import Util.Coordinates;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends StackPane

{
    protected Rectangle rectangle;
    protected Label label;
    private boolean isTaken;
    private int rowNumber, columnNumber;
    Coordinates coordinates;

    protected Tile(String tileContent, double x, double y, double width, double height)
    {
        isTaken = false;

        rowNumber = (int) x;
        columnNumber = (int) y;
        coordinates = new Coordinates((int)x,(int)y);


        // create rectangle
        rectangle = new Rectangle(width, height);
        // create label
        label = new Label(tileContent);
        getChildren().addAll(rectangle, label);

    }

    public StringProperty getTextProperty()
    {
        return label.textProperty();
    }


    public Coordinates getCoordinates()
    {
        return coordinates;
    }

    public boolean isTaken()
    {
        return isTaken;
    }

    public void putContent(String temp)
    {
        this.label.setText(temp);
    }
}
