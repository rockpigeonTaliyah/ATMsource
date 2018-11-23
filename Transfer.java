public class Transfer extends Transaction{
  private Keypad keypad; // reference to keypad
  // constant corresponding to menu option to cancel

  // Transfer constructor initializes attributes
  public Transfer( int userAccountNumber, Screen atmScreen,
    BankDatabase atmBankDatabase, Keypad atmKeypad, GUI gui)
  {
    super( userAccountNumber, atmScreen, atmBankDatabase, gui );
    keypad = atmKeypad;
  } // end Transfer constructor

  public void execute(){
    double availableBalance;
    BankDatabase bankDatabase = getBankDatabase();
    Screen screen = getScreen(); // get screen reference
    GUI gui = getGUI();
    int temp_id = 0;
    double temp_amount = 0;
    boolean is_account = true;
    boolean is_amount = true;
    // display the menu
    screen.displayMessage( gui, "\nTransfer Menu: \n\nPlease specify the target and amount: " );
    gui.delay();
    screen.displayMessage( gui, "\nTransfer Account: " );
    gui.waitTilInput();
    temp_id = Integer.parseInt(gui.getInput());
    gui.clearInput();
    screen.displayMessage( gui, "\nTransfer Amount: " );
    temp_amount  = keypad.getDoubleInput();
    // prevent error input
    if(bankDatabase.getTarget( temp_id ) == false ){
      System.out.println("Error: Account not exists");
      is_account = false;
    }else if(getAccountNumber() == temp_id){
      System.out.printf("Error: Account %d is repeated", temp_id);
      is_account = false;
    }
    if (temp_amount == 0 || temp_amount == 0.0  ) {
      screen.displayMessage( gui, "Error: Amount invalid. ");
      is_amount = false;
    }else if(temp_amount >= bankDatabase.getAvailableBalance( getAccountNumber() )){
      screen.displayMessage( gui, "Error: Insufficient amount. ");
      is_amount = false;
    }
    // transfer
    if( is_account != false & is_amount != false ){
        bankDatabase.transfer( getAccountNumber(), temp_id, temp_amount, screen, gui );
    }else{
      screen.displayMessage( gui, "\nTransaction canceled");
    }
  }
}
