package mx.uvdroids.spacecards.model;

public class Achievement {
	public int id;
	public String name;
	public String description;
	public int status;
	public int res;
	public int dis_res;
	public Achievement(String name, String description, int status,
			int res, int dis_res) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
		this.res = res;
		this.dis_res = dis_res;
	}
	public Achievement(){
		
	}
}
