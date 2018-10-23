public class SavingAccount extends Account{
  static double interestRate;
  SavingAccount( int theAccountNumber, int thePIN,
     double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }
}
