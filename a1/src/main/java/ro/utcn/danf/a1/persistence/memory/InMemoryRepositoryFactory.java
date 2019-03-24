package ro.utcn.danf.a1.persistence.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.danf.a1.persistence.api.*;

@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final InMemoryPostRepository postRepository = new InMemoryPostRepository();
    private final InMemoryTagRepository tagRepository = new InMemoryTagRepository();
    private final InMemoryPostTagRepository postTagRepository = new InMemoryPostTagRepository();

    @Override
    public UserRepository createUserRepository() {
        return userRepository;
    }

    @Override
    public PostRepository createPostRepository() {
        return postRepository;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepository;
    }

    @Override
    public PostTagRepository createPostTagRepository() {
        return postTagRepository;
    }
}
