package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieInvertedIndexRepository;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieInvertedIndexRepository movieInvertedIndexRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		MoviesResponse moviesResponse = movieDataService.fetchAll();

		for(MovieData movieData : moviesResponse){
			Movie movie = new Movie(movieData.getTitle());
			movie.setActors(movieData.getCast());
			movie = movieRepository.save(movie);
			String[] stringArr = movie.getName().split("\\s");
			for (String str: stringArr) {
				movieInvertedIndexRepository.saveWord(str, movie);
			}
		}
	}
}
