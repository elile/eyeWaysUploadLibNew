package Objects;

public class store 
{
	
	private String id;
	private String picture;
	private String name;
	private String coordinate;
	private String extra;


	public store(String id, String name, String picture, String coordinate,
			String extra) {

		this.id = id;
		this.name = name;
		this.picture = picture;
		this.coordinate = coordinate;
		this.extra = extra;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	

}
