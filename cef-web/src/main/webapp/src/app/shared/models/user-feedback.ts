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
    hasSubmitted: boolean;
    hasVisitedPage: boolean;
}