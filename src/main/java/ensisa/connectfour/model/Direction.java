package ensisa.connectfour.model;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Direction{
    UP(0,1),
    RIGHT(1,0),
    DOWN(0,-1),
    LEFT(-1,0),
    UPWARDS(1,1),
    DOWNWARDS(1,-1),
    REVERSE_UPWARDS(-1,-1),
    REVERSE_DOWNWARDS(-1,1);

    private int dx;
    private int dy;

    public static Direction[] allDirsSingleSide(){
        return new Direction[]{UP, RIGHT, UPWARDS, DOWNWARDS};
    }

    private Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public Direction reverse() {
        return Arrays.stream(Direction.values()).filter(new Predicate<Direction>() {
            @Override
            public boolean test(Direction direction) {
                return (direction.dx == -Direction.this.dx && direction.dy == -Direction.this.dy);
            }
        }).findAny().get();
    }

    public int dx(){
        return this.dx;
    }

    public int dy(){
        return this.dy;
    }

    @Override
    public String toString() {
        switch (this){
            case DOWNWARDS -> {
                return "Downwards";
            }
            case UPWARDS -> {
                return "Upwards";
            }
            case RIGHT -> {
                return "Right";
            }
            case UP -> {
                return "Up";
            }
            case DOWN -> {
                return "Down";
            }
            case LEFT -> {
                return "Left";
            }
            case REVERSE_UPWARDS -> {
                return "Reverse Upwards";
            }
            case REVERSE_DOWNWARDS -> {
                return "Reverse Downwards";
            }
            default -> {
                return "Error Direction";
            }
        }
    }
}
