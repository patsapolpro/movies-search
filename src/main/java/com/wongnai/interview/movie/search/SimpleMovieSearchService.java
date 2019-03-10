package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<Movie> movieResult = new ArrayList<>();
		for (MovieData movieData : moviesResponse) {
			queryText = queryText.replaceAll("\\s","\\S");
			Pattern pattern = Pattern.compile("(?i)\\b("+ queryText +")\\b");
			Matcher matcher = pattern.matcher(movieData.getTitle());

			if(matcher.find()) {
				Movie movie = new Movie(movieData.getTitle());
				movie.setActors(movieData.getCast());
				movieResult.add(movie);
			}
		}
		return movieResult;
	}
}
