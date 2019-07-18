package anakthsh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringReader;
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
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortedNumericSortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenGroup;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.join.ParentChildrenBlockJoinQuery;
import org.apache.lucene.search.join.QueryBitSetProducer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;
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
				applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			    JCheckBox arrangeByStars = new JCheckBox("Stars");
			    arrangeByStars.setBounds(50, 180, 100, 40);
			    arrangeByStars.setFont(new Font("Serif", Font.BOLD, 20));
			    arrangeByStars.setBackground(new Color(0, 114, 125));
			    arrangeByStars.setForeground(Color.white);
			    applicationFrame.getContentPane().add(arrangeByStars);
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
				//Panel
			    JPanel resultsPanel = new JPanel();
			    //p.add(new JTextField("some text"));
			    //resultsPanel.setSize(500,892);
			    //applicationFrame.getContentPane().add(p);
				//Search button
				button.setBackground(Color.gray);
				button.setForeground(new Color(0, 0, 0));
				button.setBounds(950, 55, 150, 40);
				button.setFont(new Font("Serif", Font.BOLD, 20));
				applicationFrame.getRootPane().setDefaultButton(button);
				applicationFrame.getContentPane().add(button);//adding button in JFrame
				
				
				
				ActionListener actionlistener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						//JPanel view = new JPanel();
						Queries q = null;
						resultsPanel.removeAll();
						try {
							q = new Queries();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						Analyzer analyzer = q.getAnalyzer();
						//EnglishAnalyzer analyzer = new EnglishAnalyzer();
						//TokenStream stream = analyzer.tokenStream("categories", textField.getText().toLowerCase());
						//System.out.println(stream.toString());
						//System.exit(1);
						IndexSearcher indexsearcher = q.getSearcher();
						
						IndexReader reader = q.getIndexReader();
						
						//ScoreDoc [] scoredoc  = null;
						
						button.setText("Search Again");
						
						QueryParser categoryParser = new QueryParser("categories", analyzer);
						//QueryParser categoryParser2 = new QueryParser("categories", analyzer);
						QueryParser nameParser = new QueryParser("name", analyzer);
						QueryParser textParser = new QueryParser("text", analyzer);
						QueryParser locationParser = new QueryParser("city",analyzer);
						
						Builder BooleanBuilder = new BooleanQuery.Builder();
						
						
						Query categoryQuery = null;
						//Query categoryQuery2 = null;
						Query nameQuery = null;
						Query textQuery = null;
						Query locationQuery = null;
						try {
							locationQuery = locationParser.parse("phoenix");
							
							if(name.isSelected()) {
								nameQuery = nameParser.parse(textField.getText().toLowerCase());
								BooleanBuilder.add(new BooleanClause(nameQuery, BooleanClause.Occur.MUST));
								BooleanBuilder.add(new BooleanClause(locationQuery, BooleanClause.Occur.MUST));
							}
							if(category.isSelected()) {
								//String [] terms = null;
								//String = textToTrim;
								//terms = textField.getText().toLowerCase().split(" ");
								categoryQuery = categoryParser.parse(textField.getText().toLowerCase());
								/*if(terms.length > 1) {
									categoryQuery2 = categoryParser2.parse(terms[1]);
									BooleanBuilder.add(new BooleanClause(categoryQuery2, BooleanClause.Occur.MUST));
								}*/
								BooleanBuilder.add(new BooleanClause(categoryQuery, BooleanClause.Occur.MUST));
								
								BooleanBuilder.add(new BooleanClause(locationQuery, BooleanClause.Occur.MUST));
								System.out.println(BooleanBuilder.toString());
								
							}
							if(text.isSelected() && (!name.isSelected()) && (!category.isSelected())) {
								textQuery = textParser.parse(textField.getText().toLowerCase());
								BooleanBuilder.add(new BooleanClause(textQuery, BooleanClause.Occur.MUST));
							}
							/*
							if(text.isSelected() && (name.isSelected() || category.isSelected())) {							 
								textQuery = textParser.parse(textField.getText().toLowerCase());
								BooleanBuilder.add(new BooleanClause(textQuery, BooleanClause.Occur.MUST));
								nameQuery = nameParser.parse(textField.getText().toLowerCase());
								BooleanBuilder.add(new BooleanClause(nameQuery, BooleanClause.Occur.MUST));
								
							}
							*/
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//System.out.println(boolquery.clauses().toString());
						BooleanQuery booleanQuery = BooleanBuilder.build();
						/*
						Builder bq = new BooleanQuery.Builder();
						TermQuery catQuery1 = new TermQuery(new Term("categories", textField.getText().toLowerCase().toString()));
						TermQuery catQuery2 = new TermQuery(new Term("name", "Parker"));
						bq.add(new BooleanClause(boolquery, BooleanClause.Occur.MUST));
						*/
						System.out.println(textField.getText());
						
						
						
						
						
						//QueryParser categoryParser = new QueryParser("categories", analyzer);
						
						QueryParser query = null;
						//String searchText = "name:" + textField.getText().toLowerCase() + " city:phoenix";
						//System.out.println(searchText);
						
						query = new QueryParser("business_id", analyzer);
						
						TopDocs x = null;
						TopFieldDocs  y= null;
						TopFieldDocs [] array = null; 
						//array[0] = x;
						//array[1] = y;
						
					
						SortField stars = new SortedNumericSortField("stars", SortField.Type.LONG, true);
						SortField reviewNum = new SortedNumericSortField("review_num", SortField.Type.LONG, true);
						
						//SortField [] arraySort ;
						//arraySort [0] = reviewNum;
						
						
						
						Sort sort = new Sort(stars,reviewNum);
						Sort reviewSort = new Sort(reviewNum);
						
						
						System.out.println(sort.toString());
						if(arrangeByStars.isSelected()) {
							
							try {
								x = indexsearcher.search(booleanQuery, 10000, sort);
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
						else {
							try {
								x = indexsearcher.search(booleanQuery, 10000);
								
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
						
						SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
						//htmlFormatter.highlightTerm(originalText, tokenGroup)
						Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(booleanQuery));
						/*Formatter formatter = new SimpleHTMLFormatter();
						QueryScorer scorer = new QueryScorer(booleanQuery);
						Highlighter highlighter = new Highlighter(formatter, scorer);
						Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 10);
						highlighter.setTextFragmenter(fragmenter);
						*/
						System.out.println(sort.toString());
						
						System.out.println(booleanQuery.clauses().toString());
						QueryBitSetProducer ko = new QueryBitSetProducer(booleanQuery);
						
						System.out.println(x.totalHits + ko.toString());
						ParentChildrenBlockJoinQuery join = new ParentChildrenBlockJoinQuery(ko, locationQuery, 0);
						//for (int i = 0; i < x.totalHits.value; i++) {
							//indexsearcher.searchAfter(after, query, numHits)
						//}
						//System.exit(1);
						//JList<Component> list = new JList();
						String infotext = "";
						
						
						if(x.totalHits.value != 0) {
							ArrayList<String> idList = new ArrayList<String>();
							//test
							TopFieldDocs [] shardHits = new TopFieldDocs[30];
							//test
							TopDocs idmatch = null;
							//TopDocs [] matchesToSort = null;
							//BooleanBuilder = new BooleanQuery.Builder(); //reInitialize the BooleanBuilder
							idList.clear();
							idList.add("0");
							for (int i = 0; i < x.totalHits.value; i++) {
								
								if (i == 20 || idList.size()-1 > 100) {
									
									break;
									
								}
								
								Document doc = null;
								
								
								try {
									doc = indexsearcher.doc(x.scoreDocs[i].doc);
								} catch (IOException e) {
									e.printStackTrace();
								}
								
								
								//String docText = (String)doc.getField("text").stringValue();
								
								TokenStream nameTokenStream = null;
								TokenStream categoriesTokenStream = null;
								//TokenStream textTokenStream = null;
								
								/*  NOT YET SHOWING CRITICS SO NO NEED TO HIGHLIGHT TEXT LIKE THAT
								if(text.isSelected()) {
									try {
										textTokenStream = TokenSources.getAnyTokenStream(reader, x.scoreDocs[i].doc, "text", analyzer);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							    	TextFragment[] textFrag = null;
							    	 try {
							    		 textFrag = highlighter.getBestTextFragments(categoriesTokenStream, docCategories, false, 20);
										} catch (IOException | InvalidTokenOffsetsException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										}
							    	//String docName = null;
								    for (int j = 0; j < textFrag.length; j++) {
								        if ((textFrag[j] != null) && (textFrag[j].getScore() > 0)) {
								        	docCategories = textFrag[j].toString();
								        	System.out.println((textFrag[j].toString()));
								        }
								      }
								    
								}*/
									//System.exit(1);
								if(text.isSelected()) {
									String docName = (String)doc.getField("name").stringValue();
									String docCategories = (String)doc.getField("categories").stringValue();
									
									try {
										categoriesTokenStream = TokenSources.getAnyTokenStream(reader, x.scoreDocs[i].doc, "categories", analyzer);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							    	TextFragment[] categoriesFrag = null;
							    	 try {
							    		 categoriesFrag = highlighter.getBestTextFragments(categoriesTokenStream, docCategories, false, 20);
										} catch (IOException | InvalidTokenOffsetsException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										}
							    	//String docName = null;
								    for (int j = 0; j < categoriesFrag.length; j++) {
								        if ((categoriesFrag[j] != null) && (categoriesFrag[j].getScore() > 0)) {
								        	docCategories = categoriesFrag[j].toString();
								        	System.out.println((categoriesFrag[j].toString()));
								        }
								        //System.out.println(docCategories.toString());
								        //System.exit(1);
								    }
								    
								    try {
										nameTokenStream = TokenSources.getAnyTokenStream(reader, x.scoreDocs[i].doc, "name", analyzer);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							    	TextFragment[] nameFrag = null;
							    	 try {
											nameFrag = highlighter.getBestTextFragments(nameTokenStream, docName, false, 20);
										} catch (IOException | InvalidTokenOffsetsException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										}
							    	//String docName = null;
								    for (int j = 0; j < nameFrag.length; j++) {
								        if ((nameFrag[j] != null) && (nameFrag[j].getScore() > 0)) {
								        	docName = nameFrag[j].toString();
								        	System.out.println((nameFrag[j].toString()));
								        }
								    }
								    
								
									
									String info =  "<p>" + docName + "</p>" +
											"  LOCATION:  " + (String)doc.getField("city").stringValue() + "<br>" +
											"   STARS:  " + (String)doc.getField("stars_value").stringValue() + "<br>" +
											"   CATEGORIES:   " + docCategories  + "<hr>";
								    infotext = infotext + info;
									
									
								}
								if(category.isSelected() || name.isSelected()) {
									
										String docName = (String)doc.getField("name").stringValue();
										String docCategories = (String)doc.getField("categories").stringValue();
										
										if(category.isSelected()) {
											try {
												categoriesTokenStream = TokenSources.getAnyTokenStream(reader, x.scoreDocs[i].doc, "categories", analyzer);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									    	TextFragment[] categoriesFrag = null;
									    	 try {
									    		 categoriesFrag = highlighter.getBestTextFragments(categoriesTokenStream, docCategories, false, 20);
												} catch (IOException | InvalidTokenOffsetsException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												}
									    	//String docName = null;
										    for (int j = 0; j < categoriesFrag.length; j++) {
										        if ((categoriesFrag[j] != null) && (categoriesFrag[j].getScore() > 0)) {
										        	docCategories = categoriesFrag[j].toString();
										        	System.out.println((categoriesFrag[j].toString()));
										        }
										        //System.out.println(docCategories.toString());
										        //System.exit(1);
										      }
										    
										}
									    if(name.isSelected()) {
									    	try {
												nameTokenStream = TokenSources.getAnyTokenStream(reader, x.scoreDocs[i].doc, "name", analyzer);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
									    	TextFragment[] nameFrag = null;
									    	 try {
													nameFrag = highlighter.getBestTextFragments(nameTokenStream, docName, false, 20);
												} catch (IOException | InvalidTokenOffsetsException e2) {
													// TODO Auto-generated catch block
													e2.printStackTrace();
												}
									    	//String docName = null;
										    for (int j = 0; j < nameFrag.length; j++) {
										        if ((nameFrag[j] != null) && (nameFrag[j].getScore() > 0)) {
										        	docName = nameFrag[j].toString();
										        	System.out.println((nameFrag[j].toString()));
										        }
										      }
										    
										}
									   
									    String info =  "<p>" + docName + "</p>" +
												"  LOCATION:  " + (String)doc.getField("city").stringValue() + "<br>" +
												"   STARS:  " + (String)doc.getField("stars_value").stringValue() + "<br>" +
												"   CATEGORIES:   " + docCategories   + "<hr>";
									    infotext = infotext + info;
								}
								
								/*
								if(text.isSelected() && name.isSelected()) {
									
								}
								*/
								
								//view.add(new textField())
								JEditorPane tempField = new JEditorPane();
								resultsPanel.add(tempField);
								
								tempField.setSize(200, 200); //logika peritto
								tempField.setContentType("text/html");
								//tempField.setText(info);
								//infotext = infotext + info;
								//JLabel tempLabel = new JLabel(info);
								//list.add(tempLabel);
								//list.add(x)
								}
							
							
							/*testarea
							//TopDocs [] anArray = (TopDocs[]) shardHits.toArray(); 
							TopDocs temp = null;
							temp = TopDocs.merge(null, 2, shardHits);
							System.out.println(temp.toString());
							testarea*/
						}
						else {
							infotext = "NO RESULTS TRY AGAIN";
						}
						
						resultsPanel.revalidate();
						//System.out.println(list.getComponentCount());
						editorPane.setEditable(false);
						editorPane.setContentType("text/html");
						editorPane.setText(infotext);
						editorPane.setCaretPosition(0);
						System.out.println(editorPane.getText().toString());
						booleanQuery = null;
						
					}
					
				};
				
				
				
				
				
				
				button.addActionListener(actionlistener);
				DeferredDocumentListener listener = new DeferredDocumentListener(3000 , actionlistener,true);
				textField.getDocument().addDocumentListener(listener);
				textField.addFocusListener(new FocusListener() {
		            @Override
		            public void focusGained(FocusEvent e) {
		                listener.start();
		            }

		            @Override
		            public void focusLost(FocusEvent e) {
		                listener.stop();
		            }
		        });

				//textField.addActionListener(listener);
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
		        
				JScrollPane scrollpane = new JScrollPane();
				scrollpane.getViewport().setView(editorPane);
				scrollpane.setBounds(210,180,892,500);
			    applicationFrame.getContentPane().add(scrollpane);
			    
				applicationFrame.setLayout(null);//using no layout managers  
				applicationFrame.setVisible(true);//making the frame visible 
				
	}
	
	
	/*
	public static String stem(String string) throws IOException {
	    TokenStream tokenizer = new StandardTokenizer();
	    tokenizer = new StandardFilter(Version.LUCENE_8_0_0, tokenizer);
	    tokenizer = new LowerCaseFilter(Version.LUCENE_8_0_0, tokenizer);
	    tokenizer = new PorterStemFilter(tokenizer);

	    CharTermAttribute token = tokenizer.getAttribute(CharTermAttribute.class);

	    tokenizer.reset();

	    StringBuilder stringBuilder = new StringBuilder();

	    while(tokenizer.incrementToken()) {
	        if(stringBuilder.length() > 0 ) {
	            stringBuilder.append(" ");
	        }

	        stringBuilder.append(token.toString());
	    }

	    tokenizer.end();
	    tokenizer.close();

	    return stringBuilder.toString();
	}
	*/
	
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
		
	 	/*
	 	 * DeferredDocumentListener listener = new DeferredDocumentListener(1000, new ActionListener() {
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
