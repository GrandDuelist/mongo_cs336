package io.github.dao;

import java.util.Random;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Manipulate SplitLabelReview Collection in mongo
 * @author zhihan
 *
 */
public class SplitUnlabelReviewDAO extends BaseConnection {
	
	public SplitUnlabelReviewDAO(){
	    this.connect(); 
		this.setDBAndCollection("cs336", "unlabel_review_after_splitting");
	}
	
	
	
	
	public  DBObject findByNumber(int number){
		DBCursor cur = this.currentCollection.find();
		while (cur.hasNext() && number>1)
		{
			number--;
			cur.next();
		}
		if(cur.hasNext()){
			return cur.next();
		}else{
			return null;
		}
	}
	
	
	
}
