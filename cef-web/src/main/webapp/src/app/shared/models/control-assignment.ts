import { ControlPath } from 'src/app/shared/models/control-path';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { EmissionsReportItem } from 'src/app/shared/models/emissions-report-item';
import { Control } from 'src/app/shared/models/control';

export class ControlAssignment {
  id: number;
  description: string;
  controlPath: ControlPath;
  emissionsUnit: EmissionUnit;
  emissionsProcess: Process;
  releasePoint: ReleasePoint;
  association: EmissionsReportItem;

  control: Control;
}
