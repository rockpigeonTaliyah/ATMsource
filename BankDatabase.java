// BankDatabase.java
// Represents the bank account information database

public class BankDatabase
{
   private Account accounts[]; // array of Accounts
   // no-argument BankDatabase constructor initializes accounts
   public BankDatabase()
   {
      accounts = new Account[ 4 ]; // just 4 accounts for testing
      accounts[ 0 ] = new Account( 12345, 54321, 1000.0, 1200.0 );
      accounts[ 1 ] = new Account( 98765, 56789, 200.0, 200.0 );
      accounts[ 2 ] = new CurrentAccount(24680, 86420, 9000, 9000);
      accounts[ 3 ] = new SavingAccount(13579, 97531, 9000, 9000);
   } // end no-argument BankDatabase constructor

   // retrieve Account object containing specified account number
   private Account getAccount( int accountNumber )
   {
      // loop through accounts searching for matching account number
      for ( Account currentAccount : accounts )
      {
         // return current account if match found

         if ( currentAccount.getAccountNumber() == accountNumber )
            return currentAccount;

      } // end for

      return null; // if no matching account was found, return null
   } // end method getAccount

   // determine whether user-specified account number and PIN match
   // those of an account in the database
   public boolean authenticateUser( int userAccountNumber, int userPIN )
   {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount( userAccountNumber );
      // System.out.println(accounts);
      // if account exists, return result of Account method validatePIN
      if ( userAccount != null )
         return userAccount.validatePIN( userPIN );
      else
         return false; // account number not found, so return false
   } // end method authenticateUser

   // return available balance of Account with specified account number
   public double getAvailableBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getAvailableBalance();
   } // end method getAvailableBalance

   // return total balance of Account with specified account number
   public double getTotalBalance( int userAccountNumber )
   {
      return getAccount( userAccountNumber ).getTotalBalance();
   } // end method getTotalBalance

   // credit an amount to Account with specified account number
   public void credit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).credit( amount );
   } // end method credit

   // debit an amount from of Account with specified account number
   public void debit( int userAccountNumber, double amount )
   {
      getAccount( userAccountNumber ).debit( amount );
   } // end method debit

   // transfer an amount from one to other with specified account numbers
   public void transfer( int userAccountNumber, int targetUserAccountNumber, double amount, Screen paramScreen, GUI gui )
   {
          Screen screen = paramScreen;
          screen.displayMessage( gui, "\n Transaction Processing.... ");
          getAccount( userAccountNumber ).debit(amount);
          getAccount( targetUserAccountNumber ).credit(amount);
          screen.displayMessage( gui, "Transaction End.");
    } // end method transfer

    // getTarget
    public boolean getTarget(int targetUserAccountNumber){
        if(getAccount( targetUserAccountNumber ) == null){
          return false;
        }
        return true;
    } // end method getTarget
  } 
// end class BankDatabase



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
