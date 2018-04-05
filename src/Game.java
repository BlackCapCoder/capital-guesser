import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

class Game {
  static String pth_names    = "../names";
  static String pth_codes    = "../codes";
  static String pth_capitals = "../capitals";
  static String pth_images   = "../imgs/";

  private ArrayList<Country> countries;
  private int correctGuesses;
  private int wrongGuesses;
  private int totalGuesses;
  private GameMode gamemode;

  public enum GameMode {
    Capital,
    Country
  }

  Game (GameMode gm) {
    gamemode = gm;

    loadData();
    Collections.shuffle(countries);
    totalGuesses = countries.size();

    // for (Country c : countries) {
    //   System.out.println(c.toString());
    // }
  }

  private void loadData () {
    countries = new ArrayList<Country>();

    try {
      BufferedReader br_name = new BufferedReader(new FileReader(pth_names));
      BufferedReader br_code = new BufferedReader(new FileReader(pth_codes));
      BufferedReader br_capital = new BufferedReader(new FileReader(pth_capitals));

      String name;
      String code;
      String capital;
      // int cnt = 0;

      while ((name = br_name.readLine()) != null) {
        // if (++cnt >= 3) break;
        code    = br_code.readLine();
        capital = br_capital.readLine();

        File f = new File(pth_images + code + ".png");
        if (!f.exists()) continue;

        countries.add(new Country(name, code, capital));
      }
    } catch (IOException e) {}
  }

  public int getCorrectCount () { return correctGuesses; }
  public int getWrongCount   () { return wrongGuesses; }
  public int getGuessIndex   () { return totalGuesses-getGuessesLeft(); }
  public int getTotalGuesses () { return totalGuesses; }
  public int getGuessesLeft  () { return countries.size(); }
  public Country getCurrentQuestion () { return countries.get(0); }

  public boolean answer (String capital) {
    String guess = capital.trim().toLowerCase();
    String answ = "";

    if (gamemode == GameMode.Capital) answ = getCurrentQuestion().getCapital();
    if (gamemode == GameMode.Country) answ = getCurrentQuestion().getName();

    answ  = answ.toLowerCase();

    boolean success = guess.equals(answ);

    if (success) {
      correctGuesses++;
    } else {
      wrongGuesses++;
    }

    countries.remove(0);

    return success;
  }

  public boolean isDone () { return countries.size() == 0; }

  public GameMode getGameMode () { return gamemode; }


  public String getGameModeString () {
    switch (gamemode) {
      case Capital:
        return "What's the capital?";
      case Country:
        return "Which country is it?";
    }

    return "";
  }

  public String getHintString () {
    switch (gamemode) {
      case Capital:
        return getCurrentQuestion().getName() + "?";
      case Country:
        return getCurrentQuestion().getCapital() + "?";
    }

    return "";
  }
}
