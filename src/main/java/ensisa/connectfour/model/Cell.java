package ensisa.connectfour.model;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
                return Color.GREEN;
        }
        return Color.BLACK;
    }

    // Non-static attributes and functions
    private IntegerProperty owner = new SimpleIntegerProperty();
    private Property<Color> color = new SimpleObjectProperty<>();

    private Property<Animation> animation = new SimpleObjectProperty<>();



    private BooleanProperty visible = new SimpleBooleanProperty();



    public Cell(){
        owner.set(PLAYER_NONE);
        color.bind(Bindings.createObjectBinding(() -> getColorByOwner(owner.get()), owner));
        visible.setValue(false);
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

    public Animation getAnimation() {
        return animation.getValue();
    }

    public Property<Animation> animationProperty() {
        return animation;
    }

    public boolean isVisible() {
        return visible.get();
    }

    public BooleanProperty visibleProperty() {
        return visible;
    }

    public void triggerAnimation(){
        visible.setValue(true);
        animation.getValue().playFromStart();
    }
}
