import { EisSubmissionStatus } from 'src/app/shared/models/eis-data';
import { EisTransactionAttachment } from 'src/app/shared/models/eis-transaction-attachment';

export class EisTranactionHistory {
    id: number;
    agencyCode: string;
    createdDate: Date;
    eisSubmissionStatus: EisSubmissionStatus;
    transactionId: string;
    submitterName: string;
    attachment: EisTransactionAttachment;
}
