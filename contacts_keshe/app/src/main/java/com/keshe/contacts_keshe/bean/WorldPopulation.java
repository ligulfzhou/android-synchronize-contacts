package com.keshe.contacts_keshe.bean;

public class WorldPopulation {
	private String rank;
	private String country;
	private String population;
	private int flag;

	public WorldPopulation(int flag, String rank, String country,
						   String population) {
		this.rank = rank;
		this.country = country;
		this.population = population;
		this.flag = flag;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}