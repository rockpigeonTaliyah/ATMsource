public class CurrentAccount extends Account{
  private int overDrawnLimit = 10000;
  CurrentAccount( int theAccountNumber, int thePIN,
     double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }

  public int getOverDrawnLimit(){
    return overDrawnLimit;
  }

  public void setOverDrawnLimit(int newOverDrawnLimit){
    overDrawnLimit = newOverDrawnLimit;
  }


}
