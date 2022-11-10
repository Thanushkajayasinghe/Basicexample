/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.basicexample.api.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.basicexample.Item;

public class BasicexampleDao {
	
	DbSessionFactory sessionFactory;
	
	public void setSessionFactory(DbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	public void purgeItem(Item item) {
		getSession().delete(item);
	}
	
	public List<Item> getAllItems() {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Item.class);
		return crit.list();
	}
	
	public List<Item> itemsByQuery(String query, boolean includeVoided) {
		
		if (query != null || !query.equals("=") || query.trim().length() != 0) {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Item.class, "i");
			// criteria.createAlias("i.owner", "owner");
			if (!includeVoided) {
				criteria.add(Restrictions.eq("i.voided", false));
			}
			
			Disjunction or = Restrictions.disjunction();
			MatchMode mode = MatchMode.ANYWHERE;
			or.add(Restrictions.ilike("i.name", query, mode));
			or.add(Restrictions.ilike("i.description", query, mode));
			criteria.add(or);
			return (List<Item>) criteria.list();
		} else {
			return getAllItems();
		}
	}
}
