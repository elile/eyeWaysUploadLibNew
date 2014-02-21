package Objects;

public class Mall {
	
	private String id;
	private String dns;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dns) {
		this.dns = dns;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Mall(String id, String dns, String name) 
	{
		this.id = id;
		this.dns = dns;
		this.name = name;
	}
	public Mall() 
	{
	}
	
	

}
