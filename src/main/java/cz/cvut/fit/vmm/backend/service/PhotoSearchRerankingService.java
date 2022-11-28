package cz.cvut.fit.vmm.backend.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import cz.cvut.fit.vmm.backend.dto.PhotoComputeScore;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import cz.cvut.fit.vmm.backend.score_computing_strategy.ScoreComputingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PhotoSearchRerankingService {

  @Autowired
  private FlickrPhotosFetcher flickrPhotosFetcher;

  @Autowired
  private ScoreComputingStrategy scoreComputingStrategy;

  public List<PhotoSortWrapper> searchPhotos(PhotoSearchDto search, int pageFrom, int pageTo) throws FlickrException {
    List<CompletableFuture<List<PhotoSortWrapper>>> photoScores = createFetchers(search, pageFrom, pageTo);
    waitForAllRequestsToBeDone(photoScores);
    List<PhotoSortWrapper> myPhotos = combineDataToList(photoScores);
    return myPhotos
            .stream()
            .sorted(Comparator.comparingDouble(PhotoSortWrapper::getScore).reversed())
            .toList();
  }

  private List<PhotoSortWrapper> combineDataToList(List<CompletableFuture<List<PhotoSortWrapper>>> fetchers){
    return fetchers.stream()
            .map(future -> {
              try {
                return future.get();
              } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
              }
            })
            .flatMap(Collection::stream).toList();
  }

  private List<CompletableFuture<List<PhotoSortWrapper>>> createFetchers(PhotoSearchDto search, int pageFrom, int pageTo){
    List<CompletableFuture<List<PhotoSortWrapper>>> photoScores = new ArrayList<>();
    for (int i = pageFrom; i <= pageTo; i++){
      photoScores.add(fetchPhotosAndComputeScore(search, i));
    }
    return photoScores;
  }

  private void waitForAllRequestsToBeDone(List<CompletableFuture<List<PhotoSortWrapper>>> futures){
    CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0])).join();
  }

  private PhotoSearchDto getSimplifiedSearch(PhotoSearchDto searchDto){
    PhotoSearchDto simpleSearch = new PhotoSearchDto();
    simpleSearch.setQuery(searchDto.getQuery());
    return simpleSearch;
  }


  @Async
  CompletableFuture<List<PhotoSortWrapper>> fetchPhotosAndComputeScore(PhotoSearchDto search, int page){
    Logger.getLogger("flickr").log(Level.INFO, "Started page " + page);
    CompletableFuture<List<PhotoSortWrapper>> future = CompletableFuture.supplyAsync(() -> {
      try {
        List<Photo> fetcherPhotos = flickrPhotosFetcher.searchFlickr(getSimplifiedSearch(search), page);
        return fetcherPhotos.stream()
                .map(PhotoReadDto::new)
                .map(photo -> {
                  PhotoComputeScore score = scoreComputingStrategy.computePhotoScore(photo, search);
                  return new PhotoSortWrapper(photo, score.getScore(), score.getScoreStats());
                })
                .toList();
      } catch (FlickrException e) {
        throw new RuntimeException(e);
      }
    });
    Logger.getLogger("flickr").log(Level.INFO, "Ended page " + page);
    return future;
  }
}
