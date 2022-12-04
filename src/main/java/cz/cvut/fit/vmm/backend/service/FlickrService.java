package cz.cvut.fit.vmm.backend.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlickrService {

  @Autowired
  private FlickrPhotosFetcher flickrPhotosFetcher;

  @Autowired
  private PhotoSearchRerankingService photoSearchRerankingService;

  public List<PhotoReadDto> searchPhotos(PhotoSearchDto search) throws FlickrException {
    List<Photo> photos = flickrPhotosFetcher.searchFlickr(search);
    return photos
            .stream()
            .limit(30)
            .map(PhotoReadDto::new)
            .toList();
  }

  public List<PhotoSortWrapper> searchPhotosWithReranking(PhotoSearchDto search) throws FlickrException {
    List<PhotoSortWrapper> sortedPhotos = photoSearchRerankingService.searchPhotos(search, 0, search.getPagesToFetch());
    return sortedPhotos
            .stream()
            .distinct()
            .limit(30)
            .toList();
  }

  public List<PhotoSortWrapper> searchPhotosWithoutParallelization(PhotoSearchDto search) throws FlickrException {
    List<PhotoSortWrapper> sortedPhotos = photoSearchRerankingService.searchPhotosWithoutParallelization(search);
    return sortedPhotos
            .stream()
            .distinct()
            .limit(30)
            .toList();
  }

}
