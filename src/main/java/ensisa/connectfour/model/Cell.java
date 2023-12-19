package ensisa.connectfour.model;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public class Cell {
    //Statric attributes and functions
    public static int PIXEL_SIZE = 100;
    public static int PIXEL_MIDDLE = PIXEL_SIZE/2;
    public static int TOKEN_PIXEL_RADIUS = 40;

    public static int PLAYER_RED = 1;
    public static int PLAYER_YELLOW = -1;
    public static int PLAYER_NONE = 0;

    public static Color getColorByOwner(int owner){
        switch (owner){
            case 1:
                return Color.RED;
            case -1:
                return Color.YELLOW;
            case 0:
                return Color.WHITE;
        }
        return Color.BLACK;
    }

    // Non-static attributes and functions
    private IntegerProperty owner = new SimpleIntegerProperty();
    private Property<Color> color = new SimpleObjectProperty<>();



    public Cell(){
        owner.set(PLAYER_NONE);
        color.bind(Bindings.createObjectBinding(() -> getColorByOwner(owner.get()), owner));
    }

    public Color getColorProperty() {
        return color.getValue();
    }

    public Property<Color> colorProperty() {
        return color;
    }

    public Color getOwnerColor(){
        return getColorByOwner(owner.get());
    }


    public int getOwner() {
        return owner.get();
    }

    public IntegerProperty ownerProperty() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner.set(owner);
    }

}
