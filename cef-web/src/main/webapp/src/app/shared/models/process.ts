import { ReleasePointApportionment } from './release-point-apportionment';

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
  releasePointAppts: ReleasePointApportionment[];
}
