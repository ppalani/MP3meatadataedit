package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v24Tag;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import beans.AlbumBean;
import beans.Meatdatabeanv1;
import beans.Metadatabeanv2;
import beans.SongBean;

public class services {

	public static String NotAvailable() {
		return "Not Available";
	}

	public static String StringHandle(String input)

	{

		if (input == null) {
			return input;
		}

		else {
			String tempstr = input;

			// removing all MassTamilan

			tempstr = StringUtils.removeIgnoreCase(input, "MassTamilan");

			tempstr = StringUtils.removeIgnoreCase(tempstr, ".io");
			tempstr = StringUtils.removeIgnoreCase(tempstr, ".fm");
			tempstr = StringUtils.removeIgnoreCase(tempstr, ".tamildadafo");
			tempstr = StringUtils.removeIgnoreCase(tempstr, ".in");
			tempstr = StringUtils.removeIgnoreCase(tempstr, ".com");
			tempstr = StringUtils.replaceIgnoreCase(tempstr, "[]", " ");
			tempstr = StringUtils.replaceIgnoreCase(tempstr, "-", " ");
			tempstr = StringUtils.replaceIgnoreCase(tempstr, "_", " ");

			tempstr = WordUtils.capitalizeFully(tempstr);

			tempstr = tempstr.trim();

			// tempstr = tempstr.replaceAll("[^a-zA-Z0-9]", " ");

			return tempstr;
		}

	}

