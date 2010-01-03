package com.pillartech.raffle.persistence.internal;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pillartech.raffle.domain.Raffle;
import com.pillartech.raffle.persistence.RaffleDAO;

public final class RaffleDAOImpl extends HibernateDaoSupport implements RaffleDAO
{

	@Override
	public Raffle create(Raffle r) {
		getHibernateTemplate().saveOrUpdate(r);
		return r;
	}

	@Override
	public void delete(Raffle r) {
		getHibernateTemplate().delete(r);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Raffle> findAll() {
		return getHibernateTemplate().find("from Raffle");
	}

	@Override
	public Raffle update(Raffle r) {
		getHibernateTemplate().saveOrUpdate(r);
		return r;
	}
}

