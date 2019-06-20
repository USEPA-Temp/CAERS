import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';

export class Control {
  id: number;
  facilitySiteId: number;
  identifier: string;
  description: string;
  percentCapture: number;
  percentControl: number;
  assignments: ControlAssignment[];
  pollutants: ControlPollutant[];
}