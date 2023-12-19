package ensisa.connectfour.model;

public enum GameState {
    INITIALIZED,
    STARTED,
    PAUSED,
    ENDED,
    RED_WINS,
    YELLOW_WINS,
    DRAW;

    @Override
    public String toString() {
        switch (this){
            case ENDED -> {
                return "Ended";
            }
            case STARTED -> {
                return "Started";
            }
            case INITIALIZED -> {
                return "Initialized";
            }
            case PAUSED -> {
                return "Paused";
            }
            case RED_WINS -> {
                return "Red wins";
            }
            case YELLOW_WINS -> {
                return "Yellow wins";
            }
            case DRAW -> {
                return "Draw";
            }
            default -> {
                return "Error State";
            }
        }
    }
}
