package ro.utcn.danf.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.persistence.api.PostRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class JdbcPostRepository implements PostRepository {
    private final JdbcTemplate template;

    @Override
    public Post save(Post post) {
        if(post.getPostid() != 0){
            update(post);
        }else{
            int id = insert(post);
            post.setPostid(id);
        }
        return post;
    }

    @Override
    public Optional<Post> findById(int id)
    {
        List<Post> posts = template.query("SELECT * FROM post WHERE postid = ?",
                (resultSet, i) ->
                        new Post(resultSet.getInt("postid"),
                                resultSet.getInt("userid"),
                                resultSet.getInt("parentid"),
                                resultSet.getString("title"),
                                resultSet.getString("text"),
                                resultSet.getString("posttype"),
                                resultSet.getTime("creationdate")));
        return posts.isEmpty() ? Optional.empty() : Optional.of(posts.get(0));
    }

    @Override
    public void remove(Post post) {
        template.update("DELETE FROM post WHERE postid = ?", post.getPostid());
    }

    @Override
    public List<Post> findAll() {
        return template.query("SELECT * FROM post", (resultSet, i) ->
                new Post(resultSet.getInt("postid"),
                        resultSet.getInt("userid"),
                        resultSet.getInt("parentid"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getString("posttype"),
                        resultSet.getTime("creationdate")));
    }

    private int insert(Post post) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("post");
        insert.setGeneratedKeyName("postid");
        Map<String,Object> data = new HashMap<>();
        data.put("title", post.getTitle());
        data.put("text", post.getText());

        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Post post) {
       template.update("UPDATE post SET title = ?, text = ?, WHERE postid = ?", post.getTitle(), post.getText(), post.getPostid());
    }
}

