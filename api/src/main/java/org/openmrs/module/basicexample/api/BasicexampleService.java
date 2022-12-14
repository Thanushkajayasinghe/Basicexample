/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.basicexample.api;

import java.util.List;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.basicexample.BasicexampleConfig;
import org.openmrs.module.basicexample.Item;
import org.springframework.transaction.annotation.Transactional;

public interface BasicexampleService extends OpenmrsService {
	
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	
	@Authorized(BasicexampleConfig.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;
	
	@Authorized(BasicexampleConfig.MODULE_PRIVILEGE)
	@Transactional
	void purgeItem(Item item) throws APIException;
	
	@Authorized()
	@Transactional()
	List<Item> getAllItems();
	
	@Authorized()
	@Transactional()
	List<Item> itemsByQuery(String query, boolean includeVoided) throws APIException;
}
