package raf.lazar.diplomski_aorp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService, IService<User, Long> {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public <S extends User> S save(S var1) {
        return null;
    }

    @Override
    public Optional<User> findById(Long var1) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long var1) {

    }
}
