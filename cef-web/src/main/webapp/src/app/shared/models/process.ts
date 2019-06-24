import { ReleasePointApportionment } from './release-point-apportionment';
import { ReportingPeriod } from './reporting-period';

export class Process {
  id: number;
  emissionsUnitId: number;
  aircraftEngineTypeCodeCode: string;
  operatingStatusCodeDescription: string;
  emissionsProcessIdentifier: string;
  statusYear: number;
  sccCode: string;
  sccShortName: string;
  description: string;
  comments: string;
  releasePointAppts: ReleasePointApportionment[];
  reportingPeriods: ReportingPeriod[];
}
