package cz.cvut.fit.vmm.backend.dto;

import com.flickr4java.flickr.people.User;
import com.flickr4java.flickr.photos.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoAuthor {
  private String username;
  private String name;
  private String imageUrl;

  public PhotoAuthor(Photo photo) {
    User user = photo.getOwner();
    this.username = user.getUsername();
    this.name = user.getRealName();
    this.imageUrl = getImageUrl(photo);
  }

  private String getImageUrl(Photo photo) {
    String template = "http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg";
    String farm = photo.getFarm();
    String server = photo.getServer();
    String nsid = photo.getOwner().getId();
    return String.format(template, farm, server, nsid);
  }
}
