package beans;

import java.util.List;

public class AlbumBean {
	public AlbumBean(List<SongBean> songs, String albumName) {
		super();
		this.songs = songs;
		this.albumName = albumName;
	}

	public AlbumBean() {

	}

	List<SongBean> songs;

	public List<SongBean> getSongs() {
		return songs;
	}

	public void setSongs(List<SongBean> songs) {
		this.songs = songs;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	String albumName;
}
