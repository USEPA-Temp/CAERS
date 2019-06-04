import { BaseCodeLookup } from 'src/app/model/base-code-lookup';
import { EmissionUnit } from 'src/app/reports/model/emission-unit';
import { ReleasePoint } from 'src/app/reports/model/release-point';
import { EmissionsReport } from 'src/app/model/emissions-report';

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
  operatingStatusCode: BaseCodeLookup ;
  naicsCode: BaseCodeLookup ;
  emissionsReport: EmissionsReport;
  emissionsUnits: EmissionUnit[];
  releasePoints: ReleasePoint[];
}
