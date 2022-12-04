package cz.cvut.fit.vmm.backend.controller;

import com.flickr4java.flickr.FlickrException;
import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import cz.cvut.fit.vmm.backend.service.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/search")
public class SearchController {

  @Autowired
  private FlickrService flickrService;

  @PostMapping("/flickr")
  public List<PhotoReadDto> handleSearch(@RequestBody PhotoSearchDto searchDto) throws FlickrException, ExecutionException, InterruptedException {
    return flickrService.searchPhotos(searchDto);
  }

  @PostMapping("/rebalanced")
  public List<PhotoSortWrapper> handleRebalancedSearch(@RequestBody PhotoSearchDto searchDto) throws FlickrException, ExecutionException, InterruptedException {
    return flickrService.searchPhotosWithReranking(searchDto);
  }

  @PostMapping("/rebalanced-without-parallelization")
  public List<PhotoSortWrapper> handleClassicRebalancedSearch(@RequestBody PhotoSearchDto searchDto) throws FlickrException, ExecutionException, InterruptedException {
    return flickrService.searchPhotosWithoutParallelization(searchDto);
  }
}
