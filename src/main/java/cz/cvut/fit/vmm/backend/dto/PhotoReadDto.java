package cz.cvut.fit.vmm.backend.dto;

import com.flickr4java.flickr.photos.GeoData;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.tags.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhotoReadDto {
  private String url;
  private String title;
  private String author;
  private String id;
  private Date dateTaken;
  private Date datePosted;
  private Collection<Tag> tags;
  private GeoData geo;

  public PhotoReadDto(Photo photo) {
    this.url = photo.getLargeUrl();
    this.title = photo.getTitle();
    this.author = photo.getOwner().getUsername();
    this.id = photo.getId();
    this.dateTaken = photo.getDateTaken();
    this.datePosted = photo.getDatePosted();
    this.tags = photo.getTags();
    this.geo = photo.getGeoData();
  }
}
