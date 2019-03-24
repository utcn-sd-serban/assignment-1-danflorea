package ro.utcn.danf.a1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postid;
    private Integer userid;
    private Integer parentid;
    private String title;
    private String text;
    private String posttype;
    @Column(name="creationdate")
    @Temporal(TemporalType.DATE)
    private Date creationdate;

    public Post(Integer userid, Integer parentid, String title, String text, String posttype, Date creationdate) {
        this.userid = userid;
        this.parentid = parentid;
        this.title = title;
        this.text = text;
        this.posttype = posttype;
        this.creationdate = creationdate;
    }


}
