import java.io.File;
import java.util.ArrayList;


public class Speaker {
	
	private String name;
	private String id;

	private File enrollSample;

	public Speaker(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public File getEnrollSample() {
		return enrollSample;
	}
	public void setEnrollSample(File enrollSample) {
		this.enrollSample = enrollSample;
	}


}
