package mindhub_homebanking.homebanking.services;

/* ---------------------------------- */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/* ---------------------------------- */

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<ClientEntity> getAllClients() {
        List<ClientEntity> clientList = repository.findAll();

        if(clientList.size() > 0) {
            return clientList;
        } else {
            return new ArrayList<ClientEntity>();
        }
    }

    public ClientEntity getClientById(Long id) {
        Optional<ClientEntity> client = repository.findById(id);

        if(client.isPresent()) {

            return client.get();

        } else {
            System.out.println("No client record exist for given id");
            return null;
        }

    }

    public ClientEntity getClientByEmail(String email) {
        Optional<ClientEntity> client = repository.findByEmail(email);

        if(client.isPresent()) {
            return client.get();

        } else {
            System.out.println("No client record exist for given id");
            return null;
        }

    }

    public ClientEntity createOrUpdateClient(ClientEntity entity)
    {
        Optional<ClientEntity> client = repository.findById(entity.getId());
        if(client.isPresent()){
            ClientEntity newEntity = client.get();
            newEntity.setEmail(entity.getEmail());
            newEntity.setFirstName(entity.getFirstName());
            newEntity.setLastName(entity.getLastName());
            newEntity.setPassword(passwordEncoder.encode(entity.getPassword()));
            newEntity = repository.save(newEntity);
            return newEntity;

        } else {

            entity = repository.save(entity);
            return entity;

        }
    }

    public void deleteClientById(Long id)
    {
        Optional<ClientEntity> employee = repository.findById(id);

        if(employee.isPresent())
        {
            repository.deleteById(id);
        }
    }
}