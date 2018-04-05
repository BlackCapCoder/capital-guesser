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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.Node;

public class Main extends Application {
  static Game game;

  // Controls
  static GridPane panel;
  static Label label_question;
  static Scene scene;
  static ImageView img_flag;
  static Label label_country;
  static TextField tb_answer;
  static Button btn_answer;
  static Label label_score;


  public static void main (String[] args) {
    game = new Game ();
    launch(args);
  }

  public void start (Stage s) {
    panel          = new GridPane();
    label_question = new Label();
    img_flag       = new ImageView();
    label_country  = new Label();
    tb_answer      = new TextField();
    btn_answer     = new Button();
    label_score    = new Label();

    label_question.setFont (new Font("Monospace", 60));
    label_question.setText("What's the capital?");

    label_country.setFont (new Font("Monospace", 60));

    label_score.setFont (new Font("Monospace", 40));
    label_score.setText("0/0");

    tb_answer.setFont (new Font("Monospace", 30));
    btn_answer.setFont (new Font("Monospace", 30));
    btn_answer.setText("Submit");
    btn_answer.setOnAction (this::onClick);

    panel.setPadding(new Insets(40, 40, 40, 40));
    panel.add(label_question, 0, 1);
    panel.add(img_flag, 0, 2);
    panel.add(label_country, 0, 3);
    panel.add(tb_answer, 0, 4);
    panel.add(btn_answer, 1, 4);
    panel.add(label_score, 0, 5);

    Scene scene = new Scene(panel, 1200, 460);
    scene.getStylesheets().add("main.css");

    scene.setOnKeyPressed(this::onKeyPressed);

    s.setScene(scene);

    loadLevel();
    s.show();
  }

  public void onKeyPressed (KeyEvent e) {
    if (e.getCode() == KeyCode.ESCAPE)
      System.exit(0);
  }

  public void onClick (ActionEvent e) {
    Button btn = (Button) e.getSource();
    String answ = tb_answer.getText();
    game.answer(answ);

    if (game.isDone()) {
      Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
      onDone(s);
    } else {
      loadLevel();
    }
  }

  public void loadLevel () {
    Country q = game.getCurrentQuestion();

    try {
      FileInputStream inp = new FileInputStream ( "../imgs/" + q.getCode() + ".png");
      Image img = new Image (inp);
      img_flag.setImage(img);

      label_country.setText(q.getName() + "?");
      label_score.setText ( ""
                          + "Score: " + game.getCorrectCount() + "        "
                          + "Questions left: " + (game.getGuessIndex()+1) + "/" + game.getTotalGuesses()
                          );
    } catch (IOException e) {}
  }


  public void onDone (Stage stage) {
    GridPane p = new GridPane();

    p.setPadding(new Insets(40, 40, 40, 40));

    Label label_gameover = new Label();
    label_gameover.setFont(new Font("Monospace", 90));
    label_gameover.setText("Game over!");
    p.add(label_gameover, 0, 1);

    Label label_info = new Label();
    label_info.setFont(new Font("Monospace", 50));
    label_info.setText("Your final score was: " + game.getCorrectCount());
    p.add(label_info, 0, 2);

    Label label_pressesc = new Label();
    label_pressesc.setFont(new Font("Monospace", 30));
    label_pressesc.setText("Press escape to quit");
    p.add(label_pressesc, 0, 3);

    stage.getScene().setRoot(p);
  }

}

