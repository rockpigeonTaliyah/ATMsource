public class CurrentAccount extends Account{
  private int overDrawnLimit = 10000; // over drawn limit of an account

  // CurrentAccount constructor initializes attributes
  CurrentAccount( int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }

  // get overDrawnLimit
  public int getOverDrawnLimit(){
    return overDrawnLimit;
  }

  // set overDrawnLimit
  public void setOverDrawnLimit(int newOverDrawnLimit){
    overDrawnLimit = newOverDrawnLimit;
  }
}
