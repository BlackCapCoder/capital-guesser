import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Game {
  static String pth_names    = "../names";
  static String pth_codes    = "../codes";
  static String pth_capitals = "../capitals";

  private ArrayList<Country> countries;

  Game () {
    // FileInputStream s = new FileInputStream ("");

    loadData();

    for (Country c : countries) {
      System.out.println(c.toString());
    }
  }

  private void loadData () {
    countries = new ArrayList<Country>();

    try (BufferedReader br_name = new BufferedReader(new FileReader(pth_names))) {
      try (BufferedReader br_code = new BufferedReader(new FileReader(pth_codes))) {
        try (BufferedReader br_capital = new BufferedReader(new FileReader(pth_capitals))) {
          String name;
          String code;
          String capital;

          while ((name = br_name.readLine()) != null) {
            code    = br_code.readLine();
            capital = br_capital.readLine();
            countries.add(new Country(name, code, capital));
          }
        } catch (IOException e) {}
      } catch (IOException e) {}
    } catch (IOException e) {}
  }

}
