package com.egen.movieflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egen.movieflix.entity.Catalog;
import com.egen.movieflix.service.CatalogService;

/**
 * Handles mapping requests related to Catalog
 * 
 * @author ramdharandonda
 *
 */
@RestController
@RequestMapping(value = "/catalog")
public class CatalogController {
	@Autowired
	public CatalogService catalogService;

	@RequestMapping(value = "/getall", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> getAll() {

		return catalogService.findAll();
	}

	@RequestMapping(value = "/findbyyear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> findByYear(@RequestParam("year") Integer year) {

		return catalogService.findAllByYear(year);
	}

	@RequestMapping(value = "/gettoprated", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> findTopRatedByType(@RequestParam("type") String type) {

		return catalogService.findTopRatedByType(type);
	}

	@RequestMapping(value = "/sortbyyear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> sortByYear() {

		return catalogService.sortByYear();
	}

	@RequestMapping(value = "/sortbyvotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> sortByVotes() {

		return catalogService.sortByVotes();
	}

	@RequestMapping(value = "/sortbyratings", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> sortByImdbRatings() {

		return catalogService.sortByRatings();
	}

	@RequestMapping(value = "/searchtitle", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Catalog> searchTitle(@RequestParam("text") String text) {

		return catalogService.searchByTitle(text);
	}

	// All the requests for Create,Update and Delete are handled by spring
	// security.
	// It will intercept the url and check if the user has ADMIN rights.
	// Handling this is in SecurityConfig class and TokenFilter class

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Catalog createCatalog(@RequestBody Catalog catalog) {
		return catalogService.create(catalog);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Catalog updateCatalog(@RequestBody Catalog catalog) {
		return catalogService.update(catalog);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void deleteCatalog(@RequestParam("catalogid") String catalogId) {

		catalogService.delete(catalogId);
	}

}
