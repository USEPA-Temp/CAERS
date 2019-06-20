import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

export class EmissionsReportItem {
  id: number;
  identifier: string;
  description: string;
  type: BaseReportUrl;

  constructor(id: number, identifier: string, description: string, type: BaseReportUrl) {
    this.id = id;
    this.identifier = identifier;
    this.description = description;
    this.type = type;
  }

  get typeDesc(): string {
    switch (this.type) {
      case BaseReportUrl.EMISSIONS_UNIT:
        return 'Emissions Unit';
      case BaseReportUrl.EMISSIONS_PROCESS:
        return 'Emissions Process';
      case BaseReportUrl.RELEASE_POINT:
        return 'Release Point';
    }
  }
}
