import { Injectable } from '@angular/core';
import { ReportStatus } from 'src/app/shared/enums/report-status';

@Injectable({
  providedIn: 'root'
})
export class UtilityService {

  constructor() { }

  static isInProgressStatus(status: string): boolean {
    return ReportStatus.IN_PROGRESS === status || ReportStatus.RETURNED === status;
  }
}
