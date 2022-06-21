package mindhub_homebanking.homebanking;

/* ---------------------------------- */

import java.util.List;
import mindhub_homebanking.homebanking.dto.ClientDTO;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/* ---------------------------------- */

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> listDTO = service.getAllClients().stream().map(ClientDTO::new).collect(toList());
        return new ResponseEntity<List<ClientDTO>>(listDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id) {
        ClientEntity entity = service.getClientById(id);
        ClientDTO entityDTO = new ClientDTO(entity);
        return new ResponseEntity<ClientDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable("id") String email) {
        ClientEntity entity = service.getClientByEmail(email);
        ClientDTO entityDTO = new ClientDTO(entity);
        return new ResponseEntity<ClientDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> createOrUpdateClient(@RequestBody ClientEntity client) {
        ClientEntity updated = service.createOrUpdateClient(client);
        ClientDTO entityDTO = new ClientDTO(updated);
        return new ResponseEntity<ClientDTO>(entityDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteClientById(@PathVariable("id") Long id) {
        service.deleteClientById(id);
        return HttpStatus.FORBIDDEN;
    }
}
