package com.egen.movieflix.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egen.movieflix.entity.Authority;
import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.entity.User;
import com.egen.movieflix.entity.UserRoles;
import com.egen.movieflix.repository.DataFillingRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
@Transactional

public class DataFillingServiceImpl implements DataFillingService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	DataFillingRepository dataFillingRepo;

	/**
	 * Convert the movielist json data File to List of Catalog Entities using
	 * Jackson
	 * 
	 * @param file,
	 *            movielist json file
	 */
	@Override
	public void insertCatalogData(File file) {

		try {

			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			StringBuffer buffer = new StringBuffer();
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

			TypeReference<List<Catalog>> mapType = new TypeReference<List<Catalog>>() {
			};
			List<Catalog> dataList = objectMapper.readValue(buffer.toString(), mapType);
			// Send to DataFillingRepository to store the list of Catalog
			// objects
			dataFillingRepo.fillIntialData(dataList);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Fill in the Admin details at the start up
	 */
	@Override
	public void insertAdminUser() {

		User user = new User();
		user.setUsername("root@movieflix.com");
		user.setPassword(new BCryptPasswordEncoder().encode("root"));
		user.setEnabled(true);
		List<Authority> authority = new ArrayList<Authority>();
		Authority auth = new Authority();
		auth.setName(UserRoles.ROLE_ADMIN);

		auth.setUsers(new ArrayList<User>());

		List<User> userlist = new ArrayList<User>();
		userlist.add(user);
		auth.setUsers(userlist);
		authority.add(auth);
		user.setAuthorities(authority);
		dataFillingRepo.insertNewUser(user);

	}
}
