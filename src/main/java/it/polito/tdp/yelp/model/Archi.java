package it.polito.tdp.yelp.model;

import java.time.LocalDate;

public class Archi {
	
	private String r1;
	private LocalDate d1;
	private String r2;
	private LocalDate d2;
	private int peso;
	
	public Archi(String r1, LocalDate d1, String r2, LocalDate d2) {
		super();
		this.r1 = r1;
		this.d1 = d1;
		this.r2 = r2;
		this.d2 = d2;
	}

	public String getR1() {
		return r1;
	}

	public LocalDate getD1() {
		return d1;
	}

	public String getR2() {
		return r2;
	}

	public LocalDate getD2() {
		return d2;
	}

	public int getPeso() {
		return peso;
	}
	
	
	
	

}
