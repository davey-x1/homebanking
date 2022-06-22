package mindhub_homebanking.homebanking;

import mindhub_homebanking.homebanking.dto.LoanDTO;
import mindhub_homebanking.homebanking.repositories.ClientLoanRepository;
import mindhub_homebanking.homebanking.repositories.ClientRepository;
import mindhub_homebanking.homebanking.repositories.LoanRepository;
import mindhub_homebanking.homebanking.repositories.models.ClientEntity;
import mindhub_homebanking.homebanking.repositories.models.ClientLoanEntity;
import mindhub_homebanking.homebanking.repositories.models.LoanEntity;
import mindhub_homebanking.homebanking.services.ClientLoanService;
import mindhub_homebanking.homebanking.services.ClientService;
import mindhub_homebanking.homebanking.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	ClientRepository clientRepo;

	@Autowired
	ClientLoanService clientLoanService;

	@Autowired
	ClientService clientService;

	@Autowired
	LoanService loanService;


	private static Logger LOG = LoggerFactory
			.getLogger(HomebankingApplication.class);

	public static void main(String[]   args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(HomebankingApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) throws Exception {
		LoanEntity loanEntity = new LoanEntity();
		HashSet<Integer> paymentOptions = new HashSet<>();
		LoanEntity loanEntity2 = new LoanEntity();
		HashSet<Integer> paymentOptions2 = new HashSet<>();
		LoanEntity loanEntity3 = new LoanEntity();
		HashSet<Integer> paymentOptions3 = new HashSet<>();
		// --------------------------------- //
		loanEntity.addToPaymentsLoan(12);
		loanEntity.addToPaymentsLoan(24);
		loanEntity.addToPaymentsLoan(36);
		loanEntity.addToPaymentsLoan(48);
		loanEntity.addToPaymentsLoan(60);
		loanEntity.setNameOfLoan("Hipotecario");
		loanEntity.setMaxAmountOfLoan(500000);
		loanService.createNewLoan(loanEntity);
		// --------------------------------- //
		loanEntity2.addToPaymentsLoan(6);
		loanEntity2.addToPaymentsLoan(12);
		loanEntity2.addToPaymentsLoan(24);
		loanEntity2.setNameOfLoan("Personal");
		loanEntity2.setMaxAmountOfLoan(100000);
		loanService.createNewLoan(loanEntity2);
		// ---------------------------------- //
		loanEntity3.addToPaymentsLoan(6);
		loanEntity3.addToPaymentsLoan(12);
		loanEntity3.addToPaymentsLoan(24);
		loanEntity3.addToPaymentsLoan(36);
		loanEntity3.setNameOfLoan("Automotriz");
		loanEntity3.setMaxAmountOfLoan(300000);


		loanService.createNewLoan(loanEntity3);
		// -------------------------------------------------
		ClientLoanEntity newClientLoan = new ClientLoanEntity();

		ClientEntity newClient = new ClientEntity();
		newClient.setEmail("asd123@gmail.com");
		newClient.setFirstName("asd");
		newClient.setLastName("qwe");
		newClient.setPassword("asd123");
		clientService.createOrUpdateClient(newClient);
		newClientLoan.setAmountOfLoan(100);
		newClientLoan.setLoanId(0);
		newClientLoan.setNameOfLoan("PERSONAL");
		newClientLoan.setOwnerOfLoan(newClient);
		newClientLoan.setLoanEntity(loanEntity);
		newClientLoan.setPaymentOfLoans(24);
		//clientLoanService.createClientLoan(newClientLoan);
		// ---------------------------------- //
		LOG.info("EXECUTING : command line runner");
	}
}
