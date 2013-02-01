package domain;
import java.io.Serializable;

import base.Contact;

public class ContactImpl implements Contact, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private int id;
	public String name;
	public String notes;	
	
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String getNotes() {
		// TODO Auto-generated method stub
		return this.notes;
	}

	public void addNotes(String note) {
		// TODO Auto-generated method stub
		if (this.notes.length() > 0) this.notes += ";";		
		this.notes += note;
	}	 
	
	public ContactImpl()
	{ 
		this.id = this.hashCode();		
	}
	
}
