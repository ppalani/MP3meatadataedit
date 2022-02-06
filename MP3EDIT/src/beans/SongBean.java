package beans;

import com.mpatric.mp3agic.Mp3File;

public class SongBean {

	String filepath;

	public SongBean() {

	}

	public String getFilepath() {
		return filepath;
	}

	public SongBean(String filepath, String movieName, Metadatabeanv2 secondmetadata, Meatdatabeanv1 firstmetadata,
			String fileName, Mp3File mp3file) {
		super();
		this.filepath = filepath;
		MovieName = movieName;
		this.secondmetadata = secondmetadata;
		this.firstmetadata = firstmetadata;
		this.fileName = fileName;
		this.mp3file = mp3file;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getMovieName() {
		return MovieName;
	}

	public void setMovieName(String movieName) {
		MovieName = movieName;
	}

	public Metadatabeanv2 getSecondmetadata() {
		return secondmetadata;
	}

	public void setSecondmetadata(Metadatabeanv2 secondmetadata) {
		this.secondmetadata = secondmetadata;
	}

	public Meatdatabeanv1 getFirstmetadata() {
		return firstmetadata;
	}

	public void setFirstmetadata(Meatdatabeanv1 firstmetadata) {
		this.firstmetadata = firstmetadata;
	}

	public String getFileName() {
		return fileName;
	}

	public Mp3File getMp3file() {
		return mp3file;
	}

	public void setMp3file(Mp3File mp3file) {
		this.mp3file = mp3file;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	String MovieName;
	Metadatabeanv2 secondmetadata;
	Meatdatabeanv1 firstmetadata;
	String fileName;

	Mp3File mp3file;

}
