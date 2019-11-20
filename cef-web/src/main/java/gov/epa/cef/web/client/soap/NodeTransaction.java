package gov.epa.cef.web.client.soap;

import net.exchangenetwork.schema.node._2.TransactionStatusCode;

public class NodeTransaction {

    private final TransactionStatusCode statusCode;

    private final String statusDetail;

    private final String transactionId;

    public NodeTransaction(String transactionId,
                           TransactionStatusCode statusCode,
                           String statusDetail) {

        super();

        this.transactionId = transactionId;
        this.statusCode = statusCode;
        this.statusDetail = statusDetail;
    }

    public TransactionStatusCode getStatusCode() {

        return statusCode;
    }

    public String getStatusDetail() {

        return statusDetail;
    }

    public String getTransactionId() {

        return transactionId;
    }
}
