package ro.utcn.danf.a1.persistence.memory;

import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.TagRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTagRepository implements TagRepository {

    private final Map<Integer, Tag> data = new HashMap<>();
    private AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public synchronized Tag save(Tag tag) {
        if (tag.getTagid() == null) {
            tag.setTagid(currentId.incrementAndGet());
        }
        data.put(tag.getTagid(), tag);
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Tag> findByTagTitle(String title) {
        return data.values().stream().filter(x->x.getTitle().equals(title)).findFirst();
    }
}
