package ro.utcn.danf.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.danf.a1.model.Post;
import ro.utcn.danf.a1.model.PostTag;
import ro.utcn.danf.a1.persistence.api.PostTagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcPostTagRepository implements PostTagRepository {
    private final JdbcTemplate template;

    @Override
    public PostTag save(PostTag postTag) {
        if(postTag.getPosttagid() != 0){
            update(postTag);
        }else{
            int id = insert(postTag);
            postTag.setPosttagid(id);
        }
        return postTag;
    }

    @Override
    public Optional<PostTag> findById(int id) {
            List<PostTag> posts = template.query("SELECT * FROM post_tag WHERE posttagid = ?",
                    (resultSet, i) ->
                            new PostTag(resultSet.getInt("posttagid"),
                                    resultSet.getInt("postid"),
                                    resultSet.getInt("tagid")));
            return posts.isEmpty() ? Optional.empty() : Optional.of(posts.get(0));
    }

    @Override
    public void remove(PostTag postTag) {
        template.update("DELETE FROM post_tag WHERE posttagid = ?", postTag.getPosttagid());
    }

    @Override
    public List<PostTag> findAll() {
        return template.query("SELECT * FROM post_tag", (resultSet, i) ->
                new PostTag(resultSet.getInt("posttagid"),
                        resultSet.getInt("postid"),
                        resultSet.getInt("tagid")));
    }

    private int insert(PostTag postTag) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("post_tag");
        insert.setGeneratedKeyName("posttagid");
        Map<String,Object> data = new HashMap<>();
        data.put("postid", postTag.getPostid());
        data.put("tagid", postTag.getTagid());

        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(PostTag postTag) {
        template.update("UPDATE post SET postid = ?, tagid = ?, WHERE posttagid = ?",
                postTag.getPostid(), postTag.getTagid(), postTag.getPosttagid());
    }
}
