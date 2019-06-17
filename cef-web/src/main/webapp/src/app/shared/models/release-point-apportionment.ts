import { ReleasePoint } from './release-point';

export class ReleasePointApportionment {
  id: number;
  percent: number;
  releasePointId: number;
  releasePointIdentifier: string;
  releasePointDescription: string;
  releasePointTypeCode: string;
  releasePoint: ReleasePoint;
}
