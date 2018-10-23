public class Transfer extends Transaction{
  private int amount; // amount to withdraw
  private Keypad keypad; // reference to keypad
  private BankDatabase bankDatabase;
  private CashDispenser cashDispenser ;
  // constant corresponding to menu option to cancel
  private final static int CANCELED = 4;

  public Transfer( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad)
  {
    super( userAccountNumber, atmScreen, atmBankDatabase );
    keypad = atmKeypad;

  } // end Withdrawal constructor

  public void execute(){
    double availableBalance;
    Screen screen = getScreen();
    TransferDataInput();
  }

  public void TransferDataInput()
  {
    BankDatabase bankDatabase = getBankDatabase();
     Screen screen = getScreen(); // get screen reference
     int temp_id = 0;
     double temp_amount = 0;
        // display the menu
    screen.displayMessageLine( "\nTransfer Menu: \n\nPlease specify the target and amount: " );
    screen.displayMessage( "\nTransfer Account: " );
    temp_id = keypad.getInput();
    screen.displayMessage( "\nTransfer Amount: " );
    temp_amount  = keypad.getInput();


    bankDatabase.transfer( getAccountNumber(), temp_id, temp_amount, screen );
    // return withdrawal amount or CANCELED
  } // end method displayMenuOfAmounts
}
