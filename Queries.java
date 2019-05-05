package anakthsh;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class Queries {

	private Directory indexdirectory = null;
	private IndexReader indexreader = null;
	private IndexSearcher indexsearcher = null;
	private Analyzer analyzer = null;

	
	public Queries() throws IOException {
		String indexPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Index";
		
		final Path indexDir = Paths.get(indexPath);
		
		if (!Files.isReadable(indexDir)) {
			
		     System.out.println("Document directory '" +indexDir.toAbsolutePath()+
		    		 "' does not exist or is not readable, please check the path");
		     System.exit(1);
		     
		}
		
		this.indexdirectory = FSDirectory.open(indexDir);
		this.indexreader = DirectoryReader.open(indexdirectory);
		this.analyzer = new StandardAnalyzer();
		System.out.println("The Number of Docs in this Index is :" + indexreader.numDocs());
		this.indexsearcher = new IndexSearcher(indexreader);
		
		//analyzer.
	}
	
	public IndexSearcher getSearcher() {
		return this.indexsearcher;
	}
	
	public Analyzer getAnalyzer() {
		return this.analyzer;
	}
	
	public void getStatistics() throws ParseException, IOException {
		
		System.out.println("i am in");
		System.out.println(analyzer.toString());
		//Query q = new QueryParser("city", analyzer).parse("Phoenix");
		Query reviewquery = IntPoint.newExactQuery("stars",1);
		Query q = new QueryParser("name", analyzer).parse("phoenix");
		//System.out.println(q.toString());
		TopDocs x = indexsearcher.search(q, indexsearcher.count(q));
	    System.out.println("I retrieved that many docs from my query : "+x.scoreDocs.length);
	    System.out.println(indexsearcher.count(reviewquery));
	    //System.out.println("The index directory contains that many indexed documents " + indexreader.numDocs());  
	      				//MAX MIN MEAN CALCULATOR
	      
	    int max = -10;
	    int minimum = 1000000;
	    int mean = 0;
	    int review_count;
	    int iterator = 1;
	    
	    for (int i=0; i<x.scoreDocs.length; i++) {
	    	
	    	//System.out.println(x.scoreDocs[i].doc);
	    	Document doc = indexsearcher.doc(x.scoreDocs[i].doc);
	    	//System.out.println(doc.toString());
	    	//System.exit(1);
	    	review_count = (int)doc.getField("review_count").numericValue();
	    	
	    	if (max < review_count) {
	    		max = review_count;
	    	 }
	    	
	    	if (minimum > review_count) {
	    		minimum = review_count;
	    	}
	    	
	    	mean = (mean + review_count);
	    	iterator++;
	    	  //String value = doc.getField("id").stringValue();
	    	  //Query reviewsWithId = new QueryParser("id",analyzer).parse(value);
	    	  //TopDocs reviews = indexsearcher.search(reviewsWithId, 100);
	    	  //System.out.println("I retrieved that many docs from my reviewQuery : "+reviews.scoreDocs.length);
	    	  //Document doc = indexsearcher.doc(x.scoreDocs[i].doc);
	    	System.out.println(doc.getField("name").stringValue());
	    }
	    mean = mean / iterator;
	    
	    			// MAX MIN MEAN OUTPUTS 
	    System.out.println("The Statistics Results about businesses in Phoenix are (look below) \n" + "MAX : " + max + " MIN : " + minimum + " MEAN : " + mean);
	    System.out.println("The Number of Docs Matched the Query : " + q.toString() + " was " + x.scoreDocs.length); //number of docs matched the query
	    System.out.println("The Number of Docs indexed is : " + indexsearcher.count(q)); //number of docs indexed
	    
	}
	
	/*public static void main (String args[]) throws IOException, ParseException {
		
						// Setting the path where indexed files exist
		String indexPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Index";
		final Path indexDir = Paths.get(indexPath);
		
		if (!Files.isReadable(indexDir)) {
			
		     System.out.println("Document directory '" +indexDir.toAbsolutePath()+
		    		 "' does not exist or is not readable, please check the path");
		     System.exit(1);
		     
		}
		
						// Constructing the indexreader
		Directory indexdirectory = FSDirectory.open(indexDir);
		IndexReader indexreader = DirectoryReader.open(indexdirectory);
		Analyzer analyzer = new StandardAnalyzer();
		System.out.println("The Number of Docs in this Index is :" + indexreader.numDocs());
		IndexSearcher indexsearcher = new IndexSearcher(indexreader);
						//QUERIES
		Query q = new QueryParser("city", analyzer).parse("Phoenix");
		Query reviewquery = IntPoint.newExactQuery("stars",1);
		
		TopDocs x = indexsearcher.search(q, indexsearcher.count(q));
	    System.out.println("I retrieved that many docs from my query : "+x.scoreDocs.length);
	    System.out.println(indexsearcher.count(reviewquery));
	    //System.out.println("The index directory contains that many indexed documents " + indexreader.numDocs());  
	      				//MAX MIN MEAN CALCULATOR
	      
	    int max = -10;
	    int minimum = 1000000;
	    int mean = 0;
	    int review_count;
	    int iterator = 1;
	    
	    for (int i=0; i<x.scoreDocs.length; i++) {
	    	
	    	//System.out.println(x.scoreDocs[i].doc);
	    	Document doc = indexsearcher.doc(x.scoreDocs[i].doc);
	    	//System.out.println(doc.toString());
	    	//System.exit(1);
	    	review_count = (int)doc.getField("review_count").numericValue();
	    	
	    	if (max < review_count) {
	    		max = review_count;
	    	 }
	    	
	    	if (minimum > review_count) {
	    		minimum = review_count;
	    	}
	    	
	    	mean = (mean + review_count);
	    	iterator++;
	    	  //String value = doc.getField("id").stringValue();
	    	  //Query reviewsWithId = new QueryParser("id",analyzer).parse(value);
	    	  //TopDocs reviews = indexsearcher.search(reviewsWithId, 100);
	    	  //System.out.println("I retrieved that many docs from my reviewQuery : "+reviews.scoreDocs.length);
	    	  //Document doc = indexsearcher.doc(x.scoreDocs[i].doc);
	    	//System.out.println(doc.getField("id").stringValue());
	    }
	    mean = mean / iterator;
	    
	    			// MAX MIN MEAN OUTPUTS 
	    System.out.println("The Statistics Results about businesses in Phoenix are (look below) \n" + "MAX : " + max + " MIN : " + minimum + " MEAN : " + mean);
	    System.out.println("The Number of Docs Matched the Query : " + q.toString() + " was " + x.scoreDocs.length); //number of docs matched the query
	    System.out.println("The Number of Docs indexed is : " + indexsearcher.count(q)); //number of docs indexed
	      
		
		
	}*/
	
	/*String index = "index";
	String field = "contents";
	String queries = null;
	int repeat = 0;
	boolean raw = false;
	String queryString = null;
	int hitsPerPage = 10;*/
}
