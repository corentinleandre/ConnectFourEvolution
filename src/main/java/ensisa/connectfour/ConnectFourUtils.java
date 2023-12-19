package ensisa.connectfour;

import ensisa.connectfour.model.Cell;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class ConnectFourUtils {

    static Shape cellBackground() {
        Rectangle background = new Rectangle();
        background.setHeight(Cell.PIXEL_SIZE);
        background.setWidth(Cell.PIXEL_SIZE);

        Circle token = new Circle();
        token.setRadius(Cell.TOKEN_PIXEL_RADIUS);
        token.setCenterX(Cell.PIXEL_MIDDLE);
        token.setCenterY(Cell.PIXEL_MIDDLE);

        Shape cellBackground = Shape.subtract(background, token);
        cellBackground.setFill(Color.BLUE);

        return cellBackground;
    }

    static Shape gridBackground(){
        Rectangle backgroundBase = new Rectangle();
        backgroundBase.setHeight(Cell.PIXEL_SIZE*6);
        backgroundBase.setWidth(Cell.PIXEL_SIZE*7);

        final Shape[] finalBackground = {backgroundBase};

        Circle tokenShape = new Circle();
        tokenShape.setRadius(Cell.TOKEN_PIXEL_RADIUS);

        IntStream.range(0,42).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                tokenShape.setCenterX(value%7 * Cell.PIXEL_SIZE + Cell.PIXEL_MIDDLE);
                tokenShape.setCenterY(value/7 * Cell.PIXEL_SIZE + Cell.PIXEL_MIDDLE);
                finalBackground[0] = Shape.subtract(finalBackground[0], tokenShape);
            }
        });
        finalBackground[0].setFill(Color.BLUE);
        return finalBackground[0];
    }

    static Shape boundTokenShape(Cell c){
        Circle token = new Circle();
        token.setRadius(Cell.TOKEN_PIXEL_RADIUS);
        token.setCenterX(Cell.PIXEL_MIDDLE);
        token.setCenterY(Cell.PIXEL_MIDDLE);

        token.fillProperty().bind(c.colorProperty());
        return token;
    }

    static Animation tokenFallTransition(Cell c, double startX, double startY, double endX, double endY){
        Circle token = new Circle();
        token.setRadius(Cell.PIXEL_SIZE);
        token.setCenterX(Cell.PIXEL_MIDDLE);
        token.setCenterY(Cell.PIXEL_MIDDLE);

        token.fillProperty().bind(c.colorProperty());
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(token);
        tt.setDuration(Duration.seconds(2));

        tt.setFromX(startX);
        tt.setFromY(startY);
        tt.setToX(endX);
        tt.setToY(endY);

        tt.setCycleCount(1);
        tt.setAutoReverse(false);

        return tt;
    }

    static Pane cellAsPane(Cell c){
        Pane pane = new Pane();
        pane.getChildren().clear();
        pane.getChildren().addAll(cellBackground(), boundTokenShape(c));
        return pane;
    }

}
