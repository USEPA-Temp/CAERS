import { FacilitySite } from "src/app/shared/models/facility-site";

export class EmissionsReport {
  id: number;
  facilityId: string;
  eisProgramId: string;
  status: string;
  year: number;
  facilitySite: FacilitySite;
}
