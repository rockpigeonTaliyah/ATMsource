import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

public class GUI {

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
	public boolean EnterPressed;
	public boolean ExitPressed;
	public String functionChoice;
	public boolean keypadEnabled = false; //Disable keypad when not needed
	public boolean keypadNumberEnabled = true;
	public int amountChoice = 0;


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
		setMessage("");
	}

	public void mergeMessage(String message) {
		this.message = this.message.concat(message);
	}

	public void setInput( String input ) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}
	public String getFunctionInput(){
							 while ( functionChoice ==null ) {
								 delay(200);;
							 }

				 		return functionChoice;
	}
	public void resetChoice(){
		functionChoice = null;
		amountChoice = 0 ;
	}
	public void delay( int time ){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			return;
		}
	}
/*	public void	confirmAmount(){
		synchronized ( this ) {
						 try {

							 while (!inputEntered) {
								 Thread.sleep(200);

							 }
						 } catch (InterruptedException e) {
							 e.printStackTrace();
						 }
		}
	}*/
	public int getAmountInput(){

				//  System.out.println("qwe");
							//  amountChoice = 4;
		functionChoice = "";
						 // }
		return amountChoice;
		}
		// return "123";
	public void printInput() {
		textArea.setText(input);
	}

	//clear the inputArea
	public void clearInput() {
		setInput( "" );
		printInput();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String string) {
		password = string;
	}

	//stop the system for user to press enter
	public void waitTilInput() {
		resetState();
	    while ( !inputEntered ) {
	    	delay(200);
				// System.out.println("enter not pressed");
			}
			System.out.println(inputEntered);
	    //reset state
		inputEntered = false;
	}
	public void waitTilEnter() {
	    while ( !EnterPressed ) {
	    	delay(200);
		}
	    //reset state
		EnterPressed = false;
	}
	/*NOTE NO ONE USING*/
	// public void waitTilExit() {
	//     while ( !ExitPressed ) {
	//     	delay(200);
	// 	}
	//     //reset state
	// 	ExitPressed = false;
	// }
	public int waitChoice(){
		resetState();
		while (ExitPressed ==false && EnterPressed == false) {
			delay(200);
		}
		if (EnterPressed) {
			EnterPressed = false;
			return 1 ;
		}else {
			ExitPressed = false;
			return 0 ;
		}
	}
	public void resetState(){
		ExitPressed = false;
		EnterPressed = false;
	}
	public void mainMenuButtonAction(String action) {
		//left top for 4
		//right top for 4
		String [] ATMActionCommand = new String[8];
		switch (action) {
			case "_WITHDRWAL": ATMActionCommand = new String []{"","100","500","1000","","","","EXIT"}; break;
			case "_TRANSFER": ATMActionCommand =  new String []{"","100","500","1000","","","","EXIT"}; break;
			case "_BLANK":  ATMActionCommand =  new String []{"","","","","","","","EXIT"}; break;
			default:
				ATMActionCommand =  new String []{"BALANCE","WITHDRAW","","","TRANSFER","","","EXIT"}; break;
		}

		removeCurrentListener();
		for ( int i = 0; i <= 7; i++){
			keys[ 16 + i ].setText(ATMActionCommand[i]);
			keys[ 16 + i ].addActionListener( new buttonListenerFunction());

		}

	}

	/*public void balanceEnquiryButtonAction() {

		removeCurrentListener();
		for ( int i = 0; i <= 7; i++){
			keys[ 16 + i ].setText(ATMActionCommand[i]);
			keys[ 16 + i ].addActionListener( new buttonListenerFunction());
		}

	}
	// public void mainMenuButtonAction() {
	// 	//left top for 4
	// 	//right top for 4
	//
	// 	removeCurrentListener();
	// 	for ( int i = 0; i <= 7; i++){
	// 		keys[ 16 + i ].setText(ATMActionCommand[i]);
	// 		keys[ 16 + i ].addActionListener( new buttonListenerFunction());
	// 	}
	//
	// }

	/**
	 * Initialize the coents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setBounds(600, 120, 400, 450);//set frame position and size
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



			//declaration of textJPanel for containing messageArea and textArea
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
			keys[10].setBackground(new Color(255,0,0));
			keys[ 10 ].setOpaque(true);
			keys[ 10 ].setBorderPainted(false);
			// keys[ 10 ].setContentAreaFilled(true);
	    keys[ 11 ] = new JButton( "Clear" );
			keys[ 11 ].setBorderPainted(false);
			keys[ 11 ].setBorder(new LineBorder(new Color(0,0,0)));
	    keys[ 12 ] = new JButton( "Enter" );
			keys[12].setBackground(new Color(0,255,0));
			keys[ 12 ].setBorderPainted(false);
			// keys[ 12 ].setContentAreaFilled(true);
			keys[ 12 ].setOpaque(true);
			// keys[ 12 ].setBackground("GREEN");

	    keys[ 13 ] = new JButton( "." );
	    keys[ 14 ] = new JButton( "00" );
	    keys[ 15 ] = new JButton( "" );

	    // add action to buttons
			keys[ 10 ].addActionListener( new buttonListenerConcat());
	    keys[ 11 ].addActionListener( new buttonListenerConcat());
	    keys[ 12 ].addActionListener( new ActionListener() {
	    	@Override
	    	public void actionPerformed( ActionEvent e ) {
	    		System.out.println("Enter Pressed");
	    		if (keypadEnabled) {
	    			inputEntered = true;
	    			EnterPressed = true;
	    		}
	    	}
	    });
	    keys[ 13 ].addActionListener( new buttonListenerConcat());
	    keys[ 14 ].addActionListener( new buttonListenerConcat());

			//left top for 4
			//right top for 4
	    for ( int i = 0; i <= 7; i++){
	    	keys[ 16 + i ] = new JButton("");
				keys[ 16 + i ].setFocusPainted(false);
			}
	    // set leftJPanel layout to grid layout
	    leftJPanel = new JPanel();
	    leftJPanel.setLayout( new GridLayout( 4, 1 ) );
	    leftJPanel.setPreferredSize( new Dimension( 110, 100 ));


	    // add buttons to leftJPanel panel
	    for ( int i = 16; i <= 19; i++ )
	    	leftJPanel.add( keys[ i ]);

	    // set rightJPanel layout to grid layout
	    rightJPanel = new JPanel();
	    rightJPanel.setLayout( new GridLayout( 4, 1 ) );
	    rightJPanel.setPreferredSize( new Dimension( 110, 100 ));

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
	public void removeCurrentListener(){
		for ( int i = 0; i <= 7; i++){
			// keys[ 16 + i ].setText(ATMActionCommand[i]);
			// keys[ 16 + i ].addActionListener( new buttonListenerFunction());
			for( ActionListener al :  keys[ 16 + i ].getActionListeners() ) {
	        keys[ 16 + i ].removeActionListener( al );
	    }
		}
	}
	class buttonListenerConcat implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				System.out.println(password);
				if (e.getActionCommand() == "Clear") {
					input = "";
					password ="";
				}else if(e.getActionCommand() == "Cancel"){
					Object[] options = { "CANCEL", "OK" };
					int action = JOptionPane.showOptionDialog(null, "Click OK to exit the ATM", "Warning",JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, options, options[0]);
					// textArea.setText(String.valueOf(action));
					if(action == 1)
						System.exit(0);
			}else
				if(keypadEnabled && keypadNumberEnabled) {
					if(!isPassword) {
						input = input.concat( e.getActionCommand() );

					}
					else if (isPassword == true) {
						password = password.concat( e.getActionCommand());
						input = input.concat("*");
					}else{
					System.out.println("none");
				}
				}
				textArea.setText(input);
		}
	}

	class buttonListenerFunction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Count of listeners: " + ((JButton) e.getSource()).getActionListeners().length);
			// functionChoice = e.getActionCommand();
			switch(e.getActionCommand()){
				case "BALANCE":functionChoice = "BALANCE";
				  break;
				case "WITHDRAW": functionChoice = "WITHDRAWAL";
				 	break;
				case "DEPOSIT": functionChoice = "DEPOSIT";
				 	break;
				case "TRANSFER": functionChoice = "TRANSFER";
				 	break;
				case "EXIT": functionChoice = "EXIT" ;amountChoice = 4;
				ExitPressed  = true;
					inputEntered = true;
				 	break;
				case "":
					break;
				default :
					amountChoice = Integer.parseInt(e.getActionCommand());
					inputEntered = true;
					System.out.print(amountChoice);
				break;
			}
			// inputEntered = true;


		}
	}

}
