package ro.utcn.danf.a1.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.persistence.api.PostRepository;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;
import ro.utcn.danf.a1.persistence.api.UserRepository;

import java.util.Calendar;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostSeed implements CommandLineRunner {

    private final RepositoryFactory factory;


    @Override
    public void run(String... args) throws Exception {
        PostRepository repository = factory.createPostRepository();
        if (repository.findAll().isEmpty())
        {
            repository.save(new Post(1, null,"Java", "Island in the kingdom of Majapahit?","QUESTION", Calendar.getInstance().getTime()));
            repository.save(new Post(2, null,"Java question", "Can I kill myself by calling method myself.remove()?","QUESTION", Calendar.getInstance().getTime()));
            repository.save(new Post(3, null,"Question 3", "Did you ever hear the tragedy of Darth Plagueis the Wise?","QUESTION", Calendar.getInstance().getTime()));
        }
    }
}
