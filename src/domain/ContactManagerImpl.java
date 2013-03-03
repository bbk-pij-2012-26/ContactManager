package domain;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
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
		if (date.before(Calendar.getInstance())) //ensure it's a past meeting
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
		if (pm != null && Calendar.getInstance().before(pm.getDate()))
		{
			throw new IllegalArgumentException();
		}
		
		return pm;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		FutureMeeting fm = (FutureMeeting)this.meetings.get(id);
		if (fm != null && fm.getDate().before(Calendar.getInstance()))
		{
			throw new IllegalArgumentException();
		}
		
		return fm;
	}

	@Override
	public Meeting getMeeting(int id) {
		return this.meetings.get(id);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact == null || !this.contacts.containsKey(contact.getId()))
		{
			throw new IllegalArgumentException();
		}
		
		List<Meeting> meetings = new ArrayList<Meeting>();		
		for (Meeting m : this.meetings.values()) {
			if (Calendar.getInstance().before(m.getDate())) //if is in future
			{		
				for (Contact c : m.getContacts()) {
					if (c.getId() == contact.getId()) {
						meetings.add(m);	
						break;
					}
				}			
			}
		}
				
		return meetings;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {	
		HashMap<Integer, Meeting> meetings = new HashMap<Integer, Meeting>();		
		for (Meeting m : this.meetings.values()) {
			boolean isInFuture = Calendar.getInstance().before(m.getDate());
			boolean isInNewList = meetings.containsKey(m.getId());		
			
			if (isInFuture && !isInNewList) {
				meetings.put(m.getId(), m);
			}
		}
		
		List<Meeting> uniqueMeetings = new ArrayList<Meeting>();
		uniqueMeetings.addAll(meetings.values());
		
		Collections.sort(uniqueMeetings, new MeetingComparator());
		
		return uniqueMeetings;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if (contact == null || !this.contacts.containsKey(contact.getId()))
		{
			throw new IllegalArgumentException();
		}
		
		List<PastMeeting> meetings = new ArrayList<PastMeeting>();		
		for (Meeting m : this.meetings.values()) {
			if (Calendar.getInstance().after(m.getDate())) //if is in past
			{		
				for (Contact c : m.getContacts()) {
					if (c.getId() == contact.getId()) {
						meetings.add((PastMeeting)m);	
						break;
					}
				}			
			}
		}
				
		return meetings;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts == null || date == null || text == null) {
			throw new NullPointerException();
		}
		
		if (contacts.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		for (Contact c : contacts)
		{
			if (!this.contacts.containsKey(c.getId()))
			{
				throw new IllegalArgumentException();
			}
		}
		
		PastMeeting pm = new PastMeetingImpl(contacts, date, text);	
		this.meetings.put(pm.getId(), pm);		
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		if (!this.meetings.containsKey(id)) {
			throw new IllegalArgumentException();
		}
		if (text == null || text.isEmpty()) {
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
		if (name == null || name.isEmpty() || 
			notes == null || notes.isEmpty()) {
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
					contacts.add(this.contacts.get(id));
				}
				else {
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
	 * Deletes all contacts and meetings information
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
