import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { EfVariable } from 'src/app/shared/models/ef-variable';

export class EmissionFactor {
  id: number;
  emissionsNumeratorUom: BaseCodeLookup;
  emissionsDenominatorUom: BaseCodeLookup;
  calculationParameterTypeCode: BaseCodeLookup;
  calculationMaterialCode: BaseCodeLookup;
  controlMeasureCode: BaseCodeLookup;
  sccCode: number;
  pollutantCode: string;
  formulaIndicator: boolean;
  controlIndicator: boolean;
  emissionFactor: number;
  emissionFactorFormula: string;
  description: string;
  note: string;
  source: string;
  variables: EfVariable[];
}
