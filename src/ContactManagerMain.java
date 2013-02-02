import base.Contact;
import domain.ContactManagerImpl;

public class ContactManagerMain {
	private final static String DATAFILENAME = "contacts.txt";
	
	/**
	 * The main method of the program
	 * @param args
	 */
	public static void main(String[] args) {
	    ContactManagerImpl cm = new ContactManagerImpl(DATAFILENAME);
	    
	    for (Contact c : cm.getAllContacts())
	    {
	    	System.out.println(c.getId() + ":" + c.getName());
	    }
	}

}
