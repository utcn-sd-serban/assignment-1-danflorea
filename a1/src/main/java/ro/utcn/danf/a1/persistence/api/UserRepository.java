package ro.utcn.danf.a1.persistence.api;

import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.SiteUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<SiteUser> findByUsername(String username);
    List<SiteUser> findAll();
    SiteUser save(SiteUser siteUser);

}
