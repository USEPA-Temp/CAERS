import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsReport } from './emissions-report';

export class FacilitySite {
  id: number;
  emissionsReport: EmissionsReport;
  emissionsUnits: EmissionUnit[];
  releasePoints: ReleasePoint[];
}
