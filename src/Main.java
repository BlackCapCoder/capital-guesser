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
  static GridPane intro;
  static GridPane main;

  static Label label_question;
  static ImageView img_flag;
  static Label label_country;
  static TextField tb_answer;
  static Label label_score;
  static Label label_index;

  static RadioButton rb_capital;
  static RadioButton rb_country;
  static ToggleGroup lang_group;


  public static void main (String[] args) {
    launch(args);
  }

  public void start (Stage s) {
    GridPane main = mkMain ();
    intro = mkIntro ();

    Scene scene = new Scene(intro, 1200, 500);
    scene.getStylesheets().add("main.css");

    scene.setOnKeyPressed(this::onKeyPressed);

    s.setScene(scene);

    s.show();
  }

  public GridPane mkIntro () {
    int row = 0;
    GridPane p = new GridPane();

    p.setPadding(new Insets(40, 40, 40, 40));

    Label label_gameover = new Label();
    label_gameover.setFont(new Font("Monospace", 80));
    label_gameover.setText("Welcome to capital guesser!");
    p.add(label_gameover, 0, row++);

    Label label_info = new Label();
    label_info.setFont(new Font("Monospace", 45));
    label_info.setText("What gamemode would you like to play?");
    label_info.setPadding(new Insets(50, 0, 30, 0));
    p.add(label_info, 0, row++);

    ToggleGroup g = new ToggleGroup();
    rb_capital = new RadioButton ("Guess the capital");
    rb_country = new RadioButton ("Guess the country");
    rb_capital.setFont (new Font("Monospace", 40));
    rb_country.setFont (new Font("Monospace", 40));
    rb_capital.setSelected(true);
    rb_capital.setToggleGroup(g);
    rb_country.setToggleGroup(g);
    rb_country.setPadding(new Insets(10, 0, 0, 0));
    p.add(rb_capital, 0, row++);
    p.add(rb_country, 0, row++);


    Label label_langs = new Label();
    label_langs.setFont(new Font("Monospace", 45));
    label_langs.setText("Choose your language");
    label_langs.setPadding(new Insets(60, 0, 20, 0));
    p.add(label_langs, 0, row++);

    lang_group = new ToggleGroup();
    boolean first = true;
    RadioButton rb_last = null;
    for (String lang : game.getLanguageList()) {
      RadioButton rb = new RadioButton (lang);
      rb.setFont (new Font("Monospace", 40));
      rb.setSelected(first);
      rb.setToggleGroup(lang_group);
      p.add(rb, 0, row++);
      rb_last = rb;
      first = false;
    }
    if (rb_last  != null) rb_last.setPadding(new Insets(0, 0, 60, 0));

    Button btn = new Button();
    btn.setFont (new Font("Monospace", 35));
    btn.setText("Play");
    btn.setOnAction (this::onStartClicked);
    btn.setId("btn-start");
    p.add(btn, 0, row++);

    return p;
  }
  public GridPane mkMain () {
    main           = new GridPane();
    label_question = new Label();
    img_flag       = new ImageView();
    label_country  = new Label();
    tb_answer      = new TextField();
    label_score    = new Label();
    label_index    = new Label();

    label_question.setFont (new Font("Monospace", 80));
    label_country.setFont (new Font("Monospace", 50));
    label_score.setFont (new Font("Monospace", 40));
    label_index.setFont (new Font("Monospace", 40));

    tb_answer.setFont (new Font("Monospace", 30));
    tb_answer.setOnKeyPressed(this::onTbKeypress);

    Button btn_answer = new Button();
    btn_answer.setFont (new Font("Monospace", 30));
    btn_answer.setText("Submit");
    btn_answer.setOnAction (this::onSubmitClicked);

    main.setPadding(new Insets(40, 40, 40, 40));
    main.add(label_question, 0, 1);
    main.add(img_flag, 0, 2);
    main.add(label_country, 0, 3);
    main.add(tb_answer, 0, 4);
    main.add(btn_answer, 1, 4);
    main.add(label_score, 0, 5);
    main.add(label_index, 0, 6);

    return main;
  }
  public GridPane mkOutro () {
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

    return p;
  }


  // --------------


  public void onKeyPressed (KeyEvent e) {
    if (e.getCode() == KeyCode.ESCAPE)
      System.exit(0);
  }

  public void onTbKeypress (KeyEvent e) {
    if (e.getCode() != KeyCode.ENTER) return;
    Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
    submit(s);
  }
  public void onSubmitClicked (ActionEvent e) {
    Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
    submit(s);
  }

  public void onStartClicked (ActionEvent e) {
    Game.GameMode gm = Game.GameMode.Capital;

    if (rb_capital.isSelected()) gm = Game.GameMode.Capital;
    if (rb_country.isSelected()) gm = Game.GameMode.Country;

    String l = ((RadioButton) lang_group.getSelectedToggle()).getText().substring(0,2).toLowerCase();
    game = new Game (gm, l);
    loadLevel();

    Stage s = (Stage) ((Node)e.getSource()).getScene().getWindow();
    s.getScene().setRoot(main);
  }

  public void onGameDone (Stage stage) {
    GridPane outro = mkOutro ();
    stage.getScene().setRoot(outro);
  }


  // --------------


  public void loadLevel () {
    Country q = game.getCurrentQuestion();

    try {
      FileInputStream inp = new FileInputStream ( Game.pth_images + q.getCode() + ".png");
      Image img = new Image (inp);
      img_flag.setImage(img);

      String c = game.getHintString();
      c = c.substring(0,1).toUpperCase() + c.substring(1, c.length() - 2) + c.substring(c.length()-2).toLowerCase();
      label_country.setText(c);
      label_score.setText (game.getTrans(2) + ": " + game.getCorrectCount());
      label_index.setText (game.getTrans(3) + ": " + (game.getGuessIndex()+1) + "/" + game.getTotalGuesses());

      tb_answer.setText("");
      tb_answer.requestFocus();

      label_question.setText(game.getGameModeString());
    } catch (IOException e) {}
  }

  public void submit (Stage s) {
    String answ = tb_answer.getText();

    if (answ.trim().equals("")) return;
    game.answer(answ);

    if (game.isDone()) {
      onGameDone(s);
    } else {
      loadLevel();
    }
  }

}

