import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';

export class UserFacilityAssociation {

  id: number;
  masterFacilityRecord: MasterFacilityRecord;
  userRoleId: number;
  approved: boolean;
  cdxUserId: string;
  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
  roleId: number;
  roleDescription: string;

  checked: boolean;
}
