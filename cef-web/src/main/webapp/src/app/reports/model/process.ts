import { ReleasePointApportionment } from './release-point-apportionment';

export class Process {
  id: number;
  description: string;
  sourceClassificationCode: string;
  releasePoints: ReleasePointApportionment[];
}