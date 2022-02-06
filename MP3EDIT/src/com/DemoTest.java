package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import beans.AlbumBean;
import beans.SongBean;
import services.services;

public class DemoTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		String path = "C:\\Users\\Praveen Palani\\Desktop\\working dir\\out";

		Boolean write = false;

		List<File> files = new ArrayList<File>();
		files = services.filterByPattern(path);
		System.out.println();

		System.out.println("no of files====" + files.size());

		List<SongBean> songs = new ArrayList<SongBean>();

		files.forEach(file -> System.out.println(file.getAbsolutePath()));

		files.forEach(file -> {

			try {

				SongBean song = services.GetMetadata(file);

				songs.add(song);

			} catch (UnsupportedTagException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		System.out.println(songs.size());

		services.createExcelFile(songs, path);

		// assigning albums to a hashset
		Set<String> albums = new HashSet<String>();
		for (int p = 0; p < songs.size(); p++) {

			String albumName = songs.get(p).getSecondmetadata().getAlbumv2();

			albums.add(services.StringHandle(albumName));

		}

		System.out.println(albums);
		// assigning songs to a the album of hash set

		Map<String, List<SongBean>> Songalbums = new HashMap<String, List<SongBean>>();

		albums.forEach(album -> {
			Songalbums.put(album, new ArrayList<SongBean>());
		});

		for (int p = 0; p < songs.size(); p++) {

			String songlistalnm = songs.get(p).getSecondmetadata().getAlbumv2();
			songlistalnm = services.StringHandle(songlistalnm);

			Songalbums.get(songlistalnm).add(songs.get(p));

		}

		List<AlbumBean> SongsAlbums = new ArrayList<AlbumBean>();

		Songalbums.forEach((k, v) -> {
			System.out.println(k + "\t" + v.size());

			List<SongBean> songsinalbum = new ArrayList<SongBean>();

			AlbumBean albumBean = new AlbumBean();

			for (int p = 0; p < v.size(); p++) {
				songsinalbum.add(v.get(p));

			}
			albumBean.setAlbumName(k);
			albumBean.setSongs(songsinalbum);
			SongsAlbums.add(albumBean);
		});

		// assigning songs to a album class created

		System.out.println("from array list");
		SongsAlbums.forEach(k -> {

			System.out.println(k.getAlbumName() + "\t" + k.getSongs().size());

		});

		// writing songs to a output directory, when write is set....

		if (write) {

			System.out.println("writing new mp3 files");
			String opdirec = "C:\\Users\\Praveen Palani\\Desktop\\working dir\\out";
			SongsAlbums.forEach(k -> {
				try {
					services.writeSongstodirectory(opdirec, k);
				} catch (NotSupportedException | IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} else {
			System.out.println("not writing new mp3 files");

		}

	}

}
