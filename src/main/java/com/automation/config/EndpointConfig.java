package com.automation.config;

public class EndpointConfig {

	// Base URLs
	public static final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1";
	public static final String SPOTIFY_ACCOUNTS_URL = "https://accounts.spotify.com";

	// Authentication
	public static final String TOKEN_ENDPOINT = "/api/token";

	// User Endpoints
	public static final String CURRENT_USER_PROFILE = "/me";
	public static final String USER_TOP_ITEMS = "/me/top/{type}";
	public static final String USER_PLAYLISTS = "/me/playlists";
	public static final String USER_SAVED_TRACKS = "/me/tracks";
	public static final String USER_SAVED_ALBUMS = "/me/albums";
	public static final String USER_FOLLOWING = "/me/following";

	// Track Endpoints
	public static final String TRACK = "/tracks/{id}";
	public static final String TRACKS = "/tracks";
	public static final String TRACK_AUDIO_FEATURES = "/audio-features/{id}";
	public static final String TRACK_AUDIO_ANALYSIS = "/audio-analysis/{id}";

	// Album Endpoints
	public static final String ALBUM = "/albums/{id}";
	public static final String ALBUMS = "/albums";
	public static final String ALBUM_TRACKS = "/albums/{id}/tracks";

	// Artist Endpoints
	public static final String ARTIST = "/artists/{id}";
	public static final String ARTISTS = "/artists";
	public static final String ARTIST_ALBUMS = "/artists/{id}/albums";
	public static final String ARTIST_TOP_TRACKS = "/artists/{id}/top-tracks";
	public static final String ARTIST_RELATED_ARTISTS = "/artists/{id}/related-artists";

	// Playlist Endpoints
	public static final String PLAYLIST = "/playlists/{id}";
	public static final String PLAYLIST_TRACKS = "/playlists/{id}/tracks";
	public static final String CREATE_PLAYLIST = "/users/{user_id}/playlists";

	// Search Endpoint
	public static final String SEARCH = "/search";

	// Browse Endpoints
	public static final String NEW_RELEASES = "/browse/new-releases";
	public static final String FEATURED_PLAYLISTS = "/browse/featured-playlists";
	public static final String CATEGORIES = "/browse/categories";
	public static final String CATEGORY = "/browse/categories/{id}";
	public static final String CATEGORY_PLAYLISTS = "/browse/categories/{id}/playlists";
	public static final String RECOMMENDATIONS = "/recommendations";

	// Player Endpoints
	public static final String CURRENTLY_PLAYING = "/me/player/currently-playing";
	public static final String RECENTLY_PLAYED = "/me/player/recently-played";
	public static final String AVAILABLE_DEVICES = "/me/player/devices";

	private EndpointConfig() {
		// Private constructor to prevent instantiation
	}
}