package gov.epa.cef.web.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EisTransactionAttachment;

public interface EisTransactionAttachmentRepository extends CrudRepository<EisTransactionAttachment, Long> {

    /**
     * Retrieve an EIS attachment based on transaction id
     * @param transactionId
     * @return
     */
    Optional<EisTransactionAttachment> findByTransactionHistoryTransactionId(String transactionId);

}
