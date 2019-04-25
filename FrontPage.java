package anakthsh;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;

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
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(10, 11, 731, 29);
		window.getContentPane().add(textField);
		textField.setColumns(10);
		
		window.add(button);//adding button in JFrame  
		          
		window.setLayout(null);//using no layout managers  
		window.setVisible(true);//making the frame visible 
		
		/*JLabel lblArrangeBy = new JLabel("Arrange by ");
	    lblArrangeBy.setBounds(54, 101, 97, 14);
	    window.getContentPane().add(lblArrangeBy);
		
		JTextField textField_1 = new JTextField();
	    textField_1.setBounds(110, 59, 169, 20);
	    window.getContentPane().add(textField_1);
	    textField_1.setColumns(10);
	    
	    JLabel lblDidYouMean = new JLabel("Did You Mean:");
	    lblDidYouMean.setBounds(10, 62, 90, 14);
	    window.getContentPane().add(lblDidYouMean);*/
		
	    //DocumentListener listener = new DocumentListener()
	    /*textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 textField.setText("Welcome to Javatpoint.");  
				
			}
	    	
	    });*/
	    
	    
	    
	    textField.getDocument().addDocumentListener(new DocumentListener() {
	    	String text = textField.getText();
            public void changedUpdate(DocumentEvent arg0) 
            {

            }
            public void insertUpdate(DocumentEvent arg0) 
            {
                //System.out.println("IT WORKS");
            	//call the suggester
                text = textField.getText();
                System.out.println(text);
                //window.setPrice(window.countTotalPrice(TabPanel.this));
            }

            public void removeUpdate(DocumentEvent arg0) 
            {
                //System.out.println("IT WORKS");
            	//call the suggester
                text = textField.getText();
                //String suggestion = suggester(text);
                //call a method that returns suggestion
                System.out.println(text);
                //panel.setPrice(panel.countTotalPrice(TabPanel.this));
            }
	    }); 
	    
	    
		Queries q = new Queries();
		q.getStatistics();
		
		//PorterStemmer stemmer = new PorterStemmer();
	}
	
	// Get suggestions given a prefix and a region.
    private static String lookup(AnalyzingInfixSuggester suggester, String word,String region) {
        try {
            List<Lookup.LookupResult> results;
            HashSet<BytesRef> contexts = new HashSet<BytesRef>();
            contexts.add(new BytesRef(region.getBytes("UTF8")));
            // Do the actual lookup.  We ask for the top 2 results.
            results = suggester.lookup(word, contexts, 5, true, false);
            System.out.println("-- \"" + word + "\" (" + region + "):");
            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
                Word p = getWord(result);
                if (p != null) {
                   return p.word;
                }
            }
        } catch (IOException e) {
            System.err.println("Error");
        }
		return word;
    }
    
    
    private static Word getWord(Lookup.LookupResult result)
    {
        try {
            BytesRef payload = result.payload;
            if (payload != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(payload.bytes);
                ObjectInputStream in = new ObjectInputStream(bis);
                Word p = (Word) in.readObject();
                return p;
            } else {
                return null;
            }
        } catch (IOException|ClassNotFoundException e) {
            throw new Error("Could not decode payload :(");
        }
    }

}
