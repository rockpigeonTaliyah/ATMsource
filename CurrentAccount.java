public class CurrentAccount extends Account{
  static int overDrawnLimit = 10000;
  CurrentAccount( int theAccountNumber, int thePIN,
     double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }
}
