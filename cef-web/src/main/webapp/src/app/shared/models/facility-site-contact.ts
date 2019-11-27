import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { FipsStateCode } from './fips-state-code';

export class FacilitySiteContact {
  id: number;
  facilitySiteId: number;
  type: BaseCodeLookup;
  prefix: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  phoneExt: string;
  streetAddress: string;
  city: string;
  stateCode: FipsStateCode;
  postalCode: string;
  county: string;
  countryCode: string;
  mailingStreetAddress: string;
  mailingCity: string;
  mailingStateCode: string;
  mailingPostalCode: string;
  mailingCountryCode: string;
}
