package ro.utcn.danf.a1.persistence.api;

import ro.utcn.danf.a1.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(int id);

    void remove(Post post);

    List<Post> findAll();

}
