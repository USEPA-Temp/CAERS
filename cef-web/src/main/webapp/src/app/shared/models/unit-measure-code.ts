import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class UnitMeasureCode implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  unitType: string;
  efNumerator: boolean;
  efDenominator: boolean;
  fuelUseUom: boolean;
  unitDesignCapacity: boolean;
  heatContentUom: boolean;
  legacy: boolean;
}
