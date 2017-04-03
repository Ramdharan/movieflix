package com.egen.movieflix.repository;

import java.util.List;

import com.egen.movieflix.entity.Authority;
import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.User;

public interface DataFillingRepository {
	public void fillIntialData(List<Catalog> dataList);

	public void insertNewUser(User user);

	public void saveAuthority(Authority auth);
}
