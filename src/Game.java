import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class Game {
  static String pth_names    = "../names";
  static String pth_codes    = "../codes";
  static String pth_capitals = "../capitals";

  private ArrayList<Country> countries;
  private int correctGuesses;
  private int wrongGuesses;
  private int totalGuesses;

  Game () {
    loadData();
    Collections.shuffle(countries);
    totalGuesses = countries.size();

    // for (Country c : countries) {
    //   System.out.println(c.toString());
    // }
  }

  private void loadData () {
    countries = new ArrayList<Country>();

    try (BufferedReader br_name = new BufferedReader(new FileReader(pth_names))) {
      try (BufferedReader br_code = new BufferedReader(new FileReader(pth_codes))) {
        try (BufferedReader br_capital = new BufferedReader(new FileReader(pth_capitals))) {
          String name;
          String code;
          String capital;
          // int cnt = 0;

          while ((name = br_name.readLine()) != null) {
            // if (++cnt >= 3) break;
            code    = br_code.readLine();
            capital = br_capital.readLine();
            countries.add(new Country(name, code, capital));
          }
        } catch (IOException e) {}
      } catch (IOException e) {}
    } catch (IOException e) {}
  }

  public int getCorrectCount () { return correctGuesses; }
  public int getWrongCount   () { return wrongGuesses; }
  public int getGuessIndex   () { return totalGuesses-getGuessesLeft(); }
  public int getTotalGuesses () { return totalGuesses; }
  public int getGuessesLeft  () { return countries.size(); }
  public Country getCurrentQuestion () { return countries.get(0); }

  public boolean answer (String capital) {
    String guess = capital.toLowerCase();
    String answ  = getCurrentQuestion().getCapital().toLowerCase();

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
}
