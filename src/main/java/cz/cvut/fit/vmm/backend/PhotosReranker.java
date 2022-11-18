package cz.cvut.fit.vmm.backend;

import cz.cvut.fit.vmm.backend.dto.PhotoReadDto;
import cz.cvut.fit.vmm.backend.dto.PhotoScore;
import cz.cvut.fit.vmm.backend.dto.PhotoSearchDto;
import cz.cvut.fit.vmm.backend.dto.PhotoSortWrapper;
import cz.cvut.fit.vmm.backend.score_strategy.BasicStrategy;
import cz.cvut.fit.vmm.backend.score_strategy.ScoreComputeStrategy;

import java.util.List;

public class PhotosReranker {
  private List<PhotoReadDto> originalPhotos;
  private PhotoSearchDto searchDto;

  private ScoreComputeStrategy scoreComputeStrategy;

  public PhotosReranker(List<PhotoReadDto> originalPhotos, PhotoSearchDto searchDto) {
    this.originalPhotos = originalPhotos;
    this.searchDto = searchDto;
    this.scoreComputeStrategy = new BasicStrategy(searchDto);
  }

  public List<PhotoSortWrapper> sortPhotos(){
    List<PhotoSortWrapper> sortReadyPhotos = computePhotoScore();
    return sortReadyPhotos.stream()
            .sorted((photo1, photo2) -> (int) (photo1.getScore().getTotal() - photo2.getScore().getTotal()))
            .toList();
  }

  private List<PhotoSortWrapper> computePhotoScore(){
    return originalPhotos.stream()
            .map(photo -> {
              PhotoScore score = scoreComputeStrategy.computePhotoScore(photo);
              return new PhotoSortWrapper(photo, score);
            })
            .toList();
  }
}
