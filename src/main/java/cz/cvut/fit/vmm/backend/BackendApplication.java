package cz.cvut.fit.vmm.backend;

import com.flickr4java.flickr.photos.PhotosInterface;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class BackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Bean
  @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
  public PhotosInterface flickrPhotosInterface(){
    return FlickrProvider.getInstance().getPhotosInterface();
  }
}
