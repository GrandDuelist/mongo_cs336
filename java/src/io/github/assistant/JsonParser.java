package io.github.assistant;

import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import io.github.bean.SplitReviewBean;
import io.github.dao.SplitUnlabelReviewDAO;
/**
 * parse json to bean
 * @author zhihan
 *
 */
public class JsonParser {
	public static SplitReviewBean parseToSplitBean(DBObject target){
		SplitReviewBean bean = new SplitReviewBean();
		bean.setId((String)target.get("id"));
		Object review = target.get("review");
		
		String reviewString = review.toString();
		try {
			JSONArray jsonArray	= new JSONArray(reviewString);
			for(int i=0;i<jsonArray.length();i++){
				
				JSONObject ob = jsonArray.getJSONObject(i);
				bean.getReviews().put(ob.get("word"),ob.get("count"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bean;
	}
}
