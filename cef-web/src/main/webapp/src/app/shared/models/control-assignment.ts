import { ControlPath } from 'src/app/shared/models/control-path';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';

export class ControlAssignment {
  id: number;
  control: EmissionsReportItem;
  controlPath: ControlPath;
  association: EmissionsReportItem;
  description: string;
  sequenceNumber: string;
}
