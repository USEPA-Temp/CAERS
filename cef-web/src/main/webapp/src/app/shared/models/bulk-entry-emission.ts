import { Pollutant } from 'src/app/shared/models/pollutant';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

export class BulkEntryEmission {
  id: number;
  pollutant: Pollutant;
  totalManualEntry: boolean;
  overallControlPercent: number;
  totalEmissions: number;
  emissionsUomCode: UnitMeasureCode;
  emissionsFactor: number;
  emissionsCalcMethodCode: CalculationMethodCode;
  emissionsNumeratorUom: UnitMeasureCode;
  emissionsDenominatorUom: UnitMeasureCode;

  previousTotalEmissions: number;
  previousEmissionsUomCode: string;

  calculationFailed: boolean;
  calculationFailureMessage: string;
}
