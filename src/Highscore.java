import java.util.ArrayList;

class Score {
  private int score;
  private int time;
  private String name;

  Score (int score, int time, String name) {
    this.score = score;
    this.time  = time;
    this.name  = name;
  }

  public int    getScore () { return this.score; }
  public int    getTime  () { return this.time; }
  public String getName  () { return this.name; }
}

class Highscore {
  private ArrayList<Score> lst = new ArrayList<Score> ();

  public void load (String pth) { }
  public ArrayList<Score> getScores () { return this.lst; }
}
