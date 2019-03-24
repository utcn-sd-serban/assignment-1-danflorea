package ro.utcn.danf.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.danf.a1.exception.ObjectNotFoundException;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Tag> showTags(){
        return repositoryFactory.createTagRepository().findAll().stream().collect(Collectors.toList());
    }

    @Transactional
    public Tag createTag(String title){
        return repositoryFactory.createTagRepository().save(new Tag(title));
    }

    public void addTagToQuestion(String tagTitle, int questionId){
       Tag tag = repositoryFactory.createTagRepository().findByTagTitle(tagTitle).orElseThrow(ObjectNotFoundException::new);
       Post question = repositoryFactory.createPostRepository().findById(questionId).orElseThrow(ObjectNotFoundException::new);
       repositoryFactory.createPostTagRepository().save(new PostTag(question.getPostid(), tag.getTagid()));
    }

}
