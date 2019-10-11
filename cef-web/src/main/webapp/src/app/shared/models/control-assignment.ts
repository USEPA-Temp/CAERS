import { ControlPath } from 'src/app/shared/models/control-path';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';

export class ControlAssignment {
  id: number;
  control: EmissionsReportItem;
  controlPath: ControlPath;
  sequenceNumber: string;
  controlPathChild: ControlPath;
}
