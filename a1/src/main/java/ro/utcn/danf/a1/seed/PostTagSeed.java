package ro.utcn.danf.a1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.PostTagRepository;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;
import ro.utcn.danf.a1.persistence.api.TagRepository;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostTagSeed implements CommandLineRunner {

    private final RepositoryFactory factory;

    @Override
    public void run(String... args) throws Exception {
        PostTagRepository repository = factory.createPostTagRepository();
        if (repository.findAll().isEmpty())
        {
            repository.save(new PostTag(1,1,1));
            repository.save(new PostTag(2,2,1));
        }
    }
}
