import { FacilitySite } from "src/app/shared/models/facility-site";
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class EmissionsReport {
  id: number;
  facilityId: string;
  eisProgramId: string;
  masterFacilityRecordId: number;
  status: string;
  programSystemCode: BaseCodeLookup;
  validationStatus: string;
  year: number;
  facilitySite: FacilitySite;
  hasSubmitted: boolean;
  fileName: string;
}
