import { Pollutant } from 'src/app/shared/models/pollutant';

export class ControlPathPollutant {
  id: number;
  pollutant: Pollutant;
  controlPathId: number;
  percentReduction: number;
  facilitySiteId: number;
}
