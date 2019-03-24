package ro.utcn.danf.a1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.danf.a1.exception.InvalidLoginCredentialException;
import ro.utcn.danf.a1.exception.UserNotFoundException;
import ro.utcn.danf.a1.model.SiteUser;
import ro.utcn.danf.a1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public SiteUser userLogin(String username, String password)
            throws UserNotFoundException, InvalidLoginCredentialException {
        Optional<SiteUser> user = repositoryFactory.createUserRepository().findByUsername(username);
        if (!user.isPresent())
            throw new UserNotFoundException();

        SiteUser siteUser = user.get();
        if(!siteUser.getPassword().equals(password))
            throw new InvalidLoginCredentialException();

        return siteUser;
    }





}
