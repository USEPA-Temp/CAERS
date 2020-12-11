import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class UserFeedback {
    id: number;
	reportId: string;
	beneficialFunctionalityComments: string;
    difficultFunctionalityComments: string;
    enhancementComments: string;
    intuitiveRating: number;
    dataEntryScreens: number;
    dataEntryBulkUpload: number;
    calculationScreens: number;
    controlsAndControlPathAssignments: number;
    qualityAssuranceChecks: number;
    overallReportingTime: number;
    facilityName: string;
    year: number;
    userId: string;
    userName: string;
    userRole: string;
    programSystemCode: BaseCodeLookup;
    createdDate: string;
    lastModifiedBy: string;
}

export interface UserFeedbackExport {
    facilityName: string;
    userName: string;
    year: number,
    createdDate: string;
    intuitiveRating?: number;
    dataEntryScreens?: number;
    dataEntryBulkUpload?: number;
    calculationScreens?: number;
    controlsAndControlPathAssignments?: number;
    qualityAssuranceChecks?: number;
    overallReportingTime?: number;
    beneficialFunctionalityComments?: string;
    difficultFunctionalityComments?: string;
    enhancementComments?: string;
}
