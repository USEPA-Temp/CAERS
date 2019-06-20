import { ControlPath } from 'src/app/shared/models/control-path';
import { EmissionUnit } from 'src/app/shared/models/emission-unit';
import { Process } from 'src/app/shared/models/process';
import { ReleasePoint } from 'src/app/shared/models/release-point';
import { ControlAssignmentAssociation } from 'src/app/shared/models/control-assignment-association';

export class ControlAssignment {
  id: number;
  description: string;
  controlPath: ControlPath;
  emissionsUnit: EmissionUnit;
  emissionsProcess: Process;
  releasePoint: ReleasePoint;
  association: ControlAssignmentAssociation;
}
