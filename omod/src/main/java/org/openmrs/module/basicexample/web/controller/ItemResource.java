/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.basicexample.web.controller;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.basicexample.Item;
import org.openmrs.module.basicexample.api.BasicexampleService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + "/item", supportedClass = Item.class, supportedOpenmrsVersions = { "1.8.*",
        "2.0.*", "2.1.*", "2.2.*", "2.3.*", "2.4.* " })
public class ItemResource extends DataDelegatingCrudResource<Item> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation r) {
		if (r instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("description");
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			description.addSelfLink();
			return description;
		} else if (r instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("name");
			description.addProperty("owner");
			description.addProperty("description");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("name");
		description.addRequiredProperty("description");
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
		return getCreatableProperties();
	}
	
	@Override
	public Item newDelegate() {
		return new Item();
	}
	
	@Override
	public Item save(Item t) {
		t.setOwner(Context.getAuthenticatedUser());
		Item item = Context.getService(BasicexampleService.class).saveItem(t);
		return item;
	}
	
	@Override
	public Item getByUniqueId(String uuid) {
		Item item = Context.getService(BasicexampleService.class).getItemByUuid(uuid);
		return item;
	}
	
	@Override
	public void purge(Item t, RequestContext context) throws ResponseException {
		if (t == null)
			return;
		Context.getService(BasicexampleService.class).purgeItem(t);
	}
	
	@Override
	protected void delete(Item t, String string, RequestContext rc) throws ResponseException {
		if (t == null)
			return;
		Context.getService(BasicexampleService.class).purgeItem(t);
	}
	
	@Override
	public NeedsPaging<Item> doGetAll(RequestContext context) {
		return new NeedsPaging<Item>(Context.getService(BasicexampleService.class).getAllItems(), context);
	}
	
	@Override
	protected PageableResult doSearch(RequestContext context) {
		
		String query = context.getParameter("q");
		
		List<Item> itemsByQuery = null;
		
		if (query != null) {
			itemsByQuery = Context.getService(BasicexampleService.class).itemsByQuery(query, false);
		} else {
			itemsByQuery = Context.getService(BasicexampleService.class).getAllItems();
		}
		
		return new NeedsPaging<Item>(itemsByQuery, context);
	}
	
}
