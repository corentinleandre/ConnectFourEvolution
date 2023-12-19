package ensisa.connectfour;

import ensisa.connectfour.model.Cell;
import ensisa.connectfour.model.GameState;
import ensisa.connectfour.model.Grid;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

public class MainController {
    @FXML
    private Pane gamePane;

    @FXML Pane endGameBorderPane;

    @FXML
    private Label resultLabel;

    @FXML
    private Pane columnButtonPane;

    @FXML
    private Button endGameExitButton;

    @FXML
    private Button newGameButton;

    private Grid grid;
    private int currentPlayer = Grid.PLAYER_RED;
    private RandomAI ai;
    //private MinimaxAI ai;

    public MainController(){
        grid = new Grid();
        ai = new RandomAI();
        //ai = new MinimaxAI();
    }

    //after FXML is loaded
    public void initialize(){
        grid.newGame();
        resetPane();
        configureEndGamePane();
        addGrid();
        addColumnButtons();
        addTokens();

        /*
        Cell c = grid.getGrid().get(1);
        Cell c2 = grid.getGrid().get(5);
        Cell c3 = grid.getGrid().get(7);
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (this){
                    try {
                        wait(5000);
                        c.setOwner(Cell.PLAYER_RED);
                        c2.setOwner(Cell.PLAYER_YELLOW);
                        c3.setOwner(Cell.PLAYER_YELLOW);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        */
    }

    private void configureEndGamePane() {
        endGameBorderPane.visibleProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("MainController::configureEndGamePane()::call() : isFinished ? " + (grid.isFinished() ? "true" : "false"));
                return grid.isFinished();
            }
        }, grid.stateProperty()));
        resultLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return grid.stateProperty().getValue().toString();
            }
        }, grid.stateProperty()));
        resultLabel.textFillProperty().bind(Bindings.createObjectBinding(new Callable<Paint>() {
            @Override
            public Paint call() throws Exception {
                switch (grid.stateProperty().getValue()){
                    case RED_WINS -> {
                        return Color.RED;
                    }
                    case YELLOW_WINS -> {
                        return Color.YELLOW;
                    }
                    default -> {
                        return Color.BLACK;
                    }
                }
            }
        }, grid.stateProperty()));
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                grid.newGame();
            }
        });
        endGameExitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
            }
        });
    }

    private void resetPane(){
        gamePane.getChildren().clear();
    }

    private void addGrid(){
        gamePane.getChildren().add(ConnectFourUtils.gridBackground());
    }

    private void addColumnButtons(){
        List<Node> buttonList = IntStream.range(0, 7).mapToObj(new IntFunction<Node>() {
            Grid grid = MainController.this.grid;

            @Override
            public Node apply(int value) {
                Button b = new Button();
                b.setPrefSize(Cell.PIXEL_SIZE, Cell.PIXEL_SIZE * 0.6);
                b.setText("Colonne : " + value);
                b.setLayoutX(Cell.PIXEL_SIZE* value);
                b.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        System.out.println("MainController::addButtons()::call() : isFinished ? " + (grid.isFinished() ? "true" : "false"));
                        return grid.isFinished();
                    }
                }, grid.stateProperty()));
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    private int column = value;

                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        grid.play(column);
                        if(!grid.isFinished()){
                            ai.makeMove(grid);
                        }
                    }
                });
                return b;
            }
        }).toList();
        columnButtonPane.setPrefHeight(Cell.PIXEL_SIZE*0.6);
        columnButtonPane.getChildren().clear();
        columnButtonPane.getChildren().addAll(buttonList);
    }

    public void addTokens(){
        IntStream.range(0,42).forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                Circle token = new Circle();
                token.setCenterX(value%7*Cell.PIXEL_SIZE +Cell.PIXEL_MIDDLE);
                token.setCenterY(value/7*Cell.PIXEL_SIZE + Cell.PIXEL_MIDDLE);
                token.setRadius(Cell.TOKEN_PIXEL_RADIUS);
                token.fillProperty().bind(grid.getGrid().get(value).colorProperty());
                gamePane.getChildren().add(token);
            }
        });
    }
}
