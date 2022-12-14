/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.basicexample.api.impl;

import java.util.List;
import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.basicexample.Item;
import org.openmrs.module.basicexample.api.BasicexampleService;
import org.openmrs.module.basicexample.api.dao.BasicexampleDao;

public class BasicexampleServiceImpl extends BaseOpenmrsService implements BasicexampleService {
	
	BasicexampleDao dao;
	
	UserService userService;
	
	public void setDao(BasicexampleDao dao) {
		this.dao = dao;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		return dao.saveItem(item);
	}
	
	@Override
	public void purgeItem(Item item) throws APIException {
		dao.purgeItem(item);
	}
	
	@Override
	public List<Item> getAllItems() throws APIException {
		return dao.getAllItems();
	}
	
	public List<Item> itemsByQuery(String query, boolean includeVoided) throws APIException {
		return dao.itemsByQuery(query, includeVoided);
	}
}
