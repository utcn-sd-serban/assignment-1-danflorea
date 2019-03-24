package ro.utcn.danf.a1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.PostRepository;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;
import ro.utcn.danf.a1.persistence.api.TagRepository;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TagSeed implements CommandLineRunner {
    private final RepositoryFactory factory;

    public void run(String... args) throws Exception {
        TagRepository repository = factory.createTagRepository();
        if (repository.findAll().isEmpty())
        {
            repository.save(new Tag(1,"java"));
            repository.save(new Tag(2,"misc"));
        }
    }
}
