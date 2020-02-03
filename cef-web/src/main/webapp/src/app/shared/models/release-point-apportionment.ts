import { ReleasePoint } from 'src/app/shared/models/release-point';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { ControlPath } from './control-path';

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
  controlPath: ControlPath;
}
