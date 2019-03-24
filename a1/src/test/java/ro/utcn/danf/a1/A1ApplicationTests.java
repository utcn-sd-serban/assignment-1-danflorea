package ro.utcn.danf.a1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ro.utcn.danf.a1.exception.InvalidLoginCredentialException;
import ro.utcn.danf.a1.exception.ObjectNotFoundException;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.model.Tag;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;
import ro.utcn.danf.a1.persistence.memory.InMemoryRepositoryFactory;
import ro.utcn.danf.a1.service.PostManagementService;
import ro.utcn.danf.a1.service.TagManagementService;
import ro.utcn.danf.a1.service.UserManagementService;

import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class A1ApplicationTests {

	private static RepositoryFactory createMockedUserFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();
		factory.createUserRepository().save(new SiteUser(582, "ju","p1", "ju@byz.com",false, "yes"));
		factory.createUserRepository().save(new SiteUser(500, "be","p1", "be@byz.com",false, "yes"));
		return factory;
	}

	private RepositoryFactory createMockedPostFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();
		factory.createPostRepository().save(new Post(527,582, null,"Java", "Makes me want to commit my code","QUESTION", Calendar.getInstance().getTime()));
		factory.createPostRepository().save(new Post(528,582, null,"Jawa", "Are pretty friendly","QUESTION", Calendar.getInstance().getTime()));
		return factory;
	}

	private RepositoryFactory createMockedTagFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();
		factory.createTagRepository().save(new Tag(11,"java"));
		factory.createTagRepository().save(new Tag(12,"also java"));
		return factory;
	}

	private RepositoryFactory createMockedPostTagFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();
		factory.createPostTagRepository().save(new PostTag(100,527,11));
		return factory;
	}

	private Post generateDummyPost(Integer id){
		return new Post (id, null,"Dummy", "Dummy question post","QUESTION", Calendar.getInstance().getTime());
	}

	private SiteUser generateDummyUser(Integer id){
		return new SiteUser(id, "Dum","my", "dum@my.com",false, "yes");
	}



	@Test
	public void testSiteUserLogin() {
		// arrange - create a mocked factory and a service backed up by this factory
		RepositoryFactory factory = createMockedUserFactory();
		UserManagementService service = new UserManagementService(factory);

		// act - remove a student with a well-known ID
		service.userLogin("ju","p1");

		// assert - expect that the student was removed from the repository and the other student is still there
		Assert.assertEquals(1, factory.createUserRepository().findAll().size());
	}

	@Test(expected = InvalidLoginCredentialException.class)
	public void testSiteUserLoginThrowsException() {
		// arrange - create a mocked factory and a service backed up by this factory
		RepositoryFactory factory = createMockedUserFactory();
		UserManagementService service = new UserManagementService(factory);

		// act - remove a student with a well-known ID
		service.userLogin("js","p2");
	}

	@Test
	public void testListAllExistingPostsWorks(){

		RepositoryFactory factory = createMockedPostFactory();
		PostManagementService service = new PostManagementService(factory);

		List<Post> currentQuestions = service.showQuestions();

		Assert.assertEquals(currentQuestions.size(), 2);
	}

	@Test
	public void testAskNewQuestion(){

		RepositoryFactory factory = createMockedPostFactory();
		PostManagementService service = new PostManagementService(factory);

		SiteUser du = generateDummyUser(1111);
		Post dp = generateDummyPost(1111);
		service.askQuestion(du,dp.getTitle(),dp.getText());

		Assert.assertEquals(service.showQuestions().size(), 3);
	}

	@Test
	public void testSearchQuestionByTitle(){

		RepositoryFactory factory = createMockedPostFactory();
		PostManagementService service = new PostManagementService(factory);

		List<Post> current = service.searchQuestionsByTitle("Java");

		Assert.assertEquals(current.size(), 1);
	}

	@Test
	public void testSearchQuestionByTitleFails(){
		RepositoryFactory factory = createMockedPostFactory();
		PostManagementService service = new PostManagementService(factory);

		List<Post> current = service.searchQuestionsByTitle("Java11");

		Assert.assertEquals(current.size(), 0);
	}

	@Test
	public void testFilterQuestionsByTag(){
		RepositoryFactory factory = createMockedPostFactory();
		PostManagementService service = new PostManagementService(factory);

		List<Post> current = service.filterQuestionsByTag("java");

		Assert.assertEquals(current.size(),1);
	}

	@Test (expected = ObjectNotFoundException.class)
	public void testAddTagToQuestion(){
		RepositoryFactory factory = createMockedTagFactory();
		TagManagementService service = new TagManagementService(factory);
		RepositoryFactory postFactory = createMockedPostFactory();
		PostManagementService postService = new PostManagementService(postFactory);

		service.addTagToQuestion("NewTag", 486);
	}



}
