package entities;

public class FineRate {
	private int id;
	private double lower_limit;
	private double higher_limit;
	private float rate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getLower_limit() {
		return lower_limit;
	}

	public void setLower_limit(double lower_limit) {
		this.lower_limit = lower_limit;
	}

	public double getHigher_limit() {
		return higher_limit;
	}

	public void setHigher_limit(double higher_limit) {
		this.higher_limit = higher_limit;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public FineRate(int id, double lower_limit, double higher_limit, float rate) {
		super();
		this.id = id;
		this.lower_limit = lower_limit;
		this.higher_limit = higher_limit;
		this.rate = rate;
	}

	public FineRate() {
		super();
	}
}
