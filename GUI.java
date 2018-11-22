import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class GUI {
	private boolean userAuthenticated; // whether user is authenticated
	private int currentAccountNumber; // current user's account number
	private Screen screen; // ATM's screen
	private Keypad keypad; // ATM's keypad
	private GUI gui;
	private CashDispenser cashDispenser; // ATM's cash dispenser
	private BankDatabase bankDatabase; // account information database

	private JFrame frame;
	private JPanel leftJPanel;
	private JPanel rightJPanel;
	private JPanel keyPadJPanel;//Main keypad
	private JPanel textJPanel;
	private JButton keys[];
	private JTextArea messageArea;//message shown by ATM
	private JTextArea textArea; //text area to display output
	private String message = "";//for message
	private String input = "";//store user input
	private String password = "";//password
	public boolean isPassword = false;
	public boolean inputEntered;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//GUI window = new GUI();
					//window.frame.setVisible(true);
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		//initialize();
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public void printMessage() {
		messageArea.setText(message);
	}

	public void setInput( String input ) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}

	public void printInput() {
		textArea.setText(input);
	}
	
	//clear the inputArea and reset state
	public void clearInput() {
		inputEntered = false;
		setInput( "" );
		printInput();	
	}
	
	public String getPassword() {
		return password;
	}
	
	//stop the system for user to press enter
	public void waitTilInput() {
		synchronized ( this ) {
	           try {
	        	   while ( !inputEntered ) {
	        		   Thread.sleep(200);;
	        	   }
	           } catch (InterruptedException e) {
	        	   e.printStackTrace();
	           }
		}
	}


	/**
	 * Initialize the coents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(600, 120, 350, 400);//set frame position and size
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	    messageArea = new JTextArea(3, 20);// declaration of messageArea for displaying message
	    messageArea.setEditable(false);    // set messageArea not editable
	    messageArea.setText(message);// display message in messageArea
	    messageArea.setLineWrap(true);// set auto line wrap
	    messageArea.setWrapStyleWord(true);//line wrap without cut words

		textArea = new JTextArea( 3, 20 );  // declaration of textArea for displaying input
	    textArea.setEditable(false);    // set textArea not editable
	    textArea.setText(input);  // display input in textArea

	    textJPanel = new JPanel();
	    textJPanel.setLayout( new BorderLayout() );
	    
	    textJPanel.add( messageArea, BorderLayout.CENTER );
	    textJPanel.add( textArea, BorderLayout.SOUTH);

	    //left 4 + right 4 + num 1-9 + 4 = 24
	    keys = new JButton[ 24 ];

	    // initialize keypad buttons
	    for ( int i = 0; i <= 9; i++ ) {
	    	keys[ i ] = new JButton( String.valueOf( i ) );
	    	keys[ i ].addActionListener( new buttonListenerConcat() );
	    }

	    keys[ 10 ] = new JButton( "Cancel" );
	    keys[ 11 ] = new JButton( "Clear" );
	    keys[ 12 ] = new JButton( "Enter" );
	    keys[ 13 ] = new JButton( "." );
	    keys[ 14 ] = new JButton( "00" );
	    keys[ 15 ] = new JButton( " " );

	    // add action to buttons
			keys[ 10 ].addActionListener( new buttonListenerConcat());
	    keys[ 11 ].addActionListener( new buttonListenerConcat());
	    keys[ 12 ].addActionListener( new ActionListener() {
	    	@Override
	    	public void actionPerformed( ActionEvent e ) {
	    		inputEntered = true;
	    	}
	    });
	    keys[ 13 ].addActionListener( new buttonListenerConcat());
	    keys[ 14 ].addActionListener( new buttonListenerConcat());

			//left top for 4
			//right top for 4
			String [] ATMActionCommand = {" ","Withdrawl","Deposit"," ","TRANSFER"," "," ","EXIT"};
	    for ( int i = 0; i <= 7; i++){
	    	keys[ 16 + i ] = new JButton(ATMActionCommand[i]);
				keys[ 16 + i ].addActionListener( new buttonListenerConcat());
			}
	    // set leftJPanel layout to grid layout
	    leftJPanel = new JPanel();
	    leftJPanel.setLayout( new GridLayout( 4, 1 ) );
	    //leftJPanel.setPreferredSize( new Dimension( 70, 100 ));


	    // add buttons to leftJPanel panel
	    for ( int i = 16; i <= 19; i++ )
	    	leftJPanel.add( keys[ i ]);

	    // set rightJPanel layout to grid layout
	    rightJPanel = new JPanel();
	    rightJPanel.setLayout( new GridLayout( 4, 1 ) );

	    // add buttons to rightJPanel panel
	    for ( int i = 20; i <= 23; i++ )
	    	rightJPanel.add( keys[ i ]);

	    // set keyPadJPanel layout to grid layout
	    keyPadJPanel = new JPanel();
	    keyPadJPanel.setLayout( new GridLayout( 4, 4 ) );

	    // add buttons to keyPadJPanel panel
	    // 7, 8, 9, cancel
	    for ( int i = 7; i <= 10; i++ )
	    	keyPadJPanel.add( keys[ i ] );

	    // 4, 5, 6
	    for ( int i = 4; i <= 6; i++ )
	    	keyPadJPanel.add( keys[ i ] );

	    // clear
	    keyPadJPanel.add( keys[ 11 ] );

	    // 1, 2, 3
	    for ( int i = 1; i <= 3; i++ )
	    	keyPadJPanel.add( keys[ i ] );

	    // enter
	    keyPadJPanel.add( keys[ 12 ] );

	    // 0
	    keyPadJPanel.add( keys[ 0 ] );

	    // ., 00
	    for ( int i = 13; i <= 15; i++ )
	    	keyPadJPanel.add( keys[ i ] );

	    frame.add( leftJPanel, BorderLayout.WEST );
	    frame.add( rightJPanel, BorderLayout.EAST );
	    frame.add( textJPanel, BorderLayout.CENTER );
	    frame.add( keyPadJPanel, BorderLayout.SOUTH );

	}

	class buttonListenerConcat implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if ( e.getActionCommand().equals( "Clear" ))
				input = "";
			else if(e.getActionCommand().equals( "Cancel" ))
			{
					Object[] options = { "CANCEL", "OK" };
					int action = JOptionPane.showOptionDialog(null, "Click OK to exit the ATM", "Warning",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					// textArea.setText(String.valueOf(action));
					if(action == 1)
						System.exit(0);
			}
			//FUNCTION KEY EVENT HERE
			// else if ( e.getActionCommand().equals( "Withdrawl" ) ) {
			//
			// }else if ( e.getActionCommand().equals( "Deposit" ) ) {
			//
			// }else if ( e.getActionCommand().equals( "TRANSFER" ) ) {
			//
			// }else if ( e.getActionCommand().equals( "EXIT" ) ) {
			//
			// }
			else {
				if(!isPassword) {
					input = input.concat( e.getActionCommand() );
				}
				else {
					password = password.concat( e.getActionCommand());
					input = input.concat("*");
				}
				System.out.printf("%s ", input);
			}
			textArea.setText(input);



		}
	}
}
