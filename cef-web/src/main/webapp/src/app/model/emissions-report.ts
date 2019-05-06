export class EmissionsReport {
  id: number;
  facilityId: string;
  status: string;
  year: number;

  statusIsOneOf(statuses: string[]): boolean {
    for (const status of statuses) {
      if (status === this.status) {
        return true;
      }
    }
    return false;
  }
}