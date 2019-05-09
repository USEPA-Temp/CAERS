import { Process } from './process';
import { UnitTypeCode } from './unit-type-code';

export class EmissionUnit {
  id: number;
  unitId: string;
  description: string;
  unitType: UnitTypeCode;
  processes: Process[];
}