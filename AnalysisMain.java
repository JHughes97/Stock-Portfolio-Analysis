/**
 * @(#)AnalysisMain.java
 * This class reads in the stock data, creates necessary stock and
 * volatility objects, and performs some calculations.
 * It includes the main method and a method for calculating the
 * standard deviation of each stock
 * @author Jack Hughes
 * @version 1.3 2016/5/29
 */

import java.util.*;

public class AnalysisMain {

	/**
	 * Main method asks the user to enter max amount of money to invest
	 * then displays to console the amount of stocks to buy in each company,
	 * the number of companys that are invested in, the amount invested,
	 * and the volatility of the stocks invested in.
	 * Stocks are chosen based on volatility
	 * @param args
	 */
    public static void main(String[] args){
    	//Ask user to enter maximum investment amount
    	Scanner scan=new Scanner(System.in);
    	System.out.println("Enter amount of money:");
    	int money=scan.nextInt();
    	System.out.println("Calculating...");

		//Read in stock values
    	FileIO io=new FileIO();
		String[] original=io.load("StockData.txt");
		int numrows=original.length;
		int numcols=original[0].split("\t").length;
		double[][] percentages=new double[numrows][numcols];

		//Create 2d array of stock values
		for(int i=1;i<numrows;i++){
			for(int j=1;j<numcols;j++){
				percentages[i][j]=Double.parseDouble(original[i].split("\t")[j]);
			}
		}

		//Create array of stock company names
		String[] names=original[0].split("\t");

		//Create array of company prices
		String[] tempPrices=io.load("Costs.txt");
    	for(int i=0;i<tempPrices.length;i++){
    		String temp=tempPrices[i].replaceAll("[^x0-9]","");
    		tempPrices[i]=temp;
    	}
    	int[] stockPrices=new int[numcols];
    	for(int i=1;i<numcols;i++){
    		stockPrices[i]=Integer.parseInt(tempPrices[i-1]);
    	}

		//Create array of stock standard deviations
    	double[] standardDeviations=deviations(percentages);

    	//Create array of stock objects and sort based on standard deviation
    	Stock[] companies=new Stock[numcols];
    	for(int i=1;i<numcols;i++){
    		double[] values=new double[numrows];
    		for(int j=1;j<numrows;j++){
    			values[j]=percentages[j][i];
    		}
    		companies[i]=new Stock(i,names[i],stockPrices[i],standardDeviations[i]);
    	}
    	Arrays.sort(companies,1,companies.length);

		//Create volatility object
    	Volatility volatility=new Volatility(percentages,money,names,stockPrices);

		/* While it is possible to decrease volatility by adding stocks,
		 * for each stock, increment until the stock no longer lowers volatility
		 */
    	boolean go=true;
    	while(go){
    		go=false;
    		for(int i=1;i<numcols;i++){
    			while(volatility.test(companies[i])){
    				go=true;
    				volatility.add(companies[i]);
    			}
    		}
    	}

		// Print company names, stocks, investment amount, and volatility
    	volatility.printNames();
    	System.out.println("\n___________________________________________\n");
    	volatility.printAmounts();
    	System.out.println("\n___________________________________________\n");
    	System.out.println("Number of companies :\t"+volatility.numCompanies());
    	System.out.println("\n___________________________________________\n");
    	System.out.println("Investment Amount :\t"+volatility.investmentAmount());
    	System.out.println("\n___________________________________________\n");
    	System.out.println("Volatility :\t"+volatility.value());
    }

	/**
	 * Calculates the standard deviations of each stock
	 * @param 2d array of stock values
	 * @return array of standard deviations
	 */
	public static double[] deviations(double[][] values){
		double[] deviations=new double[values[0].length];
		//Loop through each column
		for(int i=1;i<values[0].length;i++){
			//Calculate average of column
			double tot=0.0;
			for(int j=1;j<values.length;j++){
				tot+=values[j][i];
			}
			double avg=tot/(deviations.length-1);
			//Calculate standard deviation and put it in array
			double total=0;
			for(int j=1;j<values.length;j++){
				total+=(values[j][i]-avg)*(values[j][i]-avg);
			}
			double variance=total/(values.length-1);
			deviations[i]=Math.sqrt(variance);
		}
		//Return array
		return deviations;
	}

}