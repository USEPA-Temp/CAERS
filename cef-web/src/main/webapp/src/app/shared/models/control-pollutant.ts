import { Pollutant } from 'src/app/shared/models/pollutant';
import { Control } from 'src/app/shared/models/control';

export class ControlPollutant {
  id: number;
  pollutant: Pollutant;
  controlId: number;
  percentReduction: number;
  facilitySiteId: number;
}
