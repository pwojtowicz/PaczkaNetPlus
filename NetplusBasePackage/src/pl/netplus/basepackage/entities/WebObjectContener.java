package pl.netplus.basepackage.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class WebObjectContener {

	@JsonProperty("polaczenie")
	public String connection;

	@JsonProperty("utime_serwera")
	public long serverTime;

}
