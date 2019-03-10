package com.wongnai.interview.movie.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class MovieDataServiceImpl implements MovieDataService {

	public static final Logger logger = LoggerFactory.getLogger(MovieDataServiceImpl.class);

	public static final String MOVIE_DATA_URL
			= "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public MoviesResponse fetchAll() {
		//TODO:
		// Step 1 => Implement this method to download data from MOVIE_DATA_URL and fix any error you may found.
		// Please noted that you must only read data remotely and only from given source,
		// do not download and use local file or put the file anywhere else.
		MoviesResponse moviesResponse = new MoviesResponse();
		ResponseEntity<String> response = restTemplate.getForEntity(MOVIE_DATA_URL, String.class);
		if(response.getStatusCodeValue() == 200) {
			try {
				if(response.getBody() != null) {
					moviesResponse = objectMapper.readValue(response.getBody(), MoviesResponse.class);
				}
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return moviesResponse;
	}
}
