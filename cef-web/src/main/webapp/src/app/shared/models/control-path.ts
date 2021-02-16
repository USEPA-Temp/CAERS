import { ReleasePointApportionment } from 'src/app/shared/models/release-point-apportionment';
import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlPathPollutant } from 'src/app/shared/models/control-path-pollutant';

export class ControlPath {
  id: number;
  description: string;
  releasePointAppts: ReleasePointApportionment[];
  assignments: ControlAssignment[];
  facilitySiteId: number;
  pathId: string;
  percentControl: number;
  pollutants: ControlPathPollutant[];
}