	public static SongBean GetMetadata(File file) throws UnsupportedTagException, InvalidDataException, IOException {

		Meatdatabeanv1 metadatav1;
		Metadatabeanv2 metadatav2;
		Mp3File mp3file = new Mp3File(file);
		SongBean song = new SongBean();

		song.setMp3file(mp3file);
		song.setFilepath(file.getAbsolutePath());

		song.setMovieName(file.getParentFile().getName());

		song.setFileName(file.getName());
		System.out.println("bit rate" + mp3file.getBitrate());

		if (mp3file.hasId3v1Tag()) {

			// public Meatdatabeanv1(String trackv1, String artistv1, String titlev1, String
			// albumv1, String yearv1,
			/// String genrev1, String genreDescriptionv1, String commentv1) {

			System.out.println("hasId3v1Tag-first");
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			metadatav1 = new Meatdatabeanv1(StringHandle(id3v1Tag.getTrack()), StringHandle(id3v1Tag.getArtist()),
					StringHandle(id3v1Tag.getTitle()), StringHandle(id3v1Tag.getAlbum()),
					StringHandle(id3v1Tag.getYear()), id3v1Tag.getGenre(), StringHandle(id3v1Tag.getGenreDescription()),
					StringHandle(id3v1Tag.getComment()));

			song.setFirstmetadata(metadatav1);
			/*
			 * System.out.println("Track: " + id3v1Tag.getTrack());
			 * System.out.println("Artist: " + id3v1Tag.getArtist());
			 * System.out.println("Title: " + id3v1Tag.getTitle());
			 * System.out.println("Album: " + id3v1Tag.getAlbum());
			 * System.out.println("Year: " + id3v1Tag.getYear());
			 * System.out.println("Genre: " + id3v1Tag.getGenre() + " (" +
			 * id3v1Tag.getGenreDescription() + ")"); System.out.println("Comment: " +
			 * id3v1Tag.getComment());
			 */
		} else {
			Meatdatabeanv1 metadatav1exc = new Meatdatabeanv1(NotAvailable(), NotAvailable(), NotAvailable(),
					NotAvailable(), NotAvailable(), 0, NotAvailable(), NotAvailable());

			song.setFirstmetadata(metadatav1exc);
		}

		if (mp3file.hasId3v2Tag()) {
			System.out.println();

			System.out.println("hasId3v2Tag-second");
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();

			metadatav2 = new Metadatabeanv2(StringHandle(id3v2Tag.getTrack()), StringHandle(id3v2Tag.getArtist()),
					StringHandle(id3v2Tag.getTitle()), StringHandle(id3v2Tag.getAlbum()),
					StringHandle(id3v2Tag.getYear()), id3v2Tag.getGenre(), StringHandle(id3v2Tag.getGenreDescription()),
					StringHandle(id3v2Tag.getComment()), StringHandle(id3v2Tag.getLyrics()),
					StringHandle(id3v2Tag.getComposer()), StringHandle(id3v2Tag.getPublisher()),
					StringHandle(id3v2Tag.getOriginalArtist()), StringHandle(id3v2Tag.getAlbumArtist()),
					StringHandle(id3v2Tag.getCopyright()), StringHandle(id3v2Tag.getUrl()),
					StringHandle(id3v2Tag.getItunesComment()), StringHandle(id3v2Tag.getEncoder()),
					id3v2Tag.getAlbumImage());

			// public Metadatabeanv2(String trackv2, String artistv2, String titlev2, String
			// albumv2, String yearv2, int genrev2,
			// String genreDescriptionv2, String commentv2, String lyricsv2, String
			// composerv2, String publisherv2,
			// String original_artistv2, String album_artistv2, String copyrightv2, String
			// uRLv2, String itunescommentv2,
			/// String encoderv2, byte[] albumImageData);

			song.setSecondmetadata(metadatav2);
			/*
			 * System.out.println("Track: " + id3v2Tag.getTrack());
			 * System.out.println("Artist: " + id3v2Tag.getArtist());
			 * System.out.println("Title: " + id3v2Tag.getTitle());
			 * System.out.println("Album: " + id3v2Tag.getAlbum());
			 * System.out.println("Year: " + id3v2Tag.getYear());
			 * System.out.println("Genre: " + id3v2Tag.getGenre() + " (" +
			 * id3v2Tag.getGenreDescription() + ")"); System.out.println("Comment: " +
			 * id3v2Tag.getComment()); System.out.println("Lyrics: " +
			 * id3v2Tag.getLyrics()); System.out.println("Composer: " +
			 * id3v2Tag.getComposer()); System.out.println("Publisher: " +
			 * id3v2Tag.getPublisher()); System.out.println("Original artist: " +
			 * id3v2Tag.getOriginalArtist()); System.out.println("Album artist: " +
			 * id3v2Tag.getAlbumArtist()); System.out.println("Copyright: " +
			 * id3v2Tag.getCopyright()); System.out.println("URL: " + id3v2Tag.getUrl());
			 * 
			 * System.out.println("itunes comment" + id3v2Tag.getItunesComment());
			 * System.out.println("Encoder: " + id3v2Tag.getEncoder()); byte[]
			 * albumImageData = id3v2Tag.getAlbumImage(); if (albumImageData != null) {
			 * System.out.println("Have album image data, length: " + albumImageData.length
			 * + " bytes"); System.out.println("Album image mime type: " +
			 * id3v2Tag.getAlbumImageMimeType()); }
			 */
		} else {

			Metadatabeanv2 metadatabeanv2exec = new Metadatabeanv2(NotAvailable(), NotAvailable(), NotAvailable(),
					NotAvailable(), NotAvailable(), 0, NotAvailable(), NotAvailable(), NotAvailable(), NotAvailable(),
					NotAvailable(), NotAvailable(), NotAvailable(), NotAvailable(), NotAvailable(), NotAvailable(),
					NotAvailable(), null);
			song.setSecondmetadata(metadatabeanv2exec);
		}
		return song;
	}

