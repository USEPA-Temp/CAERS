
export class EisDataStats {

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

export interface EisDataStat {

   count: number;
   status: EisSubmissionStatus;
}
