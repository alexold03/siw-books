package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	@Autowired
    private CredentialsRepository credentialsRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public Credentials getCredentials(Long id) {
        return credentialsRepository.findById(id).orElse(null);
    }

    public Credentials getCredentials(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }

    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    public User getUserByUsername(String username) {
        Credentials credentials = this.getCredentials(username);
        return credentials != null ? credentials.getUser() : null;
    }
}


