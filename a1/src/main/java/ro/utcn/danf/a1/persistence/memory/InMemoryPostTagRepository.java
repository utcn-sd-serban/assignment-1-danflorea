package ro.utcn.danf.a1.persistence.memory;

import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.persistence.api.PostTagRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPostTagRepository implements PostTagRepository {

    private final Map<Integer, PostTag> data = new HashMap<>();
    private AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public PostTag save(PostTag postTag) {
        if (postTag.getPosttagid() == null) {
            postTag.setPosttagid(currentId.incrementAndGet());
        }
        data.put(postTag.getPosttagid(), postTag);
        return postTag;
    }

    @Override
    public Optional<PostTag> findById(int id) {
            return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(PostTag postTag) {
        data.remove(postTag.getPosttagid());
    }

    @Override
    public List<PostTag> findAll() {
        return new ArrayList<>(data.values());
    }
}
