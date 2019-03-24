package ro.utcn.danf.a1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.persistence.api.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate template;

    @Override
    public Optional<SiteUser> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<SiteUser> findAll() {
        return null;
    }

    @Override
    public SiteUser save(SiteUser siteUser) {
        return null;
    }
}
