package ro.utcn.danf.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.danf.a1.exception.ObjectNotFoundException;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.PostRepository;
import ro.utcn.danf.a1.persistence.api.PostTagRepository;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostManagementService {

    private final RepositoryFactory repositoryFactory;
    private static final String QUESTION = "QUESTION";
    private static final String ANSWER = "ANSWER";
    @Transactional
    public List<Post> showQuestions(){
        return repositoryFactory.createPostRepository().findAll().stream().
                filter(x->x.getPosttype().equals(QUESTION)).
                sorted(Comparator.comparing(Post::getCreationdate, Comparator.nullsLast(Comparator.reverseOrder()))).
                collect(Collectors.toList());
    }

    @Transactional
    public void askQuestion(SiteUser currentUser, String text, String title){
        repositoryFactory.createPostRepository().save(
                new Post(currentUser.getUserid(),null,title,text,QUESTION, Calendar.getInstance().getTime()));
    }

    @Transactional
    public void postAnswer(SiteUser currentUser, int questionId, String text, String title)
            throws ObjectNotFoundException {
        PostRepository postRepository = repositoryFactory.createPostRepository();
        postRepository.findById(questionId).orElseThrow(ObjectNotFoundException::new);
        postRepository.save(
                new Post(currentUser.getUserid(),questionId,title,text,ANSWER, Calendar.getInstance().getTime()));
    }

    public List<Post> filterQuestionsByTag(String tagTitle)
        throws ObjectNotFoundException{
        Tag tag = repositoryFactory.createTagRepository().findByTagTitle(tagTitle).orElseThrow(ObjectNotFoundException::new);
        List<Post> postList = repositoryFactory.createPostRepository().findAll();

        List<Post> questionList = postList.stream().filter(x->x.getPosttype().equals(QUESTION)).collect(Collectors.toList());
        List<PostTag> postTagList = repositoryFactory.createPostTagRepository().findAll();

        List<Post> resultList = new ArrayList<>();
        for(Post question : questionList)
            for(PostTag postTag : postTagList)
                if(question.getPostid().equals(postTag.getPostid()) && postTag.getTagid().equals(tag.getTagid()))
                    resultList.add(question);

        return resultList;
    }

    @Transactional
    public List<Post> searchQuestionsByTitle(String title){
        return repositoryFactory.createPostRepository().findAll().stream().
                filter(x->x.getPosttype().equals(QUESTION)).
                filter(x->x.getTitle().toLowerCase().equals(title.toLowerCase())).collect(Collectors.toList());
    }
}
