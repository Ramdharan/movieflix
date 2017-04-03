package com.egen.movieflix.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.egen.movieflix.entity.Catalog;

@Repository

public class CatalogRepositoryImpl implements CatalogRepository {
	@PersistenceContext
	public EntityManager entityManager;

	@Override
	public List<Catalog> findAll() {
		return entityManager.createNamedQuery("Catalog.findAll", Catalog.class).getResultList();
	}

	@Override
	public List<Catalog> findAllByType(String type) {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.findByType", Catalog.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	// Implement later
	@Override
	public Catalog findByTitle(String title) {
		return null;
	}

	@Override
	public List<Catalog> findAllByYear(Integer year) {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.findByYear", Catalog.class);
		query.setParameter("year", year);
		return query.getResultList();
	}

	@Override
	public List<Catalog> findAllByGenre(String genre) {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.findByGenre", Catalog.class);
		query.setParameter("genre", genre);
		return query.getResultList();
	}

	@Override
	public Catalog findOne(String id) {
		return entityManager.find(Catalog.class, id);
	}

	@Override
	public Catalog create(Catalog catalog) {
		entityManager.persist(catalog);
		return catalog;
	}

	@Override
	public Catalog update(Catalog catalog) {
		return entityManager.merge(catalog);
	}

	@Override
	public void delete(String catalogId) {
		Catalog catalog = entityManager.find(Catalog.class, catalogId);
		if (catalog != null)
			entityManager.remove(catalog);
	}

	@Override
	public List<Catalog> findTopRatedByType(String type) {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.findTopRated", Catalog.class);
		query.setParameter("type", type);
		return query.getResultList();
	}

	@Override
	public List<Catalog> sortByYear() {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.sortByYear", Catalog.class);
		return query.getResultList();

	}

	@Override
	public List<Catalog> sortByVotes() {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.sortByVotes", Catalog.class);
		return query.getResultList();
	}

	@Override
	public List<Catalog> sortByRatings() {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.sortByRatings", Catalog.class);
		return query.getResultList();
	}

	@Override
	public List<Catalog> searchByTitle(String text) {
		TypedQuery<Catalog> query = entityManager.createNamedQuery("Catalog.searchByTitle", Catalog.class);
		query.setParameter("title", text);
		return query.getResultList();

	}

}
