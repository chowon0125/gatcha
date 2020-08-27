package gatcha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CharDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public CharDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");//jdbc/oracle
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String draw() {
		Random random = new Random();
		int select = random.nextInt(1000);
		if(select<25) {
			return "3";
		}else if(select<125) {
			return "2";
		}else {
			return "1";
		}
	}
	
	public String tenthDraw() {
		Random random = new Random();
		int select = random.nextInt(1000);
		if(select<25) {
			return "3";
		}else {
			return "2";
		}
	}
	
	public List<CharVO> listChar(String chaGrade) {
		List<CharVO> list = new ArrayList<>();
		try {
			con = dataFactory.getConnection();
			String query = "select * from characters WHERE grade='"+chaGrade+"' AND pickable='1' ";
			query += "union all ";
			query += "select * from characters WHERE grade='"+chaGrade+"' AND pickable='1' AND pickup='1' ";
			System.out.println("PrepareStatement : " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String grade = rs.getString("grade");
				String imgPath = rs.getString("imgpath");
				String pickUp = rs.getString("pickup");
				CharVO c = new CharVO();
				c.setName(name);
				c.setGrade(grade);
				c.setImgPath(imgPath);
				c.setPickUp(pickUp);
				list.add(c);
			}
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i).getName()+" / "+list.get(i).getPickUp());
			}
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String resultJSON(String comporator) {
		String result = "{\"character\":[";
		try {
			con = dataFactory.getConnection();
			String query = "";
			switch(comporator) {
				case "recent" : query += "select * from gatcharesult "; break;
				case "grade" : query += "select * from gatcharesult ORDER BY grade DESC "; break;
				case "pickup" : query += "select * from gatcharesult where pickup='1' "; break;
			}
			System.out.println("PrepareStatement : " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String grade = rs.getString("grade");
				String path = rs.getString("imgpath");
				String pickUp = rs.getString("pickup");
				result += "{\"name\":\""+name+"\",\"grade\":\""+grade+"\",\"imgpath\":\""+path+"\",\"pickup\":\""+pickUp+"\"},";
			}
			result = result.substring(0, result.length() - 1);
			result += "]}";
			if(result.equals("{\"character\":]}")) {
				result = "no_data";
			}
			System.out.println(result);
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public CharVO select(List<CharVO> list) {
		Random random = new Random();
		int select = random.nextInt(list.size());
		return list.get(select);
	}
	
	public void commit(List<CharVO> list) {
		try {
			con = dataFactory.getConnection();
			for(int i=0; i<10; i++) {
				String name = list.get(i).getName();
				String grade = list.get(i).getGrade();
				String imgPath = list.get(i).getImgPath();
				String pickUp = list.get(i).getPickUp();
				String query = "insert into gatcharesult";
				query += " (name,grade,imgpath,pickup)";
				query += " values(?,?,?,?)";
				System.out.println("prepareStatement : " + query);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, grade);
				pstmt.setString(3, imgPath);
				pstmt.setString(4, pickUp);
				pstmt.executeUpdate();
			}
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reset() {
		try {
			con = dataFactory.getConnection();
			String query = "truncate table gatcharesult";
			System.out.println("prepareStatement:" + query);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String count() {
		String result = "";
		String suk = "", grade3 = "", grade2 = "", grade1 = "";
		try {
			con = dataFactory.getConnection();
			String query = "select\r\n" + 
					"(\r\n" + 
					"    select decode(sum(y.count2 * jw),null,'0',sum(y.count2 * jw)) as result\r\n" + 
					"    from\r\n" + 
					"    (\r\n" + 
					"        select x.name, (x.count-1) as count2, decode(x.grade,'3','50','2','10','1','1') as jw\r\n" + 
					"        from\r\n" + 
					"        (\r\n" + 
					"            select name, grade, count(*) as count\r\n" + 
					"            from gatcharesult\r\n" + 
					"            group by name, grade \r\n" + 
					"            having count(*) > 1\r\n" + 
					"        ) x\r\n" + 
					"    )y\r\n" + 
					")as suk,\r\n" + 
					"(select count(*) from gatcharesult where grade='3') as grade3,\r\n" + 
					"(select count(*) from gatcharesult where grade='2') as grade2,\r\n" + 
					"(select count(*) from gatcharesult where grade='1') as grade1\r\n" + 
					"from dual ";
			System.out.println("prepareStatement:" + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			suk = rs.getString("suk");
			grade3 = rs.getString("grade3");
			grade2 = rs.getString("grade2");
			grade1 = rs.getString("grade1");
			int total = Integer.parseInt(grade3) + Integer.parseInt(grade2) + Integer.parseInt(grade1);
			if(total == 0) {
				result += "no_data";
			}else {
				result += "{\"suk\":\""+suk+"\",\"grade3\":\""+grade3+"\",\"grade2\":\""+grade2+"\",\"grade1\":\""+grade1+"\"}";
			}
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String listJSON(String comporator) {
		String result = "{\"character\":[";
		try {
			con = dataFactory.getConnection();
			String query = "";
			query += "select * from characters ";
			switch(comporator) {
			case "grade" : query += "order by grade desc, name asc "; break;
			case "abc" : query += "order by name asc "; break;
			case "pickup" : query += "order by pickup desc, grade desc, name asc "; break;
			case "pickable" : query += "order by pickable, grade desc, name asc "; break;
			}
			System.out.println("PrepareStatement : " + query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String grade = rs.getString("grade");
				String path = rs.getString("imgpath");
				String pickUp = rs.getString("pickup");
				String pickable = rs.getString("pickable");
				result += "{\"name\":\""+name+"\",\"grade\":\""+grade+"\",\"imgpath\":\""+path+"\",\"pickup\":\""+pickUp+"\",\"pickable\":\""+pickable+"\"},";
			}
			result = result.substring(0, result.length() - 1);
			result += "]}";
			if(result.equals("{\"character\":]}")) {
				result = "no_data";
			}
			System.out.println(result);
			rs.close();
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void update(String jsondata) {
		try {
			String[] jsonArr = jsondata.split("#");
			JSONParser jsonParser = new JSONParser();
			con = dataFactory.getConnection();
			for(int i=0; i<jsonArr.length; i++) {
				JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonArr[i]);
				String name = (String)jsonObject.get("name");
				String grade = (String)jsonObject.get("grade");
				String imgpath = (String)jsonObject.get("imgpath");
				String pickup = (String)jsonObject.get("pickup");
				String pickable = (String)jsonObject.get("pickable");
				String query = "update characters set ";
				query += "pickup='"+pickup+"', ";
				query += "pickable='"+pickable+"' ";
				query += "where name='"+name+"' ";
				query += "and grade='"+grade+"' ";
				query += "and imgpath='"+imgpath+"' ";
				System.out.println("prepareStatement : " + query);
				pstmt = con.prepareStatement(query);
				pstmt.executeQuery();
			}
			pstmt.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
