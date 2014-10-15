package com.jeromesimmonds.phonebook.api.ws.filter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

@Provider
public class MediaTypeResponseFilter implements ContainerResponseFilter {
	
	private static final Pattern PATTERN_EXTENSION_XML = Pattern.compile("\\w+\\.xml($|/.?)");

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		List<MediaType> mediaTypes = requestContext.getAcceptableMediaTypes();
		MediaType finalMediaType = MediaType.valueOf(MediaType.APPLICATION_JSON);
		if ((mediaTypes.size() == 1 && mediaTypes.get(0).toString().equals(MediaType.APPLICATION_XML))
			|| PATTERN_EXTENSION_XML.matcher(requestContext.getUriInfo().getPath().toLowerCase()).matches())
			finalMediaType = MediaType.valueOf(MediaType.APPLICATION_XML);

		responseContext.setEntity(responseContext.getEntity(), null, finalMediaType);
	}	
}
