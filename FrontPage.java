package anakthsh;

import java.awt.Color;
import java.awt.Window;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.lucene.queryparser.classic.ParseException;

public class FrontPage {
	
	public static void main(String[] args) throws IOException, ParseException {
		
		JFrame window = new JFrame();
		JButton button = new JButton("Search");//creating instance of JButton  
		JTextField textField = new JTextField();
		
		
		window.setTitle("	Searchy");
		window.setBounds(100,100,1200,772);
		window.setBackground(Color.BLACK);
		window.getContentPane().setBackground(new Color(77, 209, 204));
		
		
		textField.setBackground(new Color(255, 255, 255));
		textField.setForeground(new Color(255, 69, 0));
		textField.setBounds(10, 11, 731, 29);
		window.getContentPane().add(textField);
		textField.setColumns(10);
		
		window.add(button);//adding button in JFrame  
		          
		window.setLayout(null);//using no layout managers  
		window.setVisible(false);//making the frame visible 
		
		JLabel lblArrangeBy = new JLabel("Arrange by ");
	    lblArrangeBy.setBounds(54, 101, 97, 14);
	    window.getContentPane().add(lblArrangeBy);
		
		JTextField textField_1 = new JTextField();
	    textField_1.setBounds(110, 59, 169, 20);
	    window.getContentPane().add(textField_1);
	    textField_1.setColumns(10);
	    
	    JLabel lblDidYouMean = new JLabel("Did You Mean:");
	    lblDidYouMean.setBounds(10, 62, 90, 14);
	    window.getContentPane().add(lblDidYouMean);
		
		
		Queries q = new Queries();
		q.getStatistics();
		
		//PorterStemmer stemmer = new PorterStemmer();
	}
}
