package entities;

public class Position {
	private int id;
	private String name;
	private int authority_level;

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

	public int getAuthority_level() {
		return authority_level;
	}

	public void setAuthority_level(int authority_level) {
		this.authority_level = authority_level;
	}

	public Position(int id, String name, int authority_level) {
		super();
		this.id = id;
		this.name = name;
		this.authority_level = authority_level;
	}

	public Position() {
	}

}
