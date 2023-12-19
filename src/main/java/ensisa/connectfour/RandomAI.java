package ensisa.connectfour;

import ensisa.connectfour.model.Grid;

import java.util.Random;

public class RandomAI {
    private final Random random;

    public RandomAI() {
        this.random = new Random();
    }

    public void makeMove(Grid grid) {
        int column;
        do {
            column = random.nextInt(7);
        } while (grid.canPlay(column));
        grid.play(column);
    }
}