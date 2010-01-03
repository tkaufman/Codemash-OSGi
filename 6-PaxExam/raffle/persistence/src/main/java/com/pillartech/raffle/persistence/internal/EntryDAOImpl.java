package com.pillartech.raffle.persistence.internal;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pillartech.raffle.domain.Entry;
import com.pillartech.raffle.persistence.EntryDAO;

public final class EntryDAOImpl extends HibernateDaoSupport implements EntryDAO
{

	@Override
	public Entry create(Entry e) {
		getHibernateTemplate().saveOrUpdate(e);
		return e;
	}

	@Override
	public void delete(Entry e) {
		getHibernateTemplate().delete(e);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> findAll() {
		return getHibernateTemplate().find("from Entry");
	}

	@Override
	public Entry update(Entry e) {
		getHibernateTemplate().saveOrUpdate(e);
		return e;
	}

}

