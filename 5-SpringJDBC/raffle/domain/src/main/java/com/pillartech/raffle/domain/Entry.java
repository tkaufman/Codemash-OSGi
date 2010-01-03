package com.pillartech.raffle.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Entry {

	private Long id;
	private String name;
	private String email;
	private Date created;
	private Raffle raffle;

	@Override
	public boolean equals(Object obj) {

		if (obj != null && obj instanceof Entry) {
			Entry other = (Entry)obj;
			return new EqualsBuilder()
				.append(name, other.getName())
				.append(email, other.getEmail())
				.append(created, other.getCreated())
				.isEquals();
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(name)
			.append(email)
			.append(created)
			.toHashCode();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Raffle getRaffle() {
		return raffle;
	}
	public void setRaffle(Raffle raffle) {
		this.raffle = raffle;
	}
}
