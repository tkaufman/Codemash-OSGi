package com.pillartech.raffle.persistence;

import java.util.List;

import com.pillartech.raffle.domain.Entry;

public interface EntryDAO {
	public Entry create(Entry e);
	public Entry update(Entry e);
	public void delete(Entry e);
	public List<Entry> findAll();
}
