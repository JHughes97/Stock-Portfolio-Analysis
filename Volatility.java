/**
 * @(#)Volatility.java
 * This class performs calculations on stocks
 * It includes 9 methods for:
 * setting values
 * testing a stock
 * adding a stock
 * getting the volatility value
 * returning volatility value
 * printing stock names and amounts
 * printing stocks amounts seperated by tabs
 * getting the number of unique stocks invested in
 * getting the investment amount
 * @author Jack Hughes
 * @version 1.1 2016/5/29
 */


public class Volatility {

	/*
	 * percentages - 2d array containing stock values
	 * names - array containing stock names
	 * prices - array containing stock prices
	 * stocks - array containg number of each stock to invest in
	 * value - volatility value
	 * numStocks - number of unique stocks invested in
	 * maxMoney - maximum amount that can be invested in
	 * totalMoney - total amount used
	 */
	private double[][] percentages;
	private String[] names;
	private int[] prices;
	private int[] stocks;
	private double value;
	private int numStocks;
	private int maxMoney;
	private int totalMoney;

	/**
	 * Initializes object
	 * @param inputValues money names prices
	 */
    public Volatility(double[][] inputValues,int money,String[] names,int[] prices){
    	this.percentages=inputValues;
    	this.stocks=new int[inputValues[0].length];
    	for(int i=0;i<stocks.length;i++)
    		this.stocks[i]=0;
    	this.names=names;
    	this.prices=prices;
    	this.value=1000000.0;
    	this.numStocks=0;
    	this.maxMoney=money;
    }

	/**
	 * tests a stock
	 * @param stock
	 * @return true false
	 */
    public boolean test(Stock in){
    	double[] change=new double[percentages.length];
    	for(int i=0;i<change.length;i++)
    		change[i]=0.0;

    	for(int i=1;i<change.length;i++){
    		for(int j=1;j<percentages[0].length;j++){
    			if(j==in.id())
    				change[i]+=(percentages[i][j])*(prices[j])*(stocks[j]+1);
    			else
    				change[i]+=(percentages[i][j])*(prices[j])*(stocks[j]);
    		}

    		double div=0.0;
    		for(int j=1;j<percentages[0].length;j++){
    			if(j==in.id())
    				div+=(prices[j])*(stocks[j]+1);
    			else
    				div+=(prices[j])*(stocks[j]);
    		}

    		change[i]=change[i]/div;
    	}


    	double tot=0.0;
    	for(int i=1;i<change.length;i++)
    		tot+=change[i];
    	double avg=tot/(change.length-1);

    	double total=0.0;
    	for(int i=1;i<change.length;i++)
    		total+=(change[i]-avg)*(change[i]-avg);

    	double variance=total/(change.length-1);
    	double standardDeviation=Math.sqrt(variance);

    	if((this.numStocks==0) || ((standardDeviation<this.value) && (in.price()+this.totalMoney<this.maxMoney)))
    		return true;
    	else
    		return false;
    }

	/**
	 * Add a stock to the portfolio
	 * @param stock
	 */
    public void add(Stock in){
		this.stocks[in.id()]++;
		this.totalMoney+=in.price();
		this.numStocks++;
		setValue();
    }

	/**
	 * Updates the volatility value
	 */
    private void setValue(){
    	double[] change=new double[percentages.length];
    	for(int i=0;i<change.length;i++)
    		change[i]=0.0;

    	for(int i=1;i<change.length;i++){
    		for(int j=1;j<percentages[0].length;j++){
    			change[i]+=(this.percentages[i][j])*(this.prices[j])*(this.stocks[j]);
    		}

    		double div=0.0;
    		for(int j=1;j<percentages[0].length;j++){
    			div+=(this.prices[j])*(this.stocks[j]);
    		}

    		change[i]=change[i]/div;
    	}


    	double tot=0.0;
    	for(int i=1;i<change.length;i++)
    		tot+=change[i];
    	double avg=tot/(change.length-1);

    	double total=0.0;
    	for(int i=1;i<change.length;i++)
    		total+=(change[i]-avg)*(change[i]-avg);

    	double variance=total/(change.length-1);
    	double standardDeviation=Math.sqrt(variance);

    	this.value=standardDeviation;
    }

	/**
	 * Volatility value getter method
	 */
    public double value(){
    	return this.value;
    }

	/**
	 * Prints stock names and amount to buy
	 */
	public void printNames(){
		int scaleUp=this.maxMoney/this.totalMoney;
		for(int i=1;i<names.length;i++){
			System.out.println(this.names[i]+"\t:\t"+(this.stocks[i]*scaleUp));
		}
	}

	/**
	 * Prints amounts of each stock to buy seperated by tabs
	 */
	public void printAmounts(){
		int scaleUp=this.maxMoney/this.totalMoney;
		System.out.print(this.stocks[1]*scaleUp);
		for(int i=2;i<stocks.length;i++)
			System.out.print("\t"+(this.stocks[i]*scaleUp));
	}

	/**
	 * numStocks getter method
	 */
	public int numCompanies(){
		return this.numStocks;
	}

	/**
	 * Returns amount invested
	 * @return totalMoney
	 */
	public int investmentAmount(){
		int scaleUp=this.maxMoney/this.totalMoney;
		return this.totalMoney*scaleUp;
	}
}