package ro.utcn.danf.a1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "siteuser")
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    private String username;
    private String password;
    private String email;
    private boolean banned;
    private String permission;

    public SiteUser(String username, String password, String email, boolean banned, String permission) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.banned = banned;
        this.permission = permission;
    }
}
