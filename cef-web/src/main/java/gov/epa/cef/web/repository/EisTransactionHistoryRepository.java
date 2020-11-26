package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EisTransactionHistory;

public interface EisTransactionHistoryRepository extends CrudRepository<EisTransactionHistory, Long> {

    List<EisTransactionHistory> findByProgramSystemCodeCode(String programSystemCode);
}
