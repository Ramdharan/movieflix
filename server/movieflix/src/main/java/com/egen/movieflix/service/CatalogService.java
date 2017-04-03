package com.egen.movieflix.service;

import java.util.List;

import com.egen.movieflix.entity.Catalog;

public interface CatalogService {
	public List<Catalog> findAll();

	public List<Catalog> findAllByType(String type);

	public Catalog findByTitle(String title);

	public List<Catalog> findAllByYear(Integer year);

	public List<Catalog> findAllByGenre(String genre);

	public Catalog findOne(String id);

	public Catalog create(Catalog catalog);

	public Catalog update(Catalog catalog);

	public void delete(String catalogId);

	public List<Catalog> findTopRatedByType(String type);

	public List<Catalog> sortByYear();

	public List<Catalog> sortByVotes();

	public List<Catalog> sortByRatings();

	public List<Catalog> searchByTitle(String text);

}
