package cz.cvut.fit.vmm.backend;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlickrProvider {
  private Flickr f;
  private RequestContext requestContext;
  private static FlickrProvider instance = new FlickrProvider();

  public static FlickrProvider getInstance() {
    return instance;
  }

  private FlickrProvider() {
    String apiKey = "dfc68700ccd28685a99fc9955c0c9e82";
    String sharedSecret = "6b097542b1ed5fb9";
    f = new Flickr(apiKey, sharedSecret, new REST());
    requestContext = RequestContext.getRequestContext();
    Flickr.debugRequest = false;
    Flickr.debugStream = false;
  }

  public PhotosInterface getPhotosInterface(){
    return f.getPhotosInterface();
  }
}
