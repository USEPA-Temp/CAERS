import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';

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