package Objects;

public class eyeWaysLocation 
{
	private String mx;
	private String my;
	private String floor;

	@Override
	public String toString() {
		return "eyeWaysLocation [mx=" + mx + ", my=" + my + ", floor=" + floor
				+ "]";
	}

	public eyeWaysLocation(String mx, String my, String floor) 
	{
		this.mx = mx;
		this.my = my;
		this.floor = floor;
	}

	public eyeWaysLocation() {
		this.mx = "";
		this.my = "";
		this.floor = "";
	}

	public String getMx() {
		return mx;
	}
	public void setMx(String mx) {
		this.mx = mx;
	}
	public String getMy() {
		return my;
	}
	public void setMy(String my) {
		this.my = my;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}	
}
