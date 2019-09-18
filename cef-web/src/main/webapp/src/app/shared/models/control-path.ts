import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';

export class ControlPath {
  id: number;
  description: string;
  releasePointAppts: ReleasePointApportionment[];
}
