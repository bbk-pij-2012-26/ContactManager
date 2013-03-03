package domain;

import java.io.Serializable;
import java.util.Comparator;

import base.Meeting;

public class MeetingComparator implements Serializable, Comparator<Meeting> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Meeting m1, Meeting m2) {
		return m1.getDate().compareTo(m2.getDate());
	}
}
