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
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int TRANSFER = 3;
   private static final int EXIT = 4;

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
    	  synchronized ( this ) {
    		try {
    			screen.mergeMessage(gui, "Welcome");
    			wait(1000);
    			screen.displayMessage(gui, "Please insert your card.");
    			wait(1000);//wait for user to insert card
    		}catch(InterruptedException e) {
           		e.printStackTrace();
           }
    	  }
    	 
    	 // loop while user is not yet authenticated
         while ( !userAuthenticated )
         {
            authenticateUser(); // authenticate user
         } // end while

         performTransactions(); // user is now authenticated
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
         screen.displayMessageLine(gui, "Thank you! Goodbye!" );
      } // end while
   } // end method run

   // attempts to authenticate user against database
   private void authenticateUser()
   {
	  int accountNumber = 0;
	   synchronized ( this ) {
           try {
        	   wait(1000);//give user time to watch the message
    		   screen.displayMessage( gui, "Please enter your account number: " );
    		   gui.waitTilInput();
        	   accountNumber = Integer.parseInt( gui.getInput() ); // input account number
        	   gui.clearInput();
        	   //password input process -Lucas
        	   screen.displayMessage( gui, "Enter your PIN: " );// prompt for PIN
        	   gui.isPassword = true;
        	   gui.waitTilInput();
        	   System.out.printf("\n%s", gui.getPassword());
        	   gui.isPassword = false;
        	   gui.setInput( "" );
        	   //password input session ends here ^^^^
        	   
        	   gui.printMessage();
        	   gui.waitTilInput();
           } catch (InterruptedException e) {
           		e.printStackTrace();
           }
      }

      int pin = Integer.parseInt( gui.getPassword() ) ; // input PIN
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
         screen.displayMessageLine(
        		 gui,
             "Invalid account number or PIN. Please try again." );
      }
   } // end method authenticateUser

   // display the main menu and perform transactions
   private void performTransactions()
   {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;

      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while ( !userExited )
      {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch ( mainMenuSelection )
         {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
            case WITHDRAWAL:
            case TRANSFER:

               // initialize as new object of chosen type
               currentTransaction =
                  createTransaction( mainMenuSelection );

               currentTransaction.execute(); // execute transaction
               break;
            case EXIT: // user chose to terminate session
               screen.displayMessageLine(gui, "Exiting the system..." );
               userExited = true; // this ATM session should end
               break;
            default: // user did not enter an integer from 1-4
               screen.displayMessageLine(gui,
                  "You did not enter a valid selection. Try again." );
               break;
         } // end switch
      } // end while
   } // end method performTransactions

   // display the main menu and return an input selection
   private int displayMainMenu()
   {
      screen.mergeMessage(gui, "Main menu:\n" );
      screen.mergeMessage(gui, "1 - View my balance\n" );
      screen.mergeMessage(gui, "2 - Withdraw cash\n" );
      screen.mergeMessage(gui, "3 - Transfer cash\n" );
      screen.mergeMessage(gui, "4 - Exit\n" );
      screen.mergeMessage( gui, "Enter a choice: " );
      gui.printMessage();
      return keypad.getInput(); // return user's selection
   } // end method displayMainMenu

   // return object of specified Transaction subclass
   private Transaction createTransaction( int type )
   {
      Transaction temp = null; // temporary Transaction variable

      // determine which type of Transaction to create
      switch ( type )
      {
         case BALANCE_INQUIRY: // create new BalanceInquiry transaction
            temp = new BalanceInquiry(
               currentAccountNumber, screen, bankDatabase, gui );
            break;
         case WITHDRAWAL: // create new Withdrawal transaction
            temp = new Withdrawal( currentAccountNumber, screen,
               bankDatabase, keypad, cashDispenser, gui );
            break;
        case TRANSFER: // create new Transfer transaction
            temp = new Transfer(currentAccountNumber, screen,
               bankDatabase, keypad, gui);
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
