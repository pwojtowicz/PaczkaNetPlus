package pl.netplus.basepackage.entities;

import org.codehaus.jackson.annotate.JsonProperty;

public class WebContentObjectContainer extends WebObjectContener {

	@JsonProperty("Items")
	public ContentObject[] items;
}
