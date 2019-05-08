package anakthsh;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.json.JSONException;
import org.json.JSONObject;

public class FrontPage {
	
	private static JFrame applicationFrame;
	private static JButton button;
	private static JTextField textField;
	private static JTextField suggestionField;
	//private FocusListener focuslistener;
	private static JLabel titleLabel;
	private static int flag;
	private static JEditorPane editorPane;
	
	public FrontPage() {
		
		this.applicationFrame = new JFrame();
		this.button = new JButton("Search");//creating instance of JButton  
		this.textField = new JTextField();
		this.suggestionField = new JTextField();
		this.editorPane = new JEditorPane();
		this.titleLabel = new JLabel("Find Stores In 'Phoenix'");
		this.flag = 0;
		
	}
	
	private static void initialize() throws IOException {
				
				
				applicationFrame.setTitle("	Uptown World");
				applicationFrame.setBounds(100,100,1200,772);
				applicationFrame.setBackground(Color.BLACK);
				applicationFrame.getContentPane().setBackground(new Color(0, 114, 125));
				
				//Title Label
				titleLabel.setBounds(210, 10, 450, 29);
				titleLabel.setFont(new Font("Serif", Font.BOLD, 25));
				titleLabel.setForeground(Color.white);
				applicationFrame.getContentPane().add(titleLabel);
				//Search textField 
				textField.setBackground(new Color(255, 255, 255));
				textField.setForeground(new Color(0, 0, 0));
				textField.setBounds(210, 55, 731, 40);
				textField.setFont(new Font("Stef", Font.TRUETYPE_FONT, 20));
				textField.setColumns(10);
				applicationFrame.getContentPane().add(textField);
				//Did you mean Label
				JLabel lblDidYouMean = new JLabel("Did You Mean:");
			    lblDidYouMean.setBounds(210, 140, 130, 40);
			    lblDidYouMean.setFont(new Font("Serif", Font.ITALIC, 20));
			    lblDidYouMean.setForeground(Color.white);
			    applicationFrame.getContentPane().add(lblDidYouMean);
			    //Did you mean Field
			    JLabel suggestionLabel = new JLabel("lorem ipsum");
			    suggestionLabel.setBounds(340, 140, 150, 40);
			    suggestionLabel.setFont(new Font("Serif", Font.BOLD, 20));
			    suggestionLabel.setForeground(Color.white);
			    applicationFrame.getContentPane().add(suggestionLabel);
			    //Filters Label
			    JLabel filterLabel = new JLabel("Arrange By");
			    filterLabel.setBounds(50, 140, 110, 40);
			    filterLabel.setFont(new Font("Serif", Font.BOLD, 20));
			    filterLabel.setForeground(Color.white);
			    applicationFrame.getContentPane().add(filterLabel);
			    //RadioButtons Label Search BY
			    JLabel radioLabel = new JLabel("Search By:");
			    radioLabel.setBounds(210, 100, 110, 40);
			    radioLabel.setFont(new Font("Serif", Font.BOLD, 20));
			    radioLabel.setForeground(Color.white);
			    applicationFrame.getContentPane().add(radioLabel);
			    //Choice buttons
			    JCheckBox category = new JCheckBox("category");
			    category.setBounds(320, 100, 100, 40);
			    category.setFont(new Font("Serif", Font.BOLD, 20));
			    category.setBackground(new Color(0, 114, 125));
			    category.setForeground(Color.white);
			    applicationFrame.getContentPane().add(category);
			    JCheckBox text = new JCheckBox("text");
			    text.setBounds(450, 100, 100, 40);
			    text.setFont(new Font("Serif", Font.BOLD, 20));
			    text.setBackground(new Color(0, 114, 125));
			    text.setForeground(Color.white);
			    applicationFrame.getContentPane().add(text);
			    JCheckBox name = new JCheckBox("name");
			    name.setBounds(550, 100, 100, 40);
			    name.setFont(new Font("Serif", Font.BOLD, 20));
			    name.setBackground(new Color(0, 114, 125));
			    name.setForeground(Color.white);
			    applicationFrame.getContentPane().add(name);
				
				//Search button
				button.setBackground(Color.gray);
				button.setForeground(new Color(0, 0, 0));
				button.setBounds(950, 55, 150, 40);
				button.setFont(new Font("Serif", Font.BOLD, 20));
				applicationFrame.getRootPane().setDefaultButton(button);
				applicationFrame.getContentPane().add(button);//adding button in JFrame
				
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JPanel view = new JPanel();
						Queries q = null;
						try {
							q = new Queries();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						Analyzer analyzer = q.getAnalyzer();
						IndexSearcher indexsearcher = q.getSearcher();
						
						button.setText("Search Again");
						
						QueryParser categoryParser = new QueryParser("categories", analyzer);
						QueryParser nameParser = new QueryParser("name", analyzer);
						
						Builder categoryQuery = new BooleanQuery.Builder();
						
						
						Query catquery = null;
						Query catQuery2 = null;
						try {
							if(name.isSelected()) {
								catQuery2 = nameParser.parse(textField.getText().toLowerCase());
								categoryQuery.add(new BooleanClause(catQuery2, BooleanClause.Occur.MUST));
							}
							if(category.isSelected()) {
								catquery = categoryParser.parse(textField.getText().toLowerCase());
								categoryQuery.add(new BooleanClause(catquery, BooleanClause.Occur.MUST));
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println(boolquery.clauses().toString());
						BooleanQuery boolquery = categoryQuery.build();
						/*
						Builder bq = new BooleanQuery.Builder();
						TermQuery catQuery1 = new TermQuery(new Term("categories", textField.getText().toLowerCase().toString()));
						TermQuery catQuery2 = new TermQuery(new Term("name", "Parker"));
						bq.add(new BooleanClause(boolquery, BooleanClause.Occur.MUST));
						*/
						System.out.println(textField.getText());
						
						
						
						
						
						//QueryParser categoryParser = new QueryParser("categories", analyzer);
						QueryParser textParser = new QueryParser("text", analyzer);
						Query query = null;
						//String searchText = "name:" + textField.getText().toLowerCase() + " city:phoenix";
						//System.out.println(searchText);
						/*
						try {
						 
							query = new QueryParser("name", analyzer).parse(searchText);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						*/
						TopDocs x = null;
						try {
							x = indexsearcher.search(boolquery, 10);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println(boolquery.clauses().toString());
						System.out.println(x.totalHits);
						//System.exit(1);
						//JList<Component> list = new JList();
						String text = "";
						if(x.totalHits.value != 0) {
							for (int i = 0; i < x.totalHits.value; i++) {
								
								if(i == 10) {break;}
								Document doc = null;
								try {
									doc = indexsearcher.doc(x.scoreDocs[i].doc);
								} catch (IOException e) {
									e.printStackTrace();
								}
								
								String info =  "<h3>" + (String)doc.getField("name").stringValue() + "</h3>" +
											"Location:  " + (String)doc.getField("city").stringValue() + "<br>"
											 + "<font style=\" color:#83a7ff \"> " 
											+"<strong>" + "categories: " + "</strong>" + (String)doc.getField("categories").stringValue()   + "<hr>";
								//view.add(new textField())
								text = text + info;
								//JLabel tempLabel = new JLabel(info);
								//list.add(tempLabel);
								//list.add(x)
							}
						}
						else {
							text = "NO RESULTS TRY AGAIN";
						}
						//System.out.println(list.getComponentCount());
						editorPane.setEditable(false);
						editorPane.setContentType("text/html");
						editorPane.setText(text);
						editorPane.setCaretPosition(0);
						System.out.println(editorPane.getText().toString());
						boolquery = null;
						
					}
					
				});
				applicationFrame.getContentPane().add(button);
				// Load Icon
				String fileName = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\worldtown.jpg";
				ImageIcon icon = null;
				icon = new ImageIcon(fileName);
				// Add Icon
		        JLabel label = new JLabel(icon);
		        label.setBounds(38, 10, 128, 128);
		        applicationFrame.getContentPane().add(label);
		        applicationFrame.setIconImage(icon.getImage());
				// ScrollPane
				JScrollPane scrollpane = new JScrollPane(editorPane);
				scrollpane.setBounds(210,180,892,500);
			    applicationFrame.getContentPane().add(scrollpane);
			    
				applicationFrame.setLayout(null);//using no layout managers  
				applicationFrame.setVisible(true);//making the frame visible 
				
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		//BufferedImage image;
		FrontPage GUI = new FrontPage();
		GUI.initialize();
		try {		
			RAMDirectory index_dir = new RAMDirectory();
	        StandardAnalyzer analyzer = new StandardAnalyzer();
	        AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(index_dir, analyzer);
	
	        // Create our list of products.
	        ArrayList<Word> words = new ArrayList<Word>();
	        words.add(
	            new Word(
	                "Electric Guitar",
	                "US",
	                100));
	        words.add(
	        		new Word(
	        		"Electric Train",
	                "US",
	                90));
	        words.add(
	        		new Word(
	        		"Acoustic Guitar",
	                "US",
	                80));
	        words.add(
	        		new Word(
	        		"Guarana Soda",
	                "US",
	                130));
	        
	        // Index the products with the suggester.
	        suggester.build(new WordIterator(words.iterator()));
	
	        // Do some example lookups.
	        lookup(suggester, "Gu", "US");
	        lookup(suggester, "Gu", "US");
	        lookup(suggester, "Gui", "US");
	        lookup(suggester, "Electric guit", "US");
	        
	    } catch (IOException e) {
	        System.err.println("Error!");
	    }
		
	 	/*DeferredDocumentListener listener = new DeferredDocumentListener(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Execute your required functionality here...
            	try {
            		suggestionField.setText(backDoor(textField.getText()).toLowerCase());
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	
            }
		}, true);
		*/
	    
	    /*
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
                //applicationFrame.setPrice(applicationFrame.countTotalPrice(TabPanel.this));
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
	    */
	    
		//Queries q = new Queries();
		//q.getStatistics();
		
		//PorterStemFilter stemmer = new PorterStemFilter();
	}
	/*
	public String backDoor(String word) throws IOException{
		
		CharArraySet stopWords = EnglishAnalyzer.getDefaultStopSet();

	    String docsPath = "resources/pop.json";
	    final Path docDir = Paths.get(docsPath);
	    if (!Files.isReadable(docDir)) {
	      System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
	      System.exit(1);
	    }
	    try (InputStream stream = Files.newInputStream(docDir)) {
		
		    	  FileReader Efile=new FileReader(docDir.toString());
					BufferedReader br = new BufferedReader(Efile);
					String line;
					while((line=br.readLine())!=null){
		  
				
						JSONObject jsonObject = new JSONObject(line);
						Iterator<?> keys = jsonObject.keys();
						while( keys.hasNext() ) {
						    String key = (String)keys.next();
						    key=key.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ").trim();
							if(key.contains("http") || key.contains("@") || stopWords.contains(key) || key.contains("RT")){
								continue;
							}else{
								 if ( jsonObject.has(key)) {
									if((Integer)jsonObject.get(key)!=0){
										suggestions.put((Integer)jsonObject.get(key),key);
									}
								 }
							}
						   

						   
						}
						
				//		System.out.println(suggestions);
				
				
		      
				}
					 RAMDirectory index_dir1 = new RAMDirectory();
			          StandardAnalyzer analyzer1 = new StandardAnalyzer();
			          AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester( index_dir1, analyzer1);	
						for(Map.Entry<Integer,String> entry : suggestions.entrySet()){
							words.add(new Word(entry.getValue(),"US",entry.getKey()));
						}
			            suggester.build(new WordIterator(words.iterator()));
			            
			            
			            return lookup(suggester, word, "US");
					
		    }catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return word;
	}
	*/
	
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
