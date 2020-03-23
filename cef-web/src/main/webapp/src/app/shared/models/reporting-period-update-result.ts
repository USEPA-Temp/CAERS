import { ReportingPeriod } from 'src/app/shared/models/reporting-period';

export class ReportingPeriodUpdateResult {
    reportingPeriod: ReportingPeriod;
    updatedEmissions: string[];
    notUpdatedEmissions: string[];
    failedEmissions: string[];
}
