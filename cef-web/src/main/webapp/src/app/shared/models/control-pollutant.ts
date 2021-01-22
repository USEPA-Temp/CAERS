import { Pollutant } from 'src/app/shared/models/pollutant';

export class ControlPollutant {
  id: number;
  pollutant: Pollutant;
  controlId: number;
  percentReduction: number;
  facilitySiteId: number;
}
