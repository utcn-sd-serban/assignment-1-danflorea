package ro.utcn.danf.a1.persistence.api;

import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    Tag save(Tag tag);
    List<Tag> findAll();
    Optional<Tag> findByTagTitle(String title);

}
