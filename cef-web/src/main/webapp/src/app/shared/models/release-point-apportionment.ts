import { ReleasePoint } from './release-point';
import { BaseCodeLookup } from './base-code-lookup';

export class ReleasePointApportionment {
  id: number;
  percent: number;
  releasePointId: number;
  releasePointIdentifier: string;
  releasePointDescription: string;
  releasePointTypeCode: BaseCodeLookup;
  releasePoint: ReleasePoint;
}
