import { EisTransactionAttachment } from 'src/app/shared/models/eis-transaction-attachment';

export interface EisDataStats {

   availableYears: Set<number>;
   statuses: EisDataStat[];
}

export enum EisSubmissionStatus {

   All = "All",
   NotStarted = "Not Started",
   QaEmissions = "QA Emissions",
   QaFacility = "QA Facility",
   ProdEmissions = "Prod Emissions",
   ProdFacility = "Prod Facility",
   Complete = "Complete"
}

export interface EisSearchCriteria {

   year: number;
   status?: EisSubmissionStatus;
}

export interface EisData {

   criteria: EisSearchCriteria;
   reports: EisDataReport[];
}

export interface EisDataReport {

   emissionsReportId: number;
   agencyFacilityId: string;
   attachment: EisTransactionAttachment;
   comments: string;
   eisProgramId: string;
   facilityName: string;
   lastSubmissionStatus: EisSubmissionStatus;
   lastTransactionId: string;
   passed: boolean;
   reportingYear: number;
}

export interface EisReportStatusUpdate {

   submissionStatus: EisSubmissionStatus;
   emissionsReportIds: Set<number>;
}

export interface EisDataStat {

   count: number;
   status: EisSubmissionStatus;
}
