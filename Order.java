package anakthsh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;



public class Order {
	
	private static File file;
	private static String city;
	private static String id;
	private static String text;
	private static JSONObject jsonObject = null;
	
	private Order() throws IOException, JSONException {
		String businessPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\business.json";
		
		HashSet<String> lines = new HashSet();
		HashSet<String> storesIds = new HashSet();
		File readerFile = new File(businessPath);
		File writerFile = new File("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedBusiness.json");
		File tipsFile = new File("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedTips.json");
		File reviewsFile = new File("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\review.json");
		File orderedFile = new File("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedFile.json");
		tipsFile.createNewFile();
		orderedFile.createNewFile();
		writerFile.createNewFile();
//		System.exit(1);
		Path file = Paths.get("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedBusiness.json");
		Path tipsPathFile = Paths.get("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedTips.json");
		Path orderedPathFile = Paths.get("C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\orderedFile.json");
//		Date start = new Date();
		FileReader reader = new FileReader(readerFile);
		BufferedReader buffReader = new BufferedReader(reader);
		String line ;
//		System.out.print("this is a test with \n a new line");
//		System.exit(1);
		
		
		while( (line = buffReader.readLine() ) != null) {
			jsonObject = new JSONObject(line);
			city = (String) jsonObject.get("city");
			id = (String) jsonObject.getString("business_id");
			if (city.equals("Phoenix")) {
				lines.add(line);
				storesIds.add(id);
			}
		
		}
		Files.write(file,lines,StandardCharsets.UTF_8);
		lines.clear();
		readerFile = null;
		
		HashMap<String ,String> reviews = new HashMap<String, String>(); 
		String tipPath = "C:\\Users\\ilias\\Desktop\\stefanos\\anakthsh\\Data3\\tip.json";
		File tipFile = new File(tipPath);
		buffReader = new BufferedReader(new FileReader(tipFile));
//		int i = 0;
		
		while( (line = buffReader.readLine() ) != null) {
			jsonObject = new JSONObject(line);
//			city = (String) jsonObject.get("city");
			id = (String) jsonObject.getString("business_id");
			text = jsonObject.getString("text");
//			text.replace("\n", " ");
			if (storesIds.contains(id)) {
//				System.out.println(reviews.containsKey(id));
				if (reviews.containsKey(id)) {
					
					String temp = reviews.get(id);
					temp = temp + text;
//					temp.trim();
					reviews.replace(id, temp);
//					System.out.println(id + reviews.get(id));
//					if(i == 2) {
//						System.exit(1);
//					}
//					i = i + 1;
				}
				else {
					reviews.put(id,text);
				}
//				lines.add(line);
//				storesIds.add(id);
			}
		
		}
		
		buffReader = new BufferedReader(new FileReader(reviewsFile));
		
		while( (line = buffReader.readLine() ) != null) {
			jsonObject = new JSONObject(line);
//			city = (String) jsonObject.get("city");
			id = (String) jsonObject.getString("business_id");
			text = jsonObject.getString("text");
//			text.replace("\n", " ");
			if (storesIds.contains(id)) {
//				System.out.println(reviews.containsKey(id));
				if (reviews.containsKey(id)) {
					
					String temp = reviews.get(id);
					temp = temp + text;
//					temp.trim();
					reviews.replace(id, temp);
//					System.out.println(id + reviews.get(id));
//					if(i == 2) {
//						System.exit(1);
//					}
//					i = i + 1;
				}
				else {
					reviews.put(id,text);
				}
//				lines.add(line);
//				storesIds.add(id);
			}
		
		}
		storesIds.clear();
		
		for(String i : reviews.keySet()) {
			
			text = reviews.get(i).replace("\n", "");
			text = text.replace("\r", "");
			text = text.replace("\"", "");
			text = text.replace("\\", "");
			reviews.replace(i, text);
			//lines.add("{\"business_id\":" + "\"" + i + "\"" + ",\"text\":" + "\"" + text + "\"}" );
		}
		
//		Files.write(tipsPathFile,lines,StandardCharsets.UTF_8);
		
		reader = new FileReader(writerFile);
		buffReader = new BufferedReader(reader);
		
		int i = 1;
		while( (line = buffReader.readLine() ) != null) {
			jsonObject = new JSONObject(line);
//			city = (String) jsonObject.get("city");
			id = (String) jsonObject.getString("business_id");
			String temptext = reviews.get(id);
			line = line.substring(0, line.length() - 1);
			line = line + ",\"text\":" + "\"" + temptext + "\"}" ;
			lines.add(line);
//			System.out.println(line);
//			System.exit(1);
//			if (city.equals("Phoenix")) {
//				lines.add(line);
//				storesIds.add(id);
//			}
//			System.out.println(i + id);
//			i++;
		}
		Files.write(orderedPathFile,lines,StandardCharsets.UTF_8);
		/*String tempText = "";
		for (String i : reviews.keySet()) {
			
		}*/
//		System.out.println(line.isEmpty());
//		System.out.println(line);
//		jsonObject = new JSONObject(line);
//		city = (String) jsonObject.get("city");
//		System.out.println(city);
	}
	

	public static void main(String args[]) throws IOException, JSONException {
		/*String test = "this is a test with \n a new line";
		test = test.substring(0, test.length() - 1);
		System.out.print(test);
		System.exit(1);*/
		
		Order order = new Order();
	}

}