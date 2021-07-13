import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { FipsCounty } from 'src/app/shared/models/fips-county';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { InventoryYearCodeLookup } from 'src/app/shared/models/inventory-year-code-lookup';
import { FacilityCategoryCode } from 'src/app/shared/models/facility-category-code';

export class MasterFacilityRecord {
  id: number;
  name: string;
  eisProgramId: string;
  latitude: number;
  longitude: number;
  streetAddress: string;
  city: string;
  stateCode: FipsStateCode;
  postalCode: string;
  countyCode: FipsCounty;
  mailingStreetAddress: string;
  mailingCity: string;
  mailingStateCode: FipsStateCode;
  mailingPostalCode: string;
  operatingStatusCode: BaseCodeLookup;
  tribalCode: BaseCodeLookup;
  programSystemCode: BaseCodeLookup;
  statusYear: number;
  agencyFacilityId: string;
  facilityCategoryCode: FacilityCategoryCode;
  facilitySourceTypeCode: InventoryYearCodeLookup;
  description: string;

  associationStatus: string;
}
