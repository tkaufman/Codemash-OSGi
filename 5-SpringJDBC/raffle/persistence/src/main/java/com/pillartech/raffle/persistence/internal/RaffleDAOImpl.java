package com.pillartech.raffle.persistence.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.RaffleDAO;

public final class RaffleDAOImpl implements RaffleDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private SimpleJdbcInsert insertRaffle;
	
	@Autowired
	com.pillartech.raffle.persistence.EntryDAO entryDao;

	public void setDataSource(DataSource dataSource) {
		simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		insertRaffle = new SimpleJdbcInsert(dataSource)
				.withTableName("raffles").usingGeneratedKeyColumns("id");
	}

	public Raffle create(Raffle r) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("number_of_winners", r.getNumberOfWinners());
		parameters.put("started", r.getStarted());
		Number id = insertRaffle.executeAndReturnKey(parameters);
		r.setId((Long) id);
		for (Entry e: r.getEntries()){
			entryDao.create(e);
		}
		return r;
	}

	public void delete(Raffle r) {
		simpleJdbcTemplate.update("delete from entries where raffle_id = ?",
				new Object[] { r.getId() });

		simpleJdbcTemplate.update("delete from raffles where id = ?",
				new Object[] { r.getId() });
	}

	public List<Raffle> findAll() {
		ParameterizedRowMapper<Raffle> mapper = new ParameterizedRowMapper<Raffle>() {

			public Raffle mapRow(ResultSet rs, int rowNum) throws SQLException {
				Raffle r = new Raffle();
				r.setId(rs.getLong("id"));
				r.setNumberOfWinners(rs.getInt("number_of_winners"));
				r.setStarted(rs.getDate("started"));
				return r;
			}
		};

		return simpleJdbcTemplate.query(
				"select id, number_of_winners, started from raffles", mapper);
	}

	public Raffle update(Raffle r) {
		simpleJdbcTemplate.update(
				"update raffles set number_of_winners = ? where id = ?",
				new Object[] { r.getNumberOfWinners(), r.getId() });
		return r;
	}
}
