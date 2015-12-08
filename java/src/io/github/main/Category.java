package io.github.main;

import java.util.HashMap;
import java.util.Iterator;

import io.github.assistant.JsonParser;
import io.github.assistant.PositiveNegtiveParser;
import io.github.bean.SplitReviewBean;

import io.github.dao.SplitUnlabelReviewDAO;
import io.github.dao.UnlabelReviewDAO;

import com.mongodb.DBObject;
/**
 * in the class, the input is the reviewNumber, like 5th review, the number is 5.
 * we can get the catagory of the review
 * @author zhihan
 *
 */
public class Category {
	// ith review in the dataset
	private int reviewNumber;
	private SplitReviewBean targetBean; 
	
	public Category(int reviewNumber){
		this.reviewNumber = reviewNumber;
		this.targetBean = this.readBean();
		this.getSentiment();
		this.getJson();
	}
	
	
	/**
	 * read the mongo review to java bean
	 * @return
	 */
	public SplitReviewBean readBean(){
		SplitUnlabelReviewDAO dao = new SplitUnlabelReviewDAO();
		DBObject ob = dao.findByNumber(this.reviewNumber);
		System.out.println((String)ob.get("id"));
		return JsonParser.parseToSplitBean(ob);
	}
	
	/**
	 * calculate sentiment of each review based split reivew
	 */
	public void getSentiment(){
		PositiveNegtiveParser check = new PositiveNegtiveParser();
		HashMap words = this.targetBean.getReviews();
		Iterator it = words.keySet().iterator();
		while(it.hasNext()){
			String word = (String) it.next();
			if(check.isPositiveWord(word)){
				System.out.println("pos "+word);
				this.targetBean.setPosNum(this.targetBean.getPosNum()+(Integer)words.get(word));
			}
			
			if(check.isNegativeWord(word)){
				System.out.println("neg "+word);
				this.targetBean.setNegNum(this.targetBean.getNegNum()+(Integer)words.get(word));
			}
		}
		
		
		// get the category
		if(this.targetBean.getPosNum() >= this.targetBean.getNegNum() && this.targetBean.getPosNum()!=0){
			this.targetBean.setCategory("positive");
		}		
	}
	
	/**
	 * insert the category in the originial review
	 */
	public void getJson(){
		UnlabelReviewDAO dao = new UnlabelReviewDAO();
		DBObject ob = dao.findByID(this.targetBean.getId());
		ob.put("category",this.targetBean.getCategory());
		System.out.println(ob);
		dao.close();
		
	}
	

}
