package gatcha;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Test {
	public static void main(String[] args) {
		try {
			String a = "{\"name\":\"kyarl\",\"grade\":\"1\"}#{\"name\":\"peko\",\"grade\":\"1\"}";
			String[] arr = a.split("#");
			JSONParser jsonParser = new JSONParser();
			for(int i=0; i<arr.length; i++) {
				JSONObject jsonObject = (JSONObject) jsonParser.parse(arr[i]);
				System.out.println(jsonObject.get("name"));
				System.out.println(jsonObject.get("grade"));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
