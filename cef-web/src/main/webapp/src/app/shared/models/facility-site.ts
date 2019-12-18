import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsReport } from 'src/app/shared/models/emissions-report';
import { FacilitySiteContact } from 'src/app/shared/models/facility-site-contact';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';
import { Control } from 'src/app/shared/models/control';

export class FacilitySite {
  id: number;
  name: string;
  eisProgramId: string;
  frsFacilityId: string;
  latitude: number;
  longitude: number;
  streetAddress: string;
  city: string;
  stateCode: string;
  postalCode: string;
  county: string;
  mailingStreetAddress: string;
  mailingCity: string;
  mailingStateCode: string;
  mailingPostalCode: string;
  operatingStatusCode: BaseCodeLookup;
  facilityNAICS: FacilityNaicsCode[];
  emissionsReport: EmissionsReport;
  emissionsUnits: EmissionUnit[];
  releasePoints: ReleasePoint[];
  controls: Control[];
  contacts: FacilitySiteContact[];
  tribalCode: BaseCodeLookup;
  programSystemCode: BaseCodeLookup;
  statusYear: number;
  altSiteIdentifier: string;
  facilityCategoryCode: BaseCodeLookup;
  facilitySourceTypeCode: BaseCodeLookup;
  description: string;
}
