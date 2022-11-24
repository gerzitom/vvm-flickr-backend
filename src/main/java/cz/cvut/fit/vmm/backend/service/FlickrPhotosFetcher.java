package cz.cvut.fit.vmm.backend.service;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlickrPhotosFetcher {

  @Autowired
  private PhotosInterface photosInterface;

  public List<Photo> searchFlickr(PhotoSearchDto search, int page) throws FlickrException {
    SearchParameters params = createSearchParameters(search);
    return photosInterface.search(params, 100, page);
  }

  public List<Photo> searchFlickr(PhotoSearchDto search) throws FlickrException {
    return this.searchFlickr(search, 1);
  }
  private SearchParameters createSearchParameters(PhotoSearchDto search) throws FlickrException {
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
    if(search.getGeo() != null){
      params.setLongitude(Float.toString(search.getGeo().getLng()));
      params.setLatitude(Float.toString(search.getGeo().getLat()));
    }
    params.setAccuracy(11);
    return params;
  }


}
