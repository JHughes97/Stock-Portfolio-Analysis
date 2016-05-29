/**
 * @(#)Stock.java
 * This class holds data relating to a stock
 * It includes a method for setting the values
 * It implements a compare method that compares stocks based on standard deviation
 * It includes 4 methods for returning the stock's values
 * @author Jack Hughes
 * @version 1.2 2016/5/29
 */

import java.util.*;

public class Stock implements Comparable<Stock>{
	/* id - each stock has a uniqued id
	 * name - stock company name
	 * price - stock price
	 * standardDeviation - standard deviation of stock's values
	 */
	private int id;
	private String name;
	private int price;
	private double standardDeviation;

	/**
	 * Sets the values of the stock
	 * @param id name price standardDeviation
	 */
	public Stock(int id,String name,int price,double standardDeviation){
		this.id=id;
		this.name=name;
		this.price=price;
		this.standardDeviation=standardDeviation;
	}

	/**
	 * Compares stocks based on standard deviation
	 * @param stock
	 * @return 1 -1 0
	 */
	public int compareTo(Stock two){
		if(this.standardDeviation > two.standardDeviation())
			return 1;
		else if(this.standardDeviation < two.standardDeviation())
			return -1;
		else
			return 0;
	}

	/**
	 * id getter method
	 * @return id
	 */
	public int id(){
		return this.id;
	}

	/**
	 * name getter method
	 * @return name
	 */
	public String name(){
		return this.name;
	}

	/**
	 * price getter method
	 * @return price
	 */
	public int price(){
		return this.price;
	}

	/**
	 * standard deviation getter method
	 * @return standardDeviation
	 */
	public double standardDeviation(){
		return this.standardDeviation;
	}
}