import { EisSubmissionStatus } from 'src/app/shared/models/eis-data';

export class EisTranactionHistory {
    id: number;
    agencyCode: string;
    createdDate: Date;
    eisSubmissionStatus: EisSubmissionStatus;
    transactionId: string;
    submitterName: string;
}
