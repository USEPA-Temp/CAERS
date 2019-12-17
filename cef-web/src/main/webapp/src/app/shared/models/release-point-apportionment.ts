import { ReleasePoint } from 'src/app/shared/models/release-point';
import { Process } from 'src/app/shared/models/process';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class ReleasePointApportionment {
  id: number;
  percent: number;
  releasePointId: number;
  releasePointIdentifier: string;
  releasePointDescription: string;
  releasePointTypeCode: BaseCodeLookup;
  releasePoint: ReleasePoint;
  emissionsProcessId: number;
  facilitySiteId: number;
}
