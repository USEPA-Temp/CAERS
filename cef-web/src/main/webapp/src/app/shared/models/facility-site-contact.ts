import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

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
  stateCode: string;
  postalCode: string;
  mailingStreetAddress: string;
  mailingCity: string;
  mailingStateCode: string;
  mailingPostalCode: string;
}
