package domain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import base.Contact;
import base.ContactManager;
import base.FutureMeeting;
import base.Meeting;
import base.PastMeeting;

@SuppressWarnings("serial")
public class ContactManagerImpl implements ContactManager, Serializable {
	
	private final static String dataFileName = "contacts.txt";
	
	private Map<Integer, Contact> contacts;
	private Map<Integer, Meeting> meetings;
	
	public ContactManagerImpl()
	{
		this.contacts = new HashMap<Integer, Contact>();
		this.meetings = new HashMap<Integer, Meeting>();
		
		this.init();
	}
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewContact(String name, String notes) {
		ContactImpl c = new ContactImpl();
		c.name = name;
		c.notes = notes;		
		this.contacts.put(c.getId(), c);		
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		try
		{
			FileOutputStream fs = new FileOutputStream(dataFileName);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(this);
			os.close();
			fs.close();
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
	}	
	
	/**
	 * Dummy method to return all contacts for testing
	 * @return a set of all contacts saved contacts
	 */	
	public Set<Contact> getAllContacts()
	{
		return new HashSet<Contact>(this.contacts.values());
	}
	
	/**
	 *  Initialises the ContactManager by loading the data onto memory
	 *  If the file doesn't yet exist (and a IOException is thrown)
	 *  then we call flush() to create an empty file 
	 */
	private void init()
	{
		try
		{
			ContactManagerImpl cm = null;
			FileInputStream fs = new FileInputStream(dataFileName);
		    ObjectInputStream ois = new ObjectInputStream(fs);
		    
		    cm = (ContactManagerImpl) ois.readObject();
		    this.contacts = cm.contacts;
		    this.meetings = cm.meetings;
		    
		    ois.close();
		    fs.close();
		}
		catch (IOException i)
		{
			this.flush();
			this.init();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	  
		
	}

}
