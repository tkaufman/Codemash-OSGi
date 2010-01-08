package com.pillartech.raffle.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.math.RandomUtils;

public class Raffle {

	private Long id;
	private String name;
	private Date started;
	private int numberOfWinners;
	private Set<Entry> entries;
	
	public Raffle() {
		setStarted(new Date());
		setEntries(new HashSet<Entry>());
	}
	
	public void addEntry(Entry entry) {
		if (getEntries() == null) {
			setEntries(new HashSet<Entry>());
		}
		entry.setRaffle(this);
		getEntries().add(entry);
	}
	
	public Set<Entry> pickWinners() {
		Set<Entry> winners = new HashSet<Entry>();
		if (numberOfWinners >= entries.size()) {
			winners.addAll(entries);
			entries.clear();
		}
		else {			
			while (winners.size() < numberOfWinners) {
				winners.add(pickAndRemoveRandomEntry(entries));
			}
		}
		return winners;
	}
	
	Entry pickAndRemoveRandomEntry(Set<Entry> entrySet) {
		int itemIndex = RandomUtils.nextInt(entrySet.size());
		int i = 0;
		for (Entry e : entrySet) {
			if (i == itemIndex) {
				entrySet.remove(e);
				return e;
			}
			i++;
		}
		
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Raffle) {
			Raffle other = (Raffle) obj;
			
			return new EqualsBuilder()
				.append(name, other.getName())
				.append(started, other.getStarted())
				.append(numberOfWinners, other.getNumberOfWinners())
				.append(entries, other.getEntries())
				.isEquals();
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(name)
			.append(started)
			.append(numberOfWinners)
			.append(entries)
			.toHashCode();
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public Date getStarted() {
		return started;
	}
	public void setNumberOfWinners(int numberOfWinners) {
		this.numberOfWinners = numberOfWinners;
	}
	public int getNumberOfWinners() {
		return numberOfWinners;
	}
	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}
	public Set<Entry> getEntries() {
		return entries;
	}
	
}
