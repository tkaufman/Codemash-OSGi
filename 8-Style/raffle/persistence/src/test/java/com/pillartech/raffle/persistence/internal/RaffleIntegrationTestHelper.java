package com.pillartech.raffle.persistence.internal;

import static org.testng.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;

public class RaffleIntegrationTestHelper {

	public static Entry constructEntry(String name, String email, Raffle raffle) {
		Entry result = new Entry();
		result.setName(name);
		result.setEmail(email);
		result.setCreated(new Date());
		result.setRaffle(raffle);
		return result;
	}

	public static Raffle constructRaffle(String name, String email) {
		Raffle result = new Raffle();
		result.setName("test-raffle");
		result.setStarted(new Date());
		Entry e = new Entry();
		e.setName(name);
		e.setEmail(email);
		e.setCreated(new Date());
		result.addEntry(e);
		result.setNumberOfWinners(1);
		return result;
	}

	public static void assertDbFieldsMatch(SimpleJdbcTemplate dbTemplate, Entry source) {
		Entry result = (Entry) dbTemplate.queryForObject(
				"select id, name, email, created from entries where id = ?",
				new ParameterizedRowMapper<Entry>() {
					public Entry mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Entry e = new Entry();
						e.setId(rs.getLong("id"));
						e.setName(rs.getString("name"));
						e.setEmail(rs.getString("email"));
						e.setCreated(rs.getDate("created"));
						return e;
					}
				}, source.getId());
		assertTwoEntriesMatch(source, result);
	}

	public static void assertDbFieldsMatch(SimpleJdbcTemplate dbTemplate, Raffle source) {
		Raffle result = (Raffle) dbTemplate.queryForObject(
				"select id, name, number_of_winners, started from raffles where id = ?",
				new ParameterizedRowMapper<Raffle>() {
					public Raffle mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Raffle r = new Raffle();
						r.setId(rs.getLong("id"));
						r.setName(rs.getString("name"));
						r.setNumberOfWinners(rs.getInt("number_of_winners"));
						r.setStarted(rs.getDate("started"));
						return r;
					}
				}, source.getId());
		assertTwoRafflesMatch(source, result);
	}

	public static void assertTwoEntriesMatch(Entry source, Entry result) {
		assertEquals(source.getId(), result.getId());
		assertEquals(source.getName(), result.getName());
		assertEquals(source.getEmail(), result.getEmail());
	}

	public static void assertTwoRafflesMatch(Raffle source, Raffle result) {
		assertEquals(source.getId(), result.getId());
		assertEquals(source.getName(), result.getName());
		assertEquals(source.getNumberOfWinners(), result.getNumberOfWinners());
	}

}