package domain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import base.Contact;
import base.ContactManager;
import base.FutureMeeting;
import base.Meeting;
import base.PastMeeting;

@SuppressWarnings("serial")
public class ContactManagerImpl implements ContactManager, Serializable {
	
	private String dataFileName;
	
	private SortedMap<Integer, Contact> contacts;
	private SortedMap<Integer, Meeting> meetings;
	
	public ContactManagerImpl(String fileName)
	{
		this.dataFileName = fileName;
		this.contacts = new TreeMap<Integer, Contact>();
		this.meetings = new TreeMap<Integer, Meeting>();
		
		this.init();
	}
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (date.before(Calendar.getInstance()))
		{
			throw new IllegalArgumentException();
		}
		
		for (Contact c : contacts)
		{
			if (!this.contacts.containsKey(c.getId()))
			{
				throw new IllegalArgumentException();
			}
		}
		
		FutureMeeting fm = new FutureMeetingImpl(contacts, date);
		this.meetings.put(fm.getId(), fm);
		return fm.getId();
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		PastMeeting pm = (PastMeeting)this.meetings.get(id);
		if (Calendar.getInstance().before(pm.getDate()))
		{
			throw new IllegalArgumentException();
		}
		
		return pm;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		FutureMeeting fm = (FutureMeeting)this.meetings.get(id);
		if (fm.getDate().before(Calendar.getInstance()))
		{
			throw new IllegalArgumentException();
		}
		
		return fm;
	}

	@Override
	public Meeting getMeeting(int id) {
		return (Meeting)this.meetings.get(id);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (!this.contacts.containsKey(contact.getId()))
		{
			throw new IllegalArgumentException();
		}
		
		List<Meeting> meetings = new LinkedList<Meeting>();
		
		for (Meeting m : this.meetings.values()) {
			if (Calendar.getInstance().before(m.getDate())) {
				meetings.add((Meeting)m);
			}
		}
		
		return meetings;
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
		if (!this.meetings.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		if (text == null || text.length() == 0) {
			throw new NullPointerException();
		}
			
		PastMeetingImpl m = (PastMeetingImpl)this.meetings.get(id);
		
		if (Calendar.getInstance().before(m.getDate())) {
			throw new IllegalStateException();
		}
		
		m.addNotes(text);
	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || name.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		else if (notes == null || notes.isEmpty())
		{
			throw new IllegalArgumentException();
		}
		
		ContactImpl c = new ContactImpl(name);
		c.notes = notes;
		this.contacts.put(c.getId(), c);		
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> contacts = new TreeSet<Contact>(new ContactComparator());
		
		if (ids.length == 0) {
			contacts.addAll(this.contacts.values());
		}
		else {
			for (int id : ids) {
				if (this.contacts.containsKey(id)) {
					contacts.add((ContactImpl)this.contacts.get(ids));
				}
				else
				{
					throw new IllegalArgumentException();
				}
			}
		}
		
		return contacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		//TODO: find a better implementation for this
		if (name == null || name.isEmpty())
		{
			throw new NullPointerException();
		}
		
		Set<Contact> contacts = new TreeSet<Contact>(new ContactComparator());
		for (Contact c : this.contacts.values())
		{
			if (c.getName().toUpperCase().contains(name.toUpperCase()))
			{
				contacts.add(c);
			}
		}
		
		return contacts;
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
	 * Removes all contacts and meetings information
	 * @return
	 */
	public void erase()
	{
		this.contacts.clear();
		this.meetings.clear();
	}
	
	/**
	 * Checks whether there are any records in the current instance
	 * @return
	 */
	public boolean hasData()
	{
		boolean hasData = false;
		hasData = this.contacts.size() > 0;
		hasData = this.meetings.size() > 0;
		return hasData;
	}
	
	/**
	 *  Initialises the ContactManager by loading the data onto memory
	 *  If the file doesn't yet exist (and a IOException is thrown)
	 *  then we call flush() to create an empty file 
	 *  @return
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
