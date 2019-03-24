package ro.utcn.danf.a1.persistence.memory;

import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.persistence.api.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, SiteUser> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Optional<SiteUser> findByUsername(String username) {
        return data.values().stream().filter(x->x.getUsername().equals(username)).findFirst();
    }

    @Override
    public List<SiteUser> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public SiteUser save(SiteUser siteUser) {
        if (siteUser.getUserid() == null) {
            siteUser.setUserid(currentId.incrementAndGet());
        }
        data.put(siteUser.getUserid(), siteUser);
        return siteUser;
    }
}
