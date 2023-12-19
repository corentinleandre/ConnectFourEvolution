package ensisa.connectfour.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Grid {

    public static int PLAYER_RED = 1;
    public static int PLAYER_YELLOW = -1;

    public static int follow(int where, Direction dir){return where + 7* dir.dy() + dir.dx();}

    private ObservableList<Cell> grid = FXCollections.observableArrayList();
    private IntegerProperty turn = new SimpleIntegerProperty();

    private int currentPlayer;

    private Property<GameState> state = new SimpleObjectProperty<>();

    public Grid(){
        currentPlayer = PLAYER_RED;
        grid.clear();
        grid.addAll(IntStream.range(0,42).mapToObj(new IntFunction<Cell>() {
            @Override
            public Cell apply(int value) {
                return new Cell();
            }
        }).toList());
        this.newGame();
    }

    //TAG - getters and setters
    public ObservableList<Cell> getGrid() {
        return grid;
    }

    public GameState getState() {
        return state.getValue();
    }

    public Property<GameState> stateProperty() {
        return state;
    }

    public int getTurn() {
        return turn.get();
    }

    public IntegerProperty turnProperty() {
        return turn;
    }

    //TAG - Other functions
    public int tryPlay(int col, int player){
        int cellN = 41 - (6 - col);
        while (cellN > -1 && grid.get(cellN).getOwner() != Cell.PLAYER_NONE ){
            cellN -= 7;
        }
        if(cellN < 0){
            return cellN;
        }else {
            grid.get(cellN).setOwner(player);
            return cellN;
        }
    }

    public void play(int col){
        int result = tryPlay(col, currentPlayer);
        if(result >= 0){
            currentPlayer = -currentPlayer;
            turn.set(turn.get()+1);

            //everything in the turn has been managed
            updateGameState(result);
        }
    }

    public void newGame(){
        grid.stream().forEach(new Consumer<Cell>() {
            @Override
            public void accept(Cell cell) {
                cell.setOwner(Cell.PLAYER_NONE);
            }
        });
        turn.set(0);
        state.setValue(GameState.INITIALIZED);
        currentPlayer = PLAYER_RED;
    }

    public void updateGameState(int where){
        System.out.println("Grid::updateGameState : Updating game state...");
        state.setValue(GameState.STARTED);
        if(checkVictory(where)){
            if(grid.get(where).getOwner() == PLAYER_RED){
                state.setValue(GameState.RED_WINS);
            } else if (grid.get(where).getOwner() == PLAYER_YELLOW) {
                state.setValue(GameState.YELLOW_WINS);
            }else {
                state.setValue(GameState.ENDED);
            }
        }
        if(turn.get() >= 42){
            state.setValue(GameState.DRAW);
        }
        System.out.println("Grid::updateGameState : turn is " + turn.get());
        System.out.println("Grid::updateGameState :  New game state is " + state.getValue());
    }

    public boolean checkVictory(int where){
        int owner = grid.get(where).getOwner();
        Direction[] allDirs = Direction.allDirsSingleSide();
        for(Direction dir : allDirs){
            int count = 1;
            boolean keepGoing = true;
            int whereF = follow(where, dir), whereR = follow(where, dir.reverse());
            System.out.println("Grid::checkVictory : Following direction " + dir);
            while(0 <= whereF && whereF < 42 && keepGoing){
                keepGoing = grid.get(whereF).getOwner() == owner;
                count += keepGoing ? 1 : 0;
                whereF = follow(whereF, dir);
            }
            System.out.println("Grid::checkVictory : count is " + count);
            keepGoing = true;
            System.out.println("Grid::checkVictory : Following direction " + dir.reverse());
            while(0 <= whereR && whereR < 42 && keepGoing){
                keepGoing = grid.get(whereR).getOwner() == owner;
                count += keepGoing ? 1 : 0;
                whereR = follow(whereR, dir.reverse());
            }
            System.out.println("Grid::checkVictory : count is " + count);
            if(count >= 4){
                return true;
            }
        }
        return false;
    }


    public boolean isFinished(){
        return state.getValue() == GameState.ENDED
                || state.getValue() == GameState.RED_WINS
                || state.getValue() == GameState.YELLOW_WINS
                || state.getValue() == GameState.DRAW;
    }
}

