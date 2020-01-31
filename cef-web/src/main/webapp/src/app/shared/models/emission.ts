import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { EmissionFormulaVariable } from 'src/app/shared/models/emission-formula-variable';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

export class Emission {
  id: number;
  reportingPeriodId: number;
  pollutant: Pollutant;
  totalManualEntry: boolean;
  totalEmissions: number;
  emissionsUomCode: UnitMeasureCode;
  formulaIndicator: boolean;
  emissionsFactor: number;
  emissionsFactorFormula: string;
  emissionsFactorText: string;
  emissionsCalcMethodCode: CalculationMethodCode;
  comments: string;
  calculationComment: string;
  emissionsNumeratorUom: UnitMeasureCode;
  emissionsDenominatorUom: UnitMeasureCode;
  calculatedEmissionsTons: number;
  variables: EmissionFormulaVariable[];
}
