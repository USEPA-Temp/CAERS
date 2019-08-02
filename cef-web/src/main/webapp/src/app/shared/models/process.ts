import { ReleasePointApportionment } from './release-point-apportionment';
import { ReportingPeriod } from './reporting-period';
import { BaseCodeLookup } from './base-code-lookup';

export class Process {
  id: number;
  emissionsUnitId: number;
  aircraftEngineTypeCodeCode: string;
  operatingStatusCode: BaseCodeLookup;
  emissionsProcessIdentifier: string;
  statusYear: number;
  sccCode: string;
  sccShortName: string;
  description: string;
  comments: string;
  releasePointAppts: ReleasePointApportionment[];
  reportingPeriods: ReportingPeriod[];
}
