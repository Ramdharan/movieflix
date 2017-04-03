package com.egen.movieflix.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.GenericGenerator;

@Entity

@NamedQueries({ @NamedQuery(name = "Catalog.findAll", query = "SELECT c from Catalog c"),
		@NamedQuery(name = "Catalog.findByType", query = "SELECT c from Catalog c where c.type=:type"),
		@NamedQuery(name = "Catalog.findByYear", query = "SELECT c from Catalog c where c.year=:year"),
		@NamedQuery(name = "Catalog.findByGenre", query = "SELECT c from Catalog c where c.genre=:genre"),
		@NamedQuery(name = "Catalog.findTopRated", query = "SELECT c from Catalog c where c.type=:type "
				+ "ORDER BY c.imdbRating DESC"),
		@NamedQuery(name = "Catalog.sortByRatings", query = "SELECT c from Catalog c ORDER BY c.imdbRating DESC"),
		@NamedQuery(name = "Catalog.sortByYear", query = "SELECT c from Catalog c ORDER BY c.year DESC"),
		@NamedQuery(name = "Catalog.sortByVotes", query = "SELECT c from Catalog c ORDER BY c.imdbVotes DESC"),
		@NamedQuery(name = "Catalog.searchByTitle", query = "SELECT c from Catalog c Where c.title LIKE CONCAT('%',:title,'%')") })

public class Catalog implements Serializable {

	private static final long serialVersionUID = 590135836887626928L;
	@Id
	@GenericGenerator(name = "customUUID", strategy = "uuid2")
	@GeneratedValue(generator = "customUUID")
	private String id;

	@Override
	public String toString() {
		return "Catalog [title=" + title + ", year=" + year + ", rated=" + rated + ", released=" + released
				+ ", runTime=" + runtime + ", genre=" + genre + ", director=" + director + ", writers=" + writer
				+ ", actors=" + actors + ", plot=" + plot + ", language=" + language + ", country=" + country
				+ ", awards=" + awards + ", poster=" + poster + ", metascore=" + metaScore + ", imdbRating="
				+ imdbRating + ", imdbVotes=" + imdbVotes + ", imdbId=" + imdbId + ", type=" + type + "]";
	}

	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAwards() {
		return awards;
	}

	public void setAwards(String awards) {
		this.awards = awards;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public int getMetaScore() {
		return metaScore;
	}

	public void setMetaScore(int metaScore) {
		this.metaScore = metaScore;
	}

	public float getImdbRating() {
		return imdbRating;
	}

	public void setImdbRating(float imdbRating) {
		this.imdbRating = imdbRating;
	}

	public int getImdbVotes() {
		return imdbVotes;
	}

	public void setImdbVotes(int imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private int year;
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	@Column(length = 10000)
	private String writer;
	private String actors;
	@Column(length = 10000)
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private int metaScore;
	private float imdbRating;
	private int imdbVotes;
	private String imdbId;
	private String type;

}
