package io.github.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * DAO class, manipulate the unlabel review in mongoDB
 * @author zhihan
 *
 */
public class UnlabelReviewDAO extends BaseConnection{

	public UnlabelReviewDAO(){
		   this.connect(); 
		   this.setDBAndCollection("cs336", "unlabel_review");
		}
		
		
		public  DBObject findByID(String ID){
			DBCursor cur = this.currentCollection.find(new BasicDBObject("id",ID));
		
			if(cur.hasNext()){
				return cur.next();
			}else{
				return null;
			}
		}
	
	

}
