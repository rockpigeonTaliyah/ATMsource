public class Transfer extends Transaction{
  private Keypad keypad; // reference to keypad

  // Transfer constructor initializes attributes
  public Transfer( int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad)
  {
    super( userAccountNumber, atmScreen, atmBankDatabase );
    keypad = atmKeypad;
  } // end Transfer constructor

  public void execute(){
    TransferDataInput();
  }

  public void TransferDataInput()
  {
    BankDatabase bankDatabase = getBankDatabase();
    Screen screen = getScreen(); // get screen reference
    int temp_id = 0;
    double temp_amount = 0;
    boolean is_account = true;
    boolean is_amount = true;
    // display the menu
    screen.displayMessageLine( "\nTransfer Menu: \n\nPlease specify the target and amount: " );
    screen.displayMessage( "\nTransfer Account: " );
    temp_id = keypad.getInput();
    screen.displayMessage( "\nTransfer Amount: " );
    temp_amount  = keypad.getInput();
    // prevent error input
    if(bankDatabase.getTarget( temp_id ) == false ){
      System.out.println("Error: Account not exists");
      is_account = false;
    }else if(getAccountNumber() == temp_id){
      System.out.printf("Error: Account %d is repeated", temp_id);
      is_account = false;
    }
    if (temp_amount == 0 || temp_amount >= bankDatabase.getAvailableBalance( getAccountNumber() )) {
      System.out.println("Error : Amount invalid. ");
      is_amount = false;
    }
    // transfer
    if( is_account != false & is_amount != false ){
        bankDatabase.transfer( getAccountNumber(), temp_id, temp_amount, screen );
    }else{
      System.out.println("\nTransaction canceled");
    }
  }
}
