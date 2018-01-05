package com.trentanof.software.sciabalada;

import android.widget.TextView;

public class Player {
	private Integer score = 0;
	private String name = "";
	private String pictureName = "";
	private Integer rank = 0;
	private Integer reenter = 0;
	private Integer games = 0;
	private Boolean isOut = true;  
	
	public Integer getScore(){
		return score;
	}
	
	public void setScore(Integer score){
		this.score=score;
	}

	public Integer getRank(){
		return rank;
	}
	
	public void setRank(Integer rank){
		this.rank=rank;
	}

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getPictureName(){
		return pictureName;
	}
	
	public void setPictureName(String pictureName){
		this.pictureName=pictureName;
	}

	public Integer getGames(){
		return games;
	}
	
	public void setGames(Integer games){
		this.games=games;
	}

	public Integer getReenter(){
		return reenter;
	}
	
	public void setReenter(Integer reenter){
		this.reenter=reenter;
	}

	public Boolean getIsOut(){
		return isOut;
	}
	
	public void setIsOut(Boolean isOut){
		this.isOut=isOut;
	}

	public void init(String kind, String attribs){
			if (kind.equals("new")){
				this.setName(attribs);
				this.setPictureName(attribs.toLowerCase()+"1");
				this.setIsOut(false);
			}else{
				String[] attrib = attribs.split(",");
				this.setName(attrib[0]);
				this.setPictureName(attrib[0].toLowerCase()+"1");
				this.setScore(Integer.parseInt(attrib[1]));
				this.setIsOut(Boolean.valueOf(attrib[2]));	
				this.setGames(Integer.parseInt(attrib[3]));
				this.setReenter(Integer.parseInt(attrib[4]));
			}
	}

	
	@Override
	public String toString() {
	    return this.name;
	}
	
	public Player(Integer score, String name, Integer rank){
		this.setScore(score);
		this.setName(name);
		this.setRank(rank);
	}

	public Player(Integer score, String name, Integer rank,boolean isOut){
		this.setScore(score);
		this.setName(name);
		this.setRank(rank);
		this.setIsOut(false);
	}

}
