class Country {
  private String Name;
  private String Code;
  private String Capital;

  Country (String Name, String Code, String Capital) {
    this.Name = Name;
    this.Code = Code;
    this.Capital = Capital;
  }

  String getName    () { return this.Name; }
  String getCode    () { return this.Code; }
  String getCapital () { return this.Capital; }

  public String toString () {
    return Code + ", " + Name + ", " + Capital;
  }

}
