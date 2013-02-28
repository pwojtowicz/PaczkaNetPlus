package pl.netplus.basepackage.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class ContentObject extends ModelBase {

	@JsonProperty("Text")
	private String text;
	private boolean isFavorites;

	@JsonProperty("Category")
	private String category;

	@JsonProperty("Rating")
	private double rating;

	@JsonProperty("udate")
	public long uploadDate;

	public ContentObject() {

	}

	public ContentObject(int id, String value, double rating,
			boolean isFavorites) {
		super.setId(id);
		this.text = value;
		this.rating = rating;
		this.isFavorites = isFavorites;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isFavorites() {
		return isFavorites;
	}

	public void setFavorites(boolean isFavorites) {
		this.isFavorites = isFavorites;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(long uploadDate) {
		this.uploadDate = uploadDate;
	}

}
