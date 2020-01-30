import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { EmissionFormulaVariableCode } from 'src/app/shared/models/emission-formula-variable-code';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

export class EmissionFactor {
  id: number;
  emissionsNumeratorUom: UnitMeasureCode;
  emissionsDenominatorUom: UnitMeasureCode;
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
  variables: EmissionFormulaVariableCode[];
}
