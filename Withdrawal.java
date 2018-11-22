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
   } // end Withdrawal constructor

   // perform transaction
   public void execute()
   {
      screen.displayMessage(gui, "IN_WITHDRAWL");
      boolean cashDispensed = false; // cash was not dispensed yet
      double availableBalance; // amount available for withdrawal

      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      GUI gui = getGUI();

      // loop until cash is dispensed or the user cancels
      do
      {
         // obtain a chosen withdrawal amount from the user
         amount = displayMenuOfAmounts();

         // check whether user chose a withdrawal amount or canceled
         if ( amount != CANCELED )
         {
            System.out.println(amount);
            // get available balance of account involved
            availableBalance =
               bankDatabase.getAvailableBalance( getAccountNumber() );

            // check whether the user has enough money in the account
            if ( amount <= availableBalance )
            {
               // check whether the cash dispenser has enough money
               if ( cashDispenser.isSufficientCashAvailable( amount ) )
               {
                  // update the account involved to reflect withdrawal
                  bankDatabase.debit( getAccountNumber(), amount );
                  cashDispenser.dispenseCash( amount ); // dispense cash
                  cashDispensed = true; // cash was dispensed

                  // instruct user to take cash
                  screen.displayMessageLine( gui,
                     "\nPlease take your cash now." );
               } // end if
               else // cash dispenser does not have enough cash
                  screen.displayMessageLine( gui,
                     "\nInsufficient cash available in the ATM." +
                     "\n\nPlease choose a smaller amount." );
            } // end if
            else // not enough money available in user's account
            {
               screen.displayMessageLine( gui,
                  "\nInsufficient funds in your account." +
                  "\n\nPlease choose a smaller amount." );
            } // end else
         } // end if
         else // user chose cancel menu option
         {
            screen.displayMessageLine( gui, "\nCanceling transaction..." );
            return; // return to main menu because user canceled
         } // end else
      } while ( !cashDispensed );

   } // end method execute

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts()
   {
      int userChoice = 0; // local variable to store return value

      Screen screen = getScreen(); // get screen reference
      GUI gui = getGUI();

      // array of amounts to correspond to menu numbers
      int amounts[] = {0, 100, 500, 1000};

      // loop while no valid choice has been made
      while ( userChoice == 0 )
      {
         // display the menu
         screen.displayMessageLine( gui, "\nWithdrawal Menu:" );
         screen.displayMessageLine( gui, "1 - $100" );
         screen.displayMessageLine( gui, "2 - $500" );
         screen.displayMessageLine( gui, "3 - $1000" );
         screen.displayMessageLine( gui, "You may directly specify your amounts" );
         screen.displayMessageLine( gui, "4 - Cancel transaction" );
         screen.displayMessage( gui, "\nChoose a withdrawal amount: " );

         int input = keypad.getInput();
         // int customValue = keypad.getInput();// get user input through keypad

         // determine how to proceed based on the input value
         switch ( input )
         {
            case 1:               // if the user chose a withdrawal amount
            case 2:                // (i.e., chose option 1, 2 or 3), return the
            case 3:
             userChoice = amounts[ input ];
              // save user's choice
              break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-4
              // check is the user input valid amount, then save amount , else output error
               if (input >= 100 && input % 100 == 0) {
                 userChoice = input;
               }else{
                 screen.displayMessageLine( gui,
                    "\nInvalid selection. Try again." );
               }
         } // end switch
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
