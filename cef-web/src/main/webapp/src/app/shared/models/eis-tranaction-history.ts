import { EisSubmissionStatus } from 'src/app/shared/models/eis-data';
import { EisTransactionAttachment } from 'src/app/shared/models/eis-transaction-attachment';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class EisTranactionHistory {
    id: number;
    programSystemCode: BaseCodeLookup;
    createdDate: Date;
    eisSubmissionStatus: EisSubmissionStatus;
    transactionId: string;
    submitterName: string;
    attachment: EisTransactionAttachment;
}
