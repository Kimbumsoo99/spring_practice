package com.ssafy.movie.model.dto;

public class MovieDto {
	int id;
	String title;
	String director;
	String genre;
	int runningTime;
	String img;

	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", title=" + title + ", director=" + director + ", genre=" + genre
				+ ", runningTime=" + runningTime + ", img=" + img + "]";
	}

	public MovieDto(int id, String title, String director, String genre, int runningTime, String img) {
		super();
		this.id = id;
		this.title = title;
		this.director = director;
		this.genre = genre;
		this.runningTime = runningTime;
		this.img = img;
	}

	public MovieDto() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
