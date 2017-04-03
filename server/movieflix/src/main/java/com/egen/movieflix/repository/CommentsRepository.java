package com.egen.movieflix.repository;

import java.util.List;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.Comments;

public interface CommentsRepository {
public Comments insertNewComment(Comments comment);
public List<Comments> getAllComments(String catalogid);
public Catalog getCatalog(String id);
}
