package cz.cvut.fit.vmm.backend.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import cz.cvut.fit.vmm.backend.PhotosReranker;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlickrService {

  @Autowired
  private PhotosInterface photosInterface;

  public List<PhotoReadDto> searchPhotos(PhotoSearchDto search) throws FlickrException {
    PhotoList<Photo> photos = searchFlickr(search, 40);
    return photos.stream().map(PhotoReadDto::new).toList();
  }

  public List<PhotoSortWrapper> searchPhotosWithReranking(PhotoSearchDto search) throws FlickrException {
    PhotoList<Photo> photos = searchFlickr(search, 40);
    List<PhotoReadDto> originalPhotos = photos.stream().map(PhotoReadDto::new).toList();
    PhotosReranker photosReranker = new PhotosReranker(originalPhotos, search);
    return photosReranker.sortPhotos();
  }



  private PhotoList<Photo> searchFlickr(PhotoSearchDto search, int photosToLoad) throws FlickrException {
    Set<String> extras = Set.of("date_taken", "owner_name", "geo", "date_upload", "path_alias", "icon_server");
    SearchParameters params = new SearchParameters();
    params.setMedia("photos");
    params.setExtras(Stream.of("media").collect(Collectors.toSet()));
    params.setText(search.getQuery());
    params.setTags(search.getTags());
    params.setMinUploadDate(search.getDateFrom());
    params.setMaxUploadDate(search.getDateTo());
    params.setHasGeo(true);
    params.setExtras(extras);
    params.setLongitude(Float.toString(search.getGeo().getLng()));
    params.setLatitude(Float.toString(search.getGeo().getLat()));
    params.setAccuracy(11);
    return photosInterface.search(params, photosToLoad, 0);
  }

}
