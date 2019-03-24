package ro.utcn.danf.a1.persistence.memory;

import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.persistence.api.PostRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPostRepository implements PostRepository {

    private final Map<Integer, Post> data = new HashMap<>();
    private AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public synchronized Post save(Post post) {
        if (post.getPostid() == null) {
            post.setPostid(currentId.incrementAndGet());
        }
        data.put(post.getPostid(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Post post) {
        data.remove(post.getPostid());
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(data.values());
    }
    
}
