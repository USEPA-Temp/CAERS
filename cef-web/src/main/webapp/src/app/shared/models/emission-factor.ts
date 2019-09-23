import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class EmissionFactor {
  id: number;
  emissionsNumeratorUom: BaseCodeLookup;
  emissionsDenominatorUom: BaseCodeLookup;
  calculationParameterTypeCode: BaseCodeLookup;
  calculationMaterialCode: BaseCodeLookup;
  sccCode: number;
  pollutantCode: string;
  formulaIndicator: boolean;
  controlIndicator: boolean;
  emissionFactor: number;
  emissionFactorFormula: string;
  description: string;
  note: string;
  source: string;
}
