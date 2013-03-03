import java.util.Calendar;

import domain.ContactManagerImpl;
import base.ContactManager;

public class ContactManagerMain {
	private final static String DATAFILENAME = "contacts.txt";
	
	/**
	 * The main method of the program
	 * @param args
	 */
	public static void main(String[] args) {
		
		// No UI implemented
		// Assumption is that someone else would implement this 
		// as per Sergio's comment on class forum 
		
		// just implemented a simple program which creates 
		// a new FutureMeeting and a new PastMeeting and saves it to a file
		
		ContactManager cm = new ContactManagerImpl(DATAFILENAME);
		((ContactManagerImpl)cm).erase(); //clear existing file
		
		cm.addNewContact("Contact 1", "notes for contact 1");
		cm.addNewContact("Contact 2", "notes for contact 2");
		cm.addNewContact("Contact 3", "notes for contact 3");
		cm.addNewContact("Contact 4", "notes for contact 4");
		cm.addNewContact("Contact 5", "notes for contact 5");
		
		//Add a future meeting
		Calendar futureDate = Calendar.getInstance();
		futureDate.add(Calendar.MONTH, 2);		
		cm.addFutureMeeting(cm.getContacts(), futureDate);
				
		//Add a past meeting
		Calendar pastDate = Calendar.getInstance();
		pastDate.add(Calendar.MONTH, -2);		
		cm.addNewPastMeeting(cm.getContacts(), pastDate, "notes for past date");
		
		cm.flush(); //save file
	}

}
