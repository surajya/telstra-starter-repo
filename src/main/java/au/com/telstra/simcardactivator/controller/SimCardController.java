package au.com.telstra.simcardactivator.controller;


import au.com.telstra.simcardactivator.entity.SimCardTransaction;
import au.com.telstra.simcardactivator.repository.SimCardTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;


@RestController
@RequestMapping("/simcards")
@Slf4j
public class SimCardController {

    @Autowired
    private SimCardTransactionRepository repository;

    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestParam String iccid, @RequestParam String customerEmail) {
        // Sending a POST request to SimCardActuato
        log.info(iccid);
        String url = "http://localhost:8444/actuate";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, iccid, Boolean.class);

        // Saving the transaction
        boolean isActive = response.getBody() != null && response.getBody();
        SimCardTransaction transaction = new SimCardTransaction(iccid, customerEmail, isActive);
        repository.save(transaction);

        return ResponseEntity.ok("Transaction saved successfully!");
    }

    @GetMapping("/query")
    public ResponseEntity<?> getSimCardTransaction(@RequestParam Long simCardId) {
        Optional<SimCardTransaction> transaction = repository.findById(simCardId);

        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        } else {
            return ResponseEntity.status(404).body("Transaction not found!");
        }
    }

    @GetMapping("/sim")
    public String getSim(){
        return "successfully get";
    }
}

