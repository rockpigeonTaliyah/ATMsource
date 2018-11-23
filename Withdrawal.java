// Withdrawal.java
// Represents a withdrawal ATM transaction
public class Withdrawal extends Transaction
{
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private Screen screen; // ATM's screen
   private CashDispenser cashDispenser; // reference to cash dispenser
   private GUI gui;

   // constant corresponding to menu option to cancel
   private final static int CANCELED = 4;

   // Withdrawal constructor
   public Withdrawal( int userAccountNumber, Screen atmScreen,
      BankDatabase atmBankDatabase, Keypad atmKeypad,
      CashDispenser atmCashDispenser, GUI gui )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase, gui );

      // initialize references to keypad and cash dispenser
      keypad = atmKeypad;
      cashDispenser = atmCashDispenser;
      screen = atmScreen;
      this.gui = gui;
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
     // screen.displayMessage(gui, "executed");
      // screen.displayMessage(gui, " asdf");
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      // Screen screen = getScreen();
      // GUI gui = getGUI();

      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user
         amount = displayMenuOfAmounts();
         cashDispensed = false;
//

         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            // get available balance of account involved
            availableBalance =
               bankDatabase.getAvailableBalance( getAccountNumber() );

            // check whether the user has enough money in the account
            if ( amount <= availableBalance )
            {
              System.out.print("Test A");
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                 System.out.print("Test B");
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( getAccountNumber(), amount );
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed
                  System.out.print("Test B1");
                  // instruct user to take cash
                  screen.displayMessage( gui,
                     "\nPlease take your cash now." );
                     gui.delay(1000);
                     System.out.print("Test B2");

               } // end if
               else // cash dispenser does not have enough cash
               {
                  System.out.println("B Exception");
                  screen.displayMessage( gui,
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
                     gui.delay(1000);
                   }
            } // end if
            else // not enough money available in user's account
            {
                System.out.print("Test D");
               screen.displayMessage( gui,
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
                  gui.delay(1000);
                  return;

            } // end else
         } // end if
         else // user chose cancel menu option
         {
            System.out.print("Test E Exit");

            screen.displayMessage( gui, "\nCanceling transaction..." );
            gui.delay(1000);
            return; // return to main menu because user canceled
         } // end else


      } while ( !cashDispensed );
      screen.displayMessage( gui, "\ntransaction Done, returning..." );
      gui.delay(1000);
      cashDispensed = false;
      return;
   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      gui.mainMenuButtonAction("_WITHDRWAL");
      int userChoice = 0; // local variable to store return value

      // Screen screen = getScreen(); // get screen reference
      // GUI gui = getGUI();

      // array of amounts to correspond to menu numbers
      // int amounts[] = {0, 100, 500, 1000};
      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.mergeMessage( gui, "\n Withdrawal Menu:\n\n" );
         screen.mergeMessage( gui, " -- $100\n\n" );
         screen.mergeMessage( gui, " -- $500\n\n" );
         screen.mergeMessage( gui, " -- $1000\n\n" );
         screen.mergeMessage( gui, " -- You may directly specify your amounts\n\n" );
         screen.mergeMessage( gui, " -- Cancel transaction\n\n" );
         screen.mergeMessage( gui, "\nChoose a withdrawal amount: " );
         gui.printMessage();
         int input = 0;
         gui.waitTilInput();
         System.out.println("123");
         if (gui.getInput() == "") {
           input = gui.getAmountInput();
           System.out.print(input);
         }else{
           input = Integer.parseInt(gui.getInput());
           gui.clearInput();
         }

         // int customValue = keypad.getInput();// get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 100:            // if the user chose a withdrawal amount
            case 500:                  // (i.e., chose option 1, 2 or 3), return the
            case 1000:
            screen.displayMessage(gui,"Please press enter to confirm ,\n or press exit to restart process");
            gui.waitTilInput();
             userChoice = input;
              // save user's choice
              break;
            case 4: // the user chose to cancel
               userChoice = 4; // save user's choice

               break;
            default: // the user did not enter a value from 1-4
              // check is the user input valid amount, then save amount , else output error
               // if (input >= 100 && input % 100 == 0) {
                 // gui.waitTilInput();
                 // userChoice = Integer.parseInt(gui.getInput());
                 // System.out.println(userChoice);
               // }else{
                 screen.displayMessage( gui,
                    "\nInvalid selection. Try again." );
                    break;
               // }

         } // end switch
         // screen.displayMessage(gui, Integer.toString(input));
      } // end while

      return userChoice; // return withdrawal amount or CANCELED
   } // end method displayMenuOfAmounts
} // end class Withdrawal



/**************************************************************************
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
 *************************************************************************/
