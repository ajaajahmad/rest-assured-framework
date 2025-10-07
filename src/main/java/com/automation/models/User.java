package com.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String id;

	@JsonProperty("display_name")
	private String displayName;

	private String email;

	private String country;

	private String product;

	@JsonProperty("external_urls")
	private Map<String, String> externalUrls;

	private Followers followers;

	private List<Image> images;

	private String href;

	private String type;

	private String uri;

	@JsonProperty("explicit_content")
	private ExplicitContent explicitContent;

	// Constructors
	public User() {
	}

	public User(String id, String displayName, String email) {
		this.id = id;
		this.displayName = displayName;
		this.email = email;
	}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Map<String, String> getExternalUrls() {
		return externalUrls;
	}

	public void setExternalUrls(Map<String, String> externalUrls) {
		this.externalUrls = externalUrls;
	}

	public Followers getFollowers() {
		return followers;
	}

	public void setFollowers(Followers followers) {
		this.followers = followers;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ExplicitContent getExplicitContent() {
		return explicitContent;
	}

	public void setExplicitContent(ExplicitContent explicitContent) {
		this.explicitContent = explicitContent;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", displayName='" + displayName + '\'' + ", email='" + email + '\''
				+ ", country='" + country + '\'' + ", product='" + product + '\'' + '}';
	}

	// Nested classes
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Followers {
		private String href;
		private int total;

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Image {
		private String url;
		private int height;
		private int width;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ExplicitContent {
		@JsonProperty("filter_enabled")
		private boolean filterEnabled;

		@JsonProperty("filter_locked")
		private boolean filterLocked;

		public boolean isFilterEnabled() {
			return filterEnabled;
		}

		public void setFilterEnabled(boolean filterEnabled) {
			this.filterEnabled = filterEnabled;
		}

		public boolean isFilterLocked() {
			return filterLocked;
		}

		public void setFilterLocked(boolean filterLocked) {
			this.filterLocked = filterLocked;
		}
	}
}