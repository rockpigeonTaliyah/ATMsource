public class Transfer extends Transaction{
  private Keypad keypad; // reference to keypad
  private Screen screen; // reference to keypad
  private GUI gui; // reference to keypad
  // constant corresponding to menu option to cancel

  // Transfer constructor initializes attributes
  public Transfer( int userAccountNumber, Screen atmScreen,
    BankDatabase atmBankDatabase, Keypad atmKeypad, GUI gui)
  {
    super( userAccountNumber, atmScreen, atmBankDatabase, gui );
    keypad = atmKeypad;
    this.screen = atmScreen;
    this.gui =  gui;
  } // end Transfer constructor

  public void execute(){
    double availableBalance;
    BankDatabase bankDatabase = getBankDatabase();
    Screen screen = getScreen(); // get screen reference
    GUI gui = getGUI();
    int temp_id = 0;
    double temp_amount = 0.0;
    boolean is_account = true;
    boolean is_amount = true;
    gui.keypadEnabled = true;
    // display the menu
    gui.mainMenuButtonAction("_BLANK");
    screen.displayMessage( gui, "\nTransfer Menu: \n\nPlease specify the target and amount: " );
    gui.delay(1000);

    boolean isDouble = true;
    while(isDouble){
    	try{

    		gui.clearInput();
    		screen.displayMessage( gui, "Transfer Account: " );
    	    gui.waitTilInput();
    	    temp_id = Integer.parseInt(gui.getInput());
    		isDouble = false;
    		if(!isDouble){
    			break;
    		}
    	}
    	catch(Exception e){

    		screen.displayMessage(gui, "Invalid Account Number! Re-Enter");
    		gui.delay(1000);
    	}
    }
    gui.clearInput();
    gui.mainMenuButtonAction("_BLANK");
    isDouble = true;
    while(isDouble){
    	try{

    		gui.clearInput();
    		screen.displayMessage( gui, "Transfer Amount: " );
    		gui.waitTilInput();
    		temp_amount = Double.parseDouble(gui.getInput());
    		System.out.println("temp amount: " + temp_amount);
    		isDouble = false;
    		if(!isDouble){
    			break;
    		}
    	}
    	catch(Exception e){

    		screen.displayMessage(gui, "Invalid Amount! Re-Enter");
    		gui.delay(1000);
    	}
    }
/*
try {
  if(gui.getInput() == ""){
    temp_amount = gui.getAmountInput();
    System.out.println(temp_amount);
    gui.clearInput();
  }else {
    temp_amount  =  Double.parseDouble(gui.getInput());
    gui.clearInput();
  }

} catch(Exception e) {
  screen.displayMessage(gui, "Invalid amount , please try again");
return;
}
*/



    // prevent error input
    if(bankDatabase.getTarget( temp_id ) == false ){

    	screen.displayMessage(gui, "Error: Account not exists");
	    gui.delay(1000);
	    is_account = false;
    }else if(getAccountNumber() == temp_id){

    	screen.mergeMessage(gui, "Error: Account < ");
    	screen.mergeMessage(gui, String.valueOf(temp_id));
    	screen.mergeMessage(gui, " > is repeated ");
      gui.printMessage();
    	gui.delay(1000);
    	is_account = false;
    }
    if (temp_amount == 0 || temp_amount == 0.0  ) {

    	screen.displayMessage( gui, "Error: Amount invalid. ");
      gui.delay(1000);
      is_amount = false;
    }else if(temp_amount >= bankDatabase.getAvailableBalance( getAccountNumber() )){

    	screen.displayMessage( gui, "Error: Insufficient amount. ");
      gui.delay(1000);
      is_amount = false;
    }
    // transfer

    if( is_account != false & is_amount != false ){
        bankDatabase.transfer( getAccountNumber(), temp_id, temp_amount, screen, gui );

        gui.clearInput();
        screen.displayMessage( gui, "Transaction Succeed ");
        gui.delay(1000);
        screen.displayMessage(gui, "Returning");
        gui.delay(1000);
        return;
    }else{

    	screen.displayMessage( gui, "Transaction canceled\n");
    	gui.delay(1000);

    }
    gui.clearInput();
    gui.keypadEnabled = false;
  }


}
