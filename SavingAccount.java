public class SavingAccount extends Account{
  private double CurrentInterestRate = 1.12; // current interest rate of an account


  // SavingAccount constructor initializes attributes
  SavingAccount( int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance ){
    super(theAccountNumber, thePIN, theAvailableBalance, theTotalBalance);
  }

  // get CurrentInterestRate
  public double getInterestRate(){
    return CurrentInterestRate;
  }

  // set CurrentInterestRate
  public void setInterestRate(double newInterestRate){
    CurrentInterestRate = newInterestRate;
  }

}
