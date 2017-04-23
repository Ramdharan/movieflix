package com.egen.movieflix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.repository.CatalogRepository;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {
	@Autowired
	public CatalogRepository repository;

	@Override
	public List<Catalog> findAll() {

		return repository.findAll();
	}

	@Override
	public List<Catalog> findAllByType(String type) {

		return repository.findAllByType(type);
	}

	@Override
	public Catalog findByTitle(String title) {
		return null;
	}

	@Override
	public List<Catalog> findAllByYear(Integer year) {

		return repository.findAllByYear(year);
	}

	@Override
	public List<Catalog> findAllByGenre(String genre) {

		return repository.findAllByGenre(genre);
	}

	@Override
	public Catalog findOne(String id) {
		return repository.findOne(id);
	}

	@Override
	public Catalog create(Catalog catalog) {
		return repository.create(catalog);
	}

	@Override
	public Catalog update(Catalog catalog) {

		return repository.update(catalog);
	}

	@Override
	public void delete(String catalogId) {
		repository.delete(catalogId);
	}

	@Override
	public List<Catalog> findTopRatedByType(String type) {
		return repository.findTopRatedByType(type);
	}

	@Override
	public List<Catalog> sortByYear() {

		return repository.sortByYear();
	}

	@Override
	public List<Catalog> sortByVotes() {
		return repository.sortByVotes();
	}

	@Override
	public List<Catalog> sortByRatings() {

		return repository.sortByRatings();
	}

	@Override
	public List<Catalog> searchByTitle(String text) {
		return repository.searchByTitle(text);
	}

}
