package io.github.bean;

import java.io.Serializable;
import java.util.HashMap;

public class SplitReviewBean implements Serializable {
	private String id;
	private HashMap reviews = new HashMap();
	private String Category = "negative";
	private int posNum = 0;
	private int negNum = 0;
	
	
	
	public int getPosNum() {
		return posNum;
	}
	public void setPosNum(int posNum) {
		this.posNum = posNum;
	}
	public int getNegNum() {
		return negNum;
	}
	public void setNegNum(int negNum) {
		this.negNum = negNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashMap getReviews() {
		return reviews;
	}
	public void setReviews(HashMap reviews) {
		this.reviews = reviews;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	
	
	
	
}
