package io.github.coding.test;

import com.mongodb.DBObject;

import io.github.assistant.JsonParser;
import io.github.dao.BaseConnection;
import io.github.dao.SplitUnlabelReviewDAO;

public class BaseConnectionTest {
	public static void main(String args[]){
	/*	BaseConnection bc = new BaseConnection();
		bc.connect();
		bc.showDBs();*/
		
		SplitUnlabelReviewDAO dao = new SplitUnlabelReviewDAO();
		DBObject ob = dao.findByNumber(5);
		System.out.println((String)ob.get("id"));
		JsonParser.parseToSplitBean(ob);
		/*dao.findReviewByNumber(5);*/
		dao.close();
	}
}

