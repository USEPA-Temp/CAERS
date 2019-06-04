import { EmissionUnit } from 'src/app/reports/model/emission-unit';
import { ReleasePoint } from 'src/app/reports/model/release-point';
import { EmissionsReport } from 'src/app/model/emissions-report';
import { NaicsCode } from 'src/app/model/naics-code';
import { OperatingStatusCode } from 'src/app/model/operating-status-code';

export class FacilitySite {
  id: number;
  latitude: number;
  longitude: number;
  streetAddress: string;
  city: string;
  stateCode: string;
  postalCode: string;
  mailingStreetAddress: string;
  mailingCity: string;
  mailingStateCode: string;
  mailingPostalCode: string;
  operatingStatusCode: OperatingStatusCode;
  naicsCode: NaicsCode;
  emissionsReport: EmissionsReport;
  emissionsUnits: EmissionUnit[];
  releasePoints: ReleasePoint[];
}
