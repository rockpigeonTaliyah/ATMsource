public class ATM
{
   private boolean userAuthenticated; // whether user is authenticated

   private int currentAccountNumber; // current user's account number
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private GUI gui;
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final String BALANCE = "BALANCE";
   private static final String WITHDRAWAL = "WITHDRAWAL";
   private static final String TRANSFER = "TRANSFER";
   private static final String EXIT = "EXIT";

   private boolean cashDispensed = false; // whether user withdraw money

   // no-argument ATM constructor initializes instance variables
   public ATM()
   {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   } // end no-argument ATM constructor

   // start ATM
   public void run()
   {
      gui = new GUI();
      gui.run();

	  // welcome and authenticate user; perform transactions
      while ( true )
      {
    	//print the welcome message
    	screen.mergeMessage(gui, "Welcome");
    	gui.delay(1000);;
    	screen.displayMessage(gui, "Please insert your card.");
    	gui.delay(1000);//wait for user to insert card
    	gui.setInput("");
    	gui.setPassword("");

    	 // loop while user is not yet authenticated
         while ( !userAuthenticated )
         {
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         gui.functionChoice = "";
         if (Withdrawal.getCashDispensed()) {
        	 gui.delay(1000);
        	 screen.displayMessage( gui, "Cash dispenised...\nPlease take your cash.");
        	 gui.delay(1000);
         }
         gui.delay(1000);
         screen.displayMessage( gui, "Card rejecting...\nPlease take your card.");
         screen.displayMessage(gui, "Thank you! Goodbye!" );
         System.out.println("clear exit?");
         gui.functionChoice = "";
      } // end while
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser()
   {
	  int accountNumber = 0;
	  boolean isDouble = true;
	  gui.keypadEnabled = true;
	  while (isDouble) {
		  try {
			  screen.displayMessage( gui, "Please enter your account number: " );
			  gui.waitTilInput();
			  accountNumber = Integer.parseInt( gui.getInput() ); // input account number
			  isDouble = false;
			  if (!isDouble)
				  break;
		  }
		  catch (Exception e) {
			  screen.displayMessage(gui, "Invalid account number or PIN. Please try again.");
			  gui.clearInput();
			  gui.delay(2000);
			  isDouble = true;
		  }
	  }
      gui.clearInput();

      //password input process

      screen.displayMessage( gui, "Enter your PIN: " );// prompt for PIN
      gui.isPassword = true;
      gui.waitTilInput();
   	  gui.isPassword = false;
      int pin = Integer.parseInt( gui.getPassword() ) ; // input PIN

      //DONT DEL YET TO BE FIXED!!!
      /*
      int pin = 0;
      isDouble = true;
      while(isDouble) {
    	  try {
    		  gui.isPassword = true;
			  screen.displayMessage( gui, "Enter your PIN: " );
			  gui.waitTilInput();
			  pin = Integer.parseInt( gui.getPassword() ); // input pin
			  isDouble = false;
			  gui.isPassword = false;
			  if (!isDouble)
				  break;
		  }
		  catch (Exception e) {
			  screen.displayMessage(gui, "Invalid account number or PIN. Please try again.");
			  System.out.printf("Pin:%s AC:%s", gui.getInput(), gui.getPassword());
			  gui.clearInput();
			  gui.setPassword("");
			  gui.delay(2000);
			  isDouble = true;
		  }
      }
      */

      //password input session ends here ^^^^

      gui.setPassword( "" );
      gui.clearInput();
      // set userAuthenticated to boolean value returned by database
      userAuthenticated =
         bankDatabase.authenticateUser( accountNumber, pin );

      // check whether authentication succeeded
      if ( userAuthenticated )
      {
         currentAccountNumber = accountNumber; // save user's account #
      } // end if
      else {
         screen.displayMessage(
        		 gui,
             "Invalid account number or PIN. Please try again." );
         gui.setPassword( "" );
      }
      gui.keypadEnabled = false;
   }// end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions()
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      BalanceInquiry balance = new BalanceInquiry( currentAccountNumber, screen, bankDatabase , gui );
      String mainMenuSelection = "";
      boolean userExited = false; // user has not chosen to exit
      // screen.displayMessage(gui, (displayMainMenu()));
      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
        mainMenuSelection = displayMainMenu();
        // decide how to proceed based on user's menu selection
        switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case "BALANCE":
            	balance.execute();
            	screen.displayMessage(gui, "Balance Inquiry");
              System.out.println("Inquiry");
            	break;
            case "WITHDRAWAL":

            case "TRANSFER":
            gui.setMessage("");
               // initialize as new object of chosen type
               currentTransaction = createTransaction( mainMenuSelection );
               currentTransaction.execute(); // execute transaction
               break;
            case "EXIT": // user chose to terminate session
               screen.displayMessage(gui, "Exiting the system..." );
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessage(gui,
                  "You did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions

   // display the main menu and return an input selection
   private String displayMainMenu()
   {

	    gui.mainMenuButtonAction("menu");
      screen.mergeMessage(gui, "Main menu:\n" );
      screen.mergeMessage(gui, "1 - View my balance\n" );
      screen.mergeMessage(gui, "2 - Withdraw cash\n" );
      screen.mergeMessage(gui, "3 - Transfer cash\n" );
      screen.mergeMessage(gui, "4 - Exit\n" );
      screen.mergeMessage( gui, "Enter a choice: " );
      gui.printMessage();
      gui.resetChoice();

      return gui.getFunctionInput(); // return user's selection
   } // end method displayMainMenu

   // return object of specified Transaction subclass
   private Transaction createTransaction( String type )
   {

      Transaction temp = null; // temporary Transaction variable
      // screen.displayMessage(gui,"test");
      // determine which type of Transaction to create
      switch ( type )
      {
        case "BALANCE":
        	temp = new BalanceInquiry( currentAccountNumber, screen, bankDatabase , gui );
        	break;
         case "WITHDRAWAL": // create new BalanceInquiry transaction

               temp = new Withdrawal( currentAccountNumber, screen,
                  bankDatabase, keypad, cashDispenser, gui );
            break;
        case "TRANSFER": // create new Transfer transaction
            temp = new Transfer(currentAccountNumber, screen,
               bankDatabase, keypad, gui);
          case "EXIT" :gui.mainMenuButtonAction("_BLANK"); break;
          case "":
				break;
          //  break;
      } // end switch

      return temp; // return the newly created object
   } // end method createTransaction
} // end class ATM



/**************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************/


/**************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************/
