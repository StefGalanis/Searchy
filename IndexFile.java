package anakthsh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
//import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IndexFile {
	
	private IndexFile() {}
	
	public static void main(String args[]) throws ParseException {
		
		String indexPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Index";
				
		String docsPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3";
		
		//String reviewsPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\reviewsData";
		
		boolean create = true;
		
		final Path docDir = Paths.get(docsPath);
		System.out.println(docDir.toString());
		if (!Files.isReadable(docDir)) {
		     System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
		     System.exit(1);
		}
		
		/*
		final Path reviewsDir = Paths.get(reviewsPath);
		if (!Files.isReadable(reviewsDir)) {
		    System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
		    System.exit(1);
		}
		*/
		
		Date start = new Date();
	    try {
	      System.out.println("Indexing to directory '" + indexPath + "'...");
	      
	      Directory dir = FSDirectory.open(Paths.get(indexPath));
	      //Directory reviewDir = FSDirectory.open(Paths.get(indexPath));
	      Analyzer analyzer = new StandardAnalyzer();
	      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	      //Tokenizer
	      if (create) {
	        // Create a new index in the directory, removing any
	        // previously indexed documents:
	        iwc.setOpenMode(OpenMode.CREATE);
	      } else {
	        // Add new documents to an existing index:
	        iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	      }
	      
	      IndexWriter writer = new IndexWriter(dir, iwc);
	      indexDocs(writer, docDir);
	      //IndexWriter reviewsWriter = new IndexWriter(dir, iwc);
	      //indexDocs(reviewsWriter, reviewsDir);
	      System.out.println(writer.getDirectory());
	      System.out.println(writer.getDocStats());
	      //System.exit(1);
	      //writer.close();
	      //IndexReader indexreader = new IndexReader();
	      IndexReader indexreader = DirectoryReader.open(writer);
	      System.out.println("The Number of Docs in this Index is :" + indexreader.numDocs());
	      IndexSearcher indexsearcher = new IndexSearcher(indexreader);
	      //System.exit(1);
	      //QueryParser q = new QueryParser("city", analyzer).parse("Phoenix");
	      //TermQuery query = new TermQuery(new Term("city","Charlotte"));
	      Query q = new QueryParser("city", analyzer).parse("Phoenix");
	      //System.out.println("this is a termQuery" + q.getTerm());
	      //System.out.println(q.toString());
	      TopDocs x = indexsearcher.search(q, indexsearcher.count(q));
	      System.out.println("I retrieved that many docs from my query : "+x.scoreDocs.length);
	      
	      //MAX MIN MEAN CALCULATOR
	      
	      int max = -10;
	      int minimum = 1000000;
	      int mean = 0;
	      int review_count;
	      int iterator = 1;
	      
	      for (int i=0; i<x.scoreDocs.length; i++) {
	    	  //System.out.println(x.scoreDocs[i].doc);
	    	  Document doc = indexsearcher.doc(x.scoreDocs[i].doc);
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
	      
	      System.out.println("MAX : " + max + " MIN : " + minimum + " MEAN : " + mean);
	      System.out.println(x.totalHits.value);
	      System.out.println(indexsearcher.count(q));
	      writer.close();
	      Date end = new Date();
	      System.out.println(end.getTime() - start.getTime() + " total milliseconds");

	    } catch (IOException e) {
	      System.out.println(" caught a " + e.getClass() +
	       "\n with message: " + e.getMessage());
	    }
	}
	
	static void indexDocs(final IndexWriter writer, Path path) throws IOException {
	    if (Files.isDirectory(path)) {
	      Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	          try {
	            indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
	          } catch (IOException ignore) {
	            // don't index files that can't be read.
	          }
	          return FileVisitResult.CONTINUE;
	        }
	      });
	    } else {
	      indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
	    }
	}
	
	static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
	    try (InputStream stream = Files.newInputStream(file)) {
	    	// make a new, empty document
	        //System.out.println("at " + file);
	    	//FOREACH FILE IN THE DIRECTORY CREATE DOCUMENTS
	    	
	    	  	FileReader Efile=new FileReader(file.toString());
				BufferedReader br = new BufferedReader(Efile);
				String line;
				
			//BUSINESSDOCS FIELDS
				
				String name = null;
				String city = null;
				String categories  = null;
				int review_count;
				double stars;
				
			//REVIEWDOCS FIELDS
				
				//String review_id = null;
				
			//TIPDOCS FIELDS
				
				//String text = null;
				
			//SHARED & TIPDOCS & REVIEWDOCS FIELDS 
				
				String business_id = null;
				String text = null;
				
				
		while((line=br.readLine())!=null){
				Document doc = new Document();

	      // Add the path of the file as a field named "path".  Use a
	      // field that is indexed (i.e. searchable), but don't tokenize
	      // the field into separate words and don't index term frequency
	      // or positional information:
				
	      
			//JSONOBJECT
				
				JSONObject jsonObject = null;
				jsonObject = new JSONObject(line);
			
			//SHARED FIELD
				
				Field pathField = new StringField("path", file.toString(), Field.Store.YES);
				doc.add(pathField);
				
	      // Use a LongPoint that is indexed (i.e. efficiently filterable with
	      // PointRangeQuery).  This indexes to milli-second resolution, which
	      // is often too fine.  You could instead create a number based on
	      // year/month/day/hour/minutes/seconds, down the resolution you require.
	      // For example the long value 2011021714 would mean
	      // February 17, 2011, 2-3 PM.
			    //doc.add(new LongPoint("modified", lastModified));
			    //doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
				//System.out.println(doc.getFields());
	      
	            if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
	            	// New index, so we just add the document (no old document can be there):
	            	//System.out.println("adding " + file);
	            	if(file.getFileName().toString().equals("business.json")) {
						//System.out.println(jsonObject.get("categories").toString());
	            		name = (String) jsonObject.get("name");
					    doc.add(new TextField("name",name,  Field.Store.YES));

	            		business_id = (String) jsonObject.get("business_id");
					    doc.add(new TextField("business_id",business_id,  Field.Store.YES));
					    
						city = (String) jsonObject.get("city");
						doc.add(new TextField("city",city,  Field.Store.YES));
						
						//System.out.println(jsonObject.get("categories").toString() +" |||| "+ jsonObject.get("stars").toString());
						//System.exit(1);
						//if(jsonObject.get("categories") == null){
						//System.out.println(categories.isEmpty());
						categories = (String) jsonObject.get("categories").toString();
						//if(categories.isEmpty())
						//System.out.println(categories);
						
							//System.exit(1);
						if(!(categories.equals("null"))) {
							categories = (String) jsonObject.get("categories");
							categories = categories.replace(",", "");
							doc.add(new TextField("categories",categories,  Field.Store.YES));
						}
						else {
							doc.add(new TextField("categories","no category",  Field.Store.YES));
							//System.out.println("i got in with a false statement" + jsonObject.toString());
							//System.exit(1);
						}
						//}
						//else{
						//	doc.add(new TextField("categories","no category",  Field.Store.YES));
						//}
						/*if (!categories.isEmpty()) {
							doc.add(new TextField("categories",categories,  Field.Store.YES));
						}
						else {
							doc.add(new TextField("categories","no category",  Field.Store.YES));
						}*/
						stars = (double) jsonObject.getDouble("stars");
						doc.add(new DoublePoint("stars",stars));
					    
					    review_count = (int) jsonObject.get("review_count");
					    doc.add(new StoredField("review_count",review_count));
					    
					    //System.out.println("the review_count : " + review_count + " these are the fields: " + doc.getFields());
					    //System.exit(1);
					}
					//System.exit(1);
					else if(file.getFileName().toString().equals("review.json")) {
						
						text = (String) jsonObject.getString("text");
						doc.add(new TextField("text",text, Field.Store.YES));
						
						business_id = (String) jsonObject.get("business_id");
					    doc.add(new TextField("business_id",business_id,  Field.Store.YES));
					    
					    stars = (double) jsonObject.getDouble("stars");
					    doc.add(new DoublePoint("stars",stars));
					    //doc.add(new TextField("stars",stars,Field.Store.YES));
					}
					else {
						
						business_id =(String) jsonObject.getString("business_id");
						doc.add(new TextField("review_id",business_id, Field.Store.YES));
						
						text = (String) jsonObject.getString("text");
						doc.add(new TextField("text",text, Field.Store.YES));
					}
	            	
	            	writer.addDocument(doc);
	            	
	            } else {
	              // Existing index (an old copy of this document may have been indexed) so 
	              // we use updateDocument instead to replace the old one matching the exact 
	              // path, if present:
	            	System.out.println("updating " + file);
	            	writer.updateDocument(new Term("path", file.toString()), doc);
	            }
	            //System.out.println(br.readLine().isEmpty());
		}
		br.close();
	    } catch (JSONException e) {
			e.printStackTrace();
		}
	    //System.out.println("");
	}
}
