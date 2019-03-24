package ro.utcn.danf.a1.persistence.api;

import ro.utcn.danf.a1.model.PostTag;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository {


    PostTag save(PostTag postTag);

    Optional<PostTag> findById(int id);

    void remove(PostTag postTag);

    List<PostTag> findAll();

}
