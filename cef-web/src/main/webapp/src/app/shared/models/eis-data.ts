
export interface EisDataStats {

   availableYears: number[];
   statuses: EisDataStat[];
}

export enum EisSubmissionStatus {

   All = "All",
   QaEmissions = "QA Emissions",
   QaFacility = "QA Facility",
   ProdEmissions = "Prod Emissions",
   ProdFacility = "Prod Facility"
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
   comments: string;
   eisProgramId: string;
   facilityName: string;
   lastSubmissionStatus: EisSubmissionStatus;
   lastTransactionId: string;
   passed: boolean;
   reportingYear: number;
}

export interface EisDataStat {

   count: number;
   status: EisSubmissionStatus;
}
