package cz.cvut.fit.vmm.backend.controller;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import cz.cvut.fit.vmm.backend.service.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

  @Autowired
  private FlickrService flickrService;

  @PostMapping("/flickr")
  public List<PhotoReadDto> handleSearch(@RequestBody PhotoSearchDto searchDto) throws FlickrException {
    return flickrService.searchPhotos(searchDto);
  }

  @PostMapping("/rebalanced")
  public List<PhotoSortWrapper> handleRebalancedSearch(@RequestBody PhotoSearchDto searchDto) throws FlickrException {
    return flickrService.searchPhotosWithReranking(searchDto);
  }
}
