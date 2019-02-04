package windowBuilder;

import java.io.IOException;

//== LA FENETRE OU ON VA ENTRER LES VARIABLES =======


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
//import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import comArduino.SerialTest;
import engine.maths.Vector3f;

import javax.swing.JButton;

public class InputWindow extends JFrame implements ActionListener {
	
	private final int SIZE_X = 400, SIZE_Y = 250;

	private JFrame frame;
	private JTextField textField_X;
	private JTextField textField_Y;
	private JTextField textField_Z;
	
	JButton button_Go;
	JButton button_bdot;
	
	private Vector3f rotation;
	//The rotation we'll give to the cubesat
	float rotationX, rotationY, rotationZ = 0;

	
	private boolean sending = false;
	//We only send the data once per click. This boolean regulates that.
	
	private boolean activatedCom = false;

	/**
	 * Launch the application.
	 */
	//This class will be launched from MainClass. It therefore does not need a main() of its own.
	/* 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputWindow window = new InputWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application. (CONSTRUCTOR)
	 */
	public InputWindow() {
		rotation = new Vector3f();
		initialize();
		this.frame.setVisible(true);
		
		//frame.addKeyListener( new MKeyListener() );
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		

		frame.setBounds(100, 100, SIZE_X, SIZE_Y); //X, Y, sizeX, sizeY
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSetCubesatAngular = new JLabel("Set cubeSat angular speed:");
		lblSetCubesatAngular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSetCubesatAngular.setBounds(20, 11, 176, 20);
		frame.getContentPane().add(lblSetCubesatAngular);
		
		//TEXT FIELDS (to enter text)
		
		textField_X = new JTextField();
		textField_X.setBounds(20, 50, 86, 20);
		frame.getContentPane().add(textField_X);
		textField_X.setColumns(10);
		
		textField_Y = new JTextField();
		textField_Y.setBounds(20, 90, 86, 20);
		frame.getContentPane().add(textField_Y);
		textField_Y.setColumns(10);
		
		textField_Z = new JTextField();
		textField_Z.setBounds(20, 130, 86, 20);
		frame.getContentPane().add(textField_Z);
		textField_Z.setColumns(10);
		
		textField_X.addKeyListener( new MKeyListener() );
		textField_Y.addKeyListener( new MKeyListener() );
		textField_Z.addKeyListener( new MKeyListener() );
		
		//Labels (text shown on the window)
		JLabel lblXaroundThe = new JLabel("X (around the horizontal axis)");
		lblXaroundThe.setBounds(116, 49, 170, 20);
		frame.getContentPane().add(lblXaroundThe);
		
		JLabel lblYaroundThe = new JLabel("Y (around the vertical axis)");
		lblYaroundThe.setBounds(116, 89, 170, 20);
		frame.getContentPane().add(lblYaroundThe);
		
		JLabel lblZaroundThe = new JLabel("Z (around the depth axis)");
		lblZaroundThe.setBounds(116, 129, 170, 20);
		frame.getContentPane().add(lblZaroundThe);
		
		//Bouton
		button_Go = new JButton("Go !");
		button_Go.setBounds(270, 175, 90, 20);
		frame.getContentPane().add(button_Go);
		//Event handling
		button_Go.addActionListener(this);
		button_Go.addKeyListener( new MKeyListener() );
		
		button_bdot = new JButton("B-dot !");
		button_bdot.setBounds(270, 205, 90, 20);
		frame.getContentPane().add(button_bdot);
		//Event handling
		button_bdot.addActionListener(this);
		button_bdot.addKeyListener( new MKeyListener() );
		
		
	}
	
	
	//====================================================================
	//     EVENT HANDLING 
	//================================================================
	@Override
	public void actionPerformed(ActionEvent e){
				
		//If we clicked on the "Go!" button
		if (e.getSource() == button_Go && !sending){
			//We'll store the X, Y, and Z rotation
			float rotationX = rotation.getX(), rotationY = rotation.getY(), rotationZ = rotation.getZ();

			boolean good = true;
			
			try {
				rotationX = Float.parseFloat( textField_X.getText() );
				rotationY = Float.parseFloat( textField_Y.getText() );
				rotationZ = Float.parseFloat( textField_Z.getText() );
				
			//Make sure some dumbnut didn't enter characters that aren't real numbers
			}  catch (NumberFormatException err) { 
				System.out.println("ERROR: Please enter a real number"); 
				good = false;
			}
			
			//If real numbers were indeed entered without a problem
			if (good) {
				rotation = new Vector3f( rotationX, rotationY, rotationZ );
				//Set new rotation
			
				sending = true; //And say yes to sending it to the simulation
			}
		}
		
		
		//If we clicked on the "B-dot!" button
		if (e.getSource() == button_bdot ){
			
			activatedCom = !activatedCom;
			//Toggle communication (become true if false, become false if true)
			}
}
	
	
	
	//GETTERS
	public boolean SendingData() {
		return sending;
	}
	
	public Vector3f getRotation() {
		
		sending = false;
		//Regulate. This makes sure you can only get the rotation once per click.
		
		return rotation;
	}
	
	public boolean getComStatus() {
		return activatedCom;
	}
	
	
	public void close() {
		//Trouver la commande qui ferme la fenetre sans avoir a cliquer sur Exit
		System.exit(0);
	}
	
	
}



//=============================================
//     FOR KEYBOARD EVENTS
//=============================================

class MKeyListener extends KeyAdapter {

	@Override
    public void keyPressed(KeyEvent event) {
		
		//Pressing the "Esc" or F4 key to close window
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE || event.getKeyCode() == KeyEvent.VK_F4 ) {
			System.exit(0);
		}	
    }
}





