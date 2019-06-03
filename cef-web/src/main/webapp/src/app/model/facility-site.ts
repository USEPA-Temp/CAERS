import { EmissionUnit } from '../reports/model/emission-unit';
import { ReleasePoint } from '../reports/model/release-point';
import { EmissionsReport } from './emissions-report';

export class FacilitySite {
  id: number;
  emissionsReport: EmissionsReport;
  emissionsUnits: EmissionUnit[];
  releasePoints: ReleasePoint[];
}
