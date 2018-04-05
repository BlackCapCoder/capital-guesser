import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class Main extends Application {
  public static void main (String[] args) {
    Game g = new Game ();
    System.exit(0);
    launch(args);
  }

  public void start (Stage s) {
    GridPane panel = new GridPane();

    panel.setPadding(new Insets(40, 40, 40, 40));

    Scene scene = new Scene(panel, 800, 500);
    scene.getStylesheets().add("main.css");

    scene.setOnKeyPressed(this::onKeyPressed);

    s.setScene(scene);
    s.show();
  }

  public void onKeyPressed (KeyEvent e) {
    if (e.getCode() == KeyCode.ESCAPE)
      System.exit(0);
  }

}


