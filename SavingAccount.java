public class SavingAccount extends Account{
  private double CurrentInterestRate = 1.12;
  SavingAccount( int theAccountNumber, int thePIN,
     double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }


  public double getInterestRate(){
    return CurrentInterestRate;
  }

  public void setInterestRate(double newInterestRate){
    CurrentInterestRate = newInterestRate;
  }

}
