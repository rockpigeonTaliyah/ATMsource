// BalanceInquiry.java
// Represents a balance inquiry ATM transaction

public class BalanceInquiry extends Transaction
{
	private Screen screen;
	private GUI gui;
   // BalanceInquiry constructor
   public BalanceInquiry( int userAccountNumber, Screen atmScreen,
      BankDatabase atmBankDatabase, GUI gui )
   {
      super( userAccountNumber, atmScreen, atmBankDatabase, gui );
   } // end BalanceInquiry constructor

   // performs the transaction
   public void execute()
   {
      // get references to bank database and screen
      BankDatabase bankDatabase = getBankDatabase();

      // get the available balance for the account involved
      double availableBalance =
         bankDatabase.getAvailableBalance( getAccountNumber() );

      // get the total balance for the account involved
      double totalBalance =
         bankDatabase.getTotalBalance( getAccountNumber() );

      //Button choices
      gui.mainMenuButtonAction("_BALANCE_CHECK");

      // display the balance information on the screen
      screen.mergeMessage( gui, "Balance Information:\n" );
      screen.mergeMessage( gui, " - Available balance: " );
      screen.displayDollarAmount( gui, availableBalance );
      screen.displayMessage( gui, "\n - Total balance:     " );
      screen.displayDollarAmount( gui, totalBalance );
   } // end method execute
} // end class BalanceInquiry



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
