import { ReleasePointApportionment } from './release-point-apportionment';

export class Process {
  id: number;
  emissionsUnitId: number;
  emissionsProcessIdentifier: string;
  sccCode: string;
  sccShortName: string;
  description: string;
  releasePoints: ReleasePointApportionment[];
}
