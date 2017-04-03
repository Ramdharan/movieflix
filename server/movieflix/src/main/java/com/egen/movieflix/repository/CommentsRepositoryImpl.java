package com.egen.movieflix.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Comments;
@Repository
public class CommentsRepositoryImpl implements CommentsRepository{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Comments insertNewComment(Comments comment) {
		entityManager.persist(comment);
		return comment;
	}

	@Override
	public List<Comments> getAllComments(String catalogid) {
		TypedQuery<Comments> query=entityManager.createNamedQuery("Comments.getAll",Comments.class);
		query.setParameter("catalog_id",catalogid);
		return query.getResultList();
	}

	@Override
	public Catalog getCatalog(String id) {
		Catalog catalog=entityManager.find(Catalog.class, id);  
		return catalog;
	}

	

}
