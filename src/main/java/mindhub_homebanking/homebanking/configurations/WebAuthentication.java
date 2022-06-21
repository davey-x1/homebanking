package mindhub_homebanking.homebanking.configurations;
/* ------------------------------------------ */

import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/* ------------------------------------------ */

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName -> {
            Optional<ClientEntity> client = clientRepository.findByEmail(inputName);

            if (client.isPresent()) {
                System.out.println(client.get());
                if(client.get().getRole() == "CLIENT"){
                    return new User(client.get().getEmail(), client.get().getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENT"));
                } else {
                    return new User(client.get().getEmail(), client.get().getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
            } else {
                System.out.println("Not found");
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}