import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

export class EmissionsReportItem {
  id: number;
  identifier: string;
  description: string;
  type: string;
  typeDesc: string;

  constructor(id: number, identifier: string, description: string, type: string) {
    this.id = id;
    this.identifier = identifier;
    this.description = description;
    this.type = type;
  }
}
