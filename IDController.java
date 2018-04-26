import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class IDController {
	
	private ArrayList<Speaker> speakers;
	private SpeakerID speakerID;
	private Speaker peter;
	private Speaker benji;
	public static void main(String[] args) throws InterruptedException{
		IDController cont = new IDController();
	}
	public IDController() {
		
		this.speakerID = new SpeakerID();
		this.speakers = new ArrayList<Speaker>();
		
	}
	
	
	
	public ArrayList<Speaker> getSpeakers() {
		return speakers;
	}
	public void setSpeakers(ArrayList<Speaker> speakers) {
		this.speakers = speakers;
	}
	public SpeakerID getSpeakerID() {
		return speakerID;
	}
	public void setSpeakerID(SpeakerID speakerID) {
		this.speakerID = speakerID;
	}
	public void enrollSpeaker(Speaker speaker) throws InterruptedException{
		 String op_id= this.speakerID.enrollUser(speaker.getId(), speaker.getEnrollSample());
		 TimeUnit.SECONDS.sleep(5);
		 System.out.println(this.speakerID.checkOperation(op_id));
	}

	public void createProfile(Speaker speaker){
		String speakerID= this.speakerID.createProfile();
		speaker.setId(speakerID);
		
	}

	public String  identifyUser(File sample, ArrayList<Speaker> speakers) throws InterruptedException{
		String list = "";
		int i = 0;
		for(Speaker speaker: speakers){
			list= list +  speaker.getId() +",";
		}	
		String op_id= this.speakerID.idenitfy(list, sample);
		TimeUnit.SECONDS.sleep(5);
		return this.speakerID.checkOperation(op_id);
	}
	public String checkUser(Speaker speaker){
		return this.speakerID.checkUser(speaker.getId());
	}


}
