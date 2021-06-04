package Tiles;

import Util.Coordinates;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public abstract class Tile extends StackPane

{
    protected Rectangle rectangle;
    protected Label label;

    private int rowNumber, columnNumber;
    Coordinates coordinates;

    protected Tile(String tileContent, double x, double y, double width, double height)
    {
        rowNumber = (int) y; //x
        columnNumber = (int) x; //y
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
        return !this.label.getText().equals("");
    }

    public void putContent(String temp)
    {
        this.label.setText(temp);
    }

    public String getContent() { return this.label.getText();}

    public int getxCoordinate()
    {
        return columnNumber;
    }

    public int getyCoordinate()
    {
        return rowNumber;
    }


}
