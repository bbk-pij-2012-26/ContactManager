import base.Contact;
import domain.ContactManagerImpl;

public class ContactManagerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		ContactManagerImpl cm = new ContactManagerImpl();		
//	    cm.addNewContact("Contact #1", "Notes #1");
//	    cm.addNewContact("Contact #2", "Notes #2");	 
//	    cm.addNewContact("Contact #3", "Notes #3");	    
//	    cm.flush();
	    
	    ContactManagerImpl cm2 = new ContactManagerImpl();
	    
	    for (Contact c : cm2.getAllContacts())
		{
			System.out.println(c.getName());
		}    
	      
	}

}
