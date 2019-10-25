import { FacilitySite } from "src/app/shared/models/facility-site";

export class EmissionsReport {
  id: number;
  facilityId: string;
  eisProgramId: string;
  agencyCode: string;
  status: string;
  agencyCode: string;
  validationStatus: string;
  year: number;
  facilitySite: FacilitySite;
}
