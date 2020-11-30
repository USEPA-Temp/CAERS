import { AppRole } from 'src/app/shared/enums/app-role';

export class User {
  cdxUserId: string;
  email: string;
  firstName: string;
  fullName: string;
  lastName: string;
  role: AppRole;
  userRoleId: number;
  programSystemCode: string;

  // these methods can be used for easier role checking
  public canReview() {
    return this.isReviewer() || this.isAdmin();
  }

  public isAdmin() {
    return AppRole.CAER_ADMIN === this.role;
  }

  public isNeiCertifier() {
    return AppRole.NEI_CERTIFIER === this.role;
  }

  public isPreparer() {
    return AppRole.PREPARER === this.role;
  }

  public isReviewer() {
    return AppRole.REVIEWER === this.role;
  }
}
