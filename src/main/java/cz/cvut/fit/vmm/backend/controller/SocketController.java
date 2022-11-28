package cz.cvut.fit.vmm.backend.controller;

import com.flickr4java.flickr.FlickrException;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import cz.cvut.fit.vmm.backend.dto.SocketResponseProgress;
import cz.cvut.fit.vmm.backend.service.FlickrService;
import cz.cvut.fit.vmm.backend.service.PhotoSearchRerankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class SocketController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  private FlickrService flickrService;

  @Autowired
  private PhotoSearchRerankingService photoSearchRerankingService;

  @MessageMapping("/search")
  public void sendSearchResults(@Payload PhotoSearchDto search) throws InterruptedException, FlickrException, ExecutionException {
    List<PhotoSortWrapper> allPhotosRepository = new ArrayList<>();
    double cycles = search.getPagesToFetch() / 10;
    for(int i = 0; i < cycles; i++){
      int from = 1 + i * 10;
      int to = 10 + i * 10;
      List<PhotoSortWrapper> photos = photoSearchRerankingService.searchPhotos(search, from, to);
      allPhotosRepository.addAll(photos);
      List<PhotoSortWrapper> bestPhotos = sortBestPhotos(allPhotosRepository);
      double progress = computeProgress(i, cycles);
      SocketResponseProgress payload = new SocketResponseProgress(allPhotosRepository.size(), progress, bestPhotos);
      sendPayload(payload);
    }
  }

  private double computeProgress(int i, double cycles){
    return ((i + 1) / cycles) * 100;
  }
  private List<PhotoSortWrapper> sortBestPhotos(List<PhotoSortWrapper> allPhotos){
    return allPhotos.stream()
            .distinct()
            .sorted(Comparator.comparingDouble(PhotoSortWrapper::getScore).reversed())
            .limit(100)
            .toList();
  }

  private void sendPayload(SocketResponseProgress payload){
    simpMessagingTemplate.convertAndSend("/topic/news", payload);
  }

  private void sendPhotos(List<PhotoSortWrapper> photos){
    simpMessagingTemplate.convertAndSend("/topic/news", photos);
  }


}
