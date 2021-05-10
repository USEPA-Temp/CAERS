import { BaseCodeLookup } from './base-code-lookup';
import { UnitMeasureCode } from './unit-measure-code';

export class CalculationMaterialCode implements BaseCodeLookup {
  code: string;
  description: string;
  shortName: string;
  fuelUseMaterial: boolean;
  defaultHeatContentRatio: number;
  heatContentRatioNumeratorUom: UnitMeasureCode;
  heatContentRatioDenominatorUom: UnitMeasureCode;
}
