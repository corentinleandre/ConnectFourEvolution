package ensisa.connectfour;

import ensisa.connectfour.model.Cell;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectFourApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConnectFourApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Cell.PIXEL_SIZE*7, Cell.PIXEL_SIZE*6.6);
        stage.setTitle("Ultra Connect 4 Evolution");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
