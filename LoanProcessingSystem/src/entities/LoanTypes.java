package entities;

public class LoanTypes {
	// properties
	private int id;
	private String name;
	private float interest_rate;
	
	// getters/setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getInterest_rate() {
		return interest_rate;
	}
	public void setInterest_rate(float interest_rate) {
		this.interest_rate = interest_rate;
	}
	
	// constructors
	public LoanTypes(int id, String name, float interest_rate) {
		super();
		this.id = id;
		this.name = name;
		this.interest_rate = interest_rate;
	}
	public LoanTypes() {
		super();
	}
}
