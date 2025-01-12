package au.com.telstra.simcardactivator.repository;


import au.com.telstra.simcardactivator.entity.SimCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardTransactionRepository extends JpaRepository<SimCardTransaction, Long> {}