	public static List<File> filterByPattern(String directory) {
		List<File> files = new ArrayList<File>();
		// Reading the folder and getting Stream.
		try (Stream<Path> walk = Files.walk(Paths.get(directory))) {

			// Filtering the paths by a folder and adding into a list.

			List<String> fileNamesList = walk.map(x -> x.toString()).filter(f -> f.contains(".mp3"))
					.collect(Collectors.toList());

			// printing the folder names
			/// fileNamesList.forEach(System.out::println);

			fileNamesList.forEach(file -> files.add(new File(file)));
			System.out.println("no of strings====" + fileNamesList.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return files;
	}

	public static void writeSongstodirectory(String path, AlbumBean album) throws NotSupportedException, IOException {

		String AlbumName = album.getAlbumName();

		List<SongBean> songs = album.getSongs();

		for (int k = 0; k < songs.size(); k++) {

			String track = String.valueOf(k + 1);

			SongBean song = songs.get(k);
			Mp3File mp3file = song.getMp3file();

			if (mp3file.hasId3v1Tag()) {
				mp3file.removeId3v1Tag();
			}
			if (mp3file.hasId3v2Tag()) {
				mp3file.removeId3v2Tag();
			}
			if (mp3file.hasCustomTag()) {
				mp3file.removeCustomTag();
			}

			song.setFileName(song.getSecondmetadata().getTitlev2() + ".mp3");

			ID3v1 id3v1Tag;
			if (mp3file.hasId3v1Tag()) {
				id3v1Tag = mp3file.getId3v1Tag();
			} else {
				// mp3 does not have an ID3v1 tag, let's create one..
				id3v1Tag = new ID3v1Tag();
				mp3file.setId3v1Tag(id3v1Tag);
			}

			// setting id3v1 tags
			id3v1Tag.setTrack(track);
			id3v1Tag.setArtist(song.getSecondmetadata().getArtistv2());
			id3v1Tag.setTitle(song.getSecondmetadata().getTitlev2());
			id3v1Tag.setAlbum(AlbumName);
			id3v1Tag.setYear(song.getSecondmetadata().getYearv2());
			id3v1Tag.setGenre(24);
			id3v1Tag.setComment("Praveen P Ilayaraja Songs");

			// setting id3v2 tags

			ID3v2 id3v2Tag;
			if (mp3file.hasId3v2Tag()) {
				id3v2Tag = mp3file.getId3v2Tag();
			} else {
				// mp3 does not have an ID3v2 tag, let's create one..
				id3v2Tag = new ID3v24Tag();
				mp3file.setId3v2Tag(id3v2Tag);
			}
			id3v2Tag.setTrack(track);
			id3v2Tag.setArtist(song.getSecondmetadata().getArtistv2());
			id3v2Tag.setTitle(song.getSecondmetadata().getTitlev2());
			id3v2Tag.setAlbum(AlbumName);
			id3v2Tag.setYear(song.getSecondmetadata().getYearv2());
			id3v2Tag.setGenre(24);
			id3v2Tag.setGenreDescription("Soundtrack");
			id3v2Tag.setComment("Praveen P Ilayaraja Songs");
			id3v2Tag.setLyrics("");
			id3v2Tag.setComposer("");
			id3v2Tag.setPublisher("");
			id3v2Tag.setOriginalArtist("Ilayaraja");
			id3v2Tag.setAlbumArtist("Ilayaraja");
			id3v2Tag.setCopyright("");
			id3v2Tag.setUrl("");
			id3v2Tag.setEncoder("");

			File theDir = new File("C:\\Users\\Praveen Palani\\Desktop\\working dir\\out" + "\\" + AlbumName);
			if (!theDir.exists()) {
				theDir.mkdirs();
			}

			mp3file.save("C:\\Users\\Praveen Palani\\Desktop\\working dir\\out" + "\\" + AlbumName + "\\"
					+ song.getFileName());
		}

	}

	public static void createExcelFile(List<SongBean> songs, String xlfilepath)
			throws FileNotFoundException, IOException {
		int sno = 1;
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Songs");

		Row row = sheet.createRow(0);

		row.createCell(0).setCellValue("S no");
		row.createCell(1).setCellValue("Folder Name");
		row.createCell(2).setCellValue("File Name");

		// setting headers for version 1 tags
		row.createCell(3).setCellValue("Trackv1");
		row.createCell(4).setCellValue("Artistv1");
		row.createCell(5).setCellValue("Titlev1");
		row.createCell(6).setCellValue("Albumv1");
		row.createCell(7).setCellValue("Yearv1");
		row.createCell(8).setCellValue("Genrev1");
		row.createCell(9).setCellValue("GenreDescriptionv1");
		row.createCell(10).setCellValue("Commentv1");

		// setting headers for version 2 tags

		row.createCell(11).setCellValue("Artistv2");
		row.createCell(12).setCellValue("Titlev2");
		row.createCell(13).setCellValue("Albumv2");
		row.createCell(14).setCellValue("Yearv2");
		row.createCell(15).setCellValue("Genrev2");
		row.createCell(16).setCellValue("GenreDescriptionv2");
		row.createCell(17).setCellValue("Commentv2");
		row.createCell(18).setCellValue("Lyricsv2");
		row.createCell(19).setCellValue("Composerv2");
		row.createCell(20).setCellValue("Publisherv2");
		row.createCell(21).setCellValue("Original_artistv2");
		row.createCell(22).setCellValue("Album_artistv2");
		row.createCell(23).setCellValue("Copyrightv2");
		row.createCell(24).setCellValue("URLv2");
		row.createCell(25).setCellValue("itunescommentv2");
		row.createCell(26).setCellValue("Encoderv2");

		// populating songs data...

		for (int p = 0; p < songs.size(); p++) {

			SongBean song = songs.get(p);
			Row datarow = sheet.createRow(p + 1);

			datarow.createCell(0).setCellValue(p + 1);
			datarow.createCell(1).setCellValue((song.getMovieName()));
			datarow.createCell(2).setCellValue((song.getFileName()));

			Meatdatabeanv1 meatdatabeanv1 = song.getFirstmetadata();
			// setting values for version 1 tags
			datarow.createCell(3).setCellValue((meatdatabeanv1.getTrackv1()));

			datarow.createCell(4).setCellValue((meatdatabeanv1.getArtistv1()));
			datarow.createCell(5).setCellValue((meatdatabeanv1.getTitlev1()));
			datarow.createCell(6).setCellValue((meatdatabeanv1.getAlbumv1()));
			datarow.createCell(7).setCellValue((meatdatabeanv1.getYearv1()));
			datarow.createCell(8).setCellValue(meatdatabeanv1.getGenrev1());
			datarow.createCell(9).setCellValue((meatdatabeanv1.getGenreDescriptionv1()));
			datarow.createCell(10).setCellValue((meatdatabeanv1.getCommentv1()));

			// setting values for version 2 tags
			Metadatabeanv2 metadatabeanv2 = song.getSecondmetadata();
			datarow.createCell(11).setCellValue((metadatabeanv2.getArtistv2()));
			datarow.createCell(12).setCellValue((metadatabeanv2.getTitlev2()));
			datarow.createCell(13).setCellValue((metadatabeanv2.getAlbumv2()));
			datarow.createCell(14).setCellValue((metadatabeanv2.getYearv2()));
			datarow.createCell(15).setCellValue(metadatabeanv2.getGenrev2());
			datarow.createCell(16).setCellValue((metadatabeanv2.getGenreDescriptionv2()));
			datarow.createCell(17).setCellValue((metadatabeanv2.getCommentv2()));
			datarow.createCell(18).setCellValue((metadatabeanv2.getLyricsv2()));
			datarow.createCell(19).setCellValue((metadatabeanv2.getComposerv2()));
			datarow.createCell(20).setCellValue((metadatabeanv2.getPublisherv2()));
			datarow.createCell(21).setCellValue((metadatabeanv2.getOriginal_artistv2()));
			datarow.createCell(22).setCellValue((metadatabeanv2.getAlbum_artistv2()));
			datarow.createCell(23).setCellValue((metadatabeanv2.getCopyrightv2()));
			datarow.createCell(24).setCellValue((metadatabeanv2.getURLv2()));
			datarow.createCell(25).setCellValue((metadatabeanv2.getItunescommentv2()));
			datarow.createCell(26).setCellValue((metadatabeanv2.getEncoderv2()));
		}

		try (OutputStream fileOut = new FileOutputStream(xlfilepath + "\\" + "SONGS_meta.xls")) {
			wb.write(fileOut);

			System.out.println(fileOut);

		}
	}

}
