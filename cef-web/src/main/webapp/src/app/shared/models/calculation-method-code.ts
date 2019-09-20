import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class CalculationMethodCode implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  controlIndicator: boolean;
  epaEmissionFactor: boolean;
  totalDirectEntry: boolean;
}
