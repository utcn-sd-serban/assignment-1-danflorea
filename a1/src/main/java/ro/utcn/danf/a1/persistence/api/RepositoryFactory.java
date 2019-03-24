package ro.utcn.danf.a1.persistence.api;

public interface RepositoryFactory {

    UserRepository createUserRepository();
    PostRepository createPostRepository();
    TagRepository createTagRepository();
    PostTagRepository createPostTagRepository();

}
