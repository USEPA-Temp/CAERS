import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { ReportingPeriod } from 'src/app/shared/models/reporting-period';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { AircraftEngineTypeCode } from './aircraft-engine-type-code';

export class Process {
  id: number;
  emissionsUnitId: number;
  aircraftEngineTypeCode: AircraftEngineTypeCode;
  operatingStatusCode: BaseCodeLookup;
  emissionsProcessIdentifier: string;
  statusYear: number;
  sccCode: string;
  sccDescription: string;
  sccShortName: string;
  description: string;
  comments: string;
  releasePointAppts: ReleasePointApportionment[];
  reportingPeriods: ReportingPeriod[];
  emissionsUnit: EmissionUnit;
}
