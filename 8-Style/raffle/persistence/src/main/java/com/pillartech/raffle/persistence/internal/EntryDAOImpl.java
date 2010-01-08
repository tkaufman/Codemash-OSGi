package com.pillartech.raffle.persistence.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.internal.RaffleDAOImpl;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.persistence.EntryDAO;

public final class EntryDAOImpl implements EntryDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private SimpleJdbcInsert insertEntry;

	public void setDataSource(DataSource dataSource) {
		simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		insertEntry = new SimpleJdbcInsert(dataSource).withTableName("entries")
				.usingGeneratedKeyColumns("id");
	}

	public Entry create(Entry e) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", e.getName());
		parameters.put("email", e.getEmail());
		parameters.put("created", e.getCreated());
		parameters.put("raffle_id", e.getRaffle().getId());
		Number id = insertEntry.executeAndReturnKey(parameters);
		e.setId((Long) id);
		return e;
	}

	public void delete(Entry e) {
		simpleJdbcTemplate.update("delete from entries where id = ?",
				new Object[] { e.getId() });
	}

	public List<Entry> findAll() {

		EntryRowMapper mapper = new EntryRowMapper();
		
		// TODO: Change from N+1 if performance hit
		List<Entry> results = simpleJdbcTemplate.query(
				"select id, name, email, created from entries", mapper);
		for (Entry e: results) {
			populateRaffleForEntry(e);
		}
		return results;
	}

	public List<Entry> findForRaffle(Long raffleId) {
		EntryRowMapper mapper = new EntryRowMapper();

		List<Entry> results = simpleJdbcTemplate.query(
				"select id, name, email, created from entries where raffle_id = ?", mapper, new Object[]{raffleId});

		return results;
	}

	public Entry update(Entry e) {
		simpleJdbcTemplate.update(
				"update entries set name = ?, email = ? where id = ?",
				new Object[] { e.getName(), e.getEmail(), e.getId() });
		return e;
	}

	public static final class EntryRowMapper implements ParameterizedRowMapper<Entry> {
		public Entry mapRow(ResultSet rs, int rowNum) throws SQLException {
			Entry e = new Entry();
			e.setId(rs.getLong("id"));
			e.setName(rs.getString("name"));
			e.setEmail(rs.getString("email"));
			e.setCreated(rs.getDate("created"));
			return e;
		}
	};

	private void populateRaffleForEntry(Entry e) {
		RaffleDAOImpl.RaffleRowMapper mapper = new RaffleDAOImpl.RaffleRowMapper();

		Raffle r = simpleJdbcTemplate.queryForObject(
						"select id, name, number_of_winners, started from raffles where id = (select raffle_id from entries where id = ?)",
						mapper, new Object[] { e.getId() });

		e.setRaffle(r);
	}

}
