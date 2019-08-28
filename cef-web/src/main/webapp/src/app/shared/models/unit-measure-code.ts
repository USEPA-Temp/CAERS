import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class UnitMeasureCode implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  efNumerator: boolean;
  efDenominator: boolean;
}
