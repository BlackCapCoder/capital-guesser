import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

class Score {
  private int score;
  private long time;
  private String name;

  Score (int score, long time, String name) {
    this.score = score;
    this.time  = time;
    this.name  = name;
  }

  public int    getScore () { return this.score; }
  public long   getTime  () { return this.time; }
  public String getName  () { return this.name; }
}

class Highscore {
  private ArrayList<Score> lst = new ArrayList<Score> ();

  public void addScore (Score s) { lst.add(s); }

  public ArrayList<Score> getScores () {
    Collections.sort(lst, (a, b) -> {
      int sa = a.getScore();
      int sb = b.getScore();

      if (sa == sb) return (int) (a.getTime() - b.getTime());
      return sb - sa;
    } );
    return this.lst;
  }

  public void load (String pth) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(pth));

      while (true) {
        String name;
        String time_str;
        String score_str;

        if ((name      = br.readLine()) == null) break;
        if ((time_str  = br.readLine()) == null) break;
        if ((score_str = br.readLine()) == null) break;

        lst.add(new Score(Integer.parseInt(score_str) ,Integer.parseInt(time_str), name));
      }
    } catch (IOException e) {}
  }

  public void save (String pth) {
    try (PrintWriter out = new PrintWriter(pth)) {
      for (Score s : lst) {
        out.println(s.getName());
        out.println(s.getTime());
        out.println(s.getScore());
      }
    } catch (IOException e) {}
  }

}
