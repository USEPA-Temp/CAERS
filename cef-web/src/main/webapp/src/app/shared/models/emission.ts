import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';
import { CalculationMethodCode } from 'src/app/shared/models/calculation-method-code';

export class Emission {
  id: number;
  reportingPeriodId: number;
  pollutant: Pollutant;
  totalEmissions: number;
  emissionsUomCode: BaseCodeLookup;
  emissionsFactor: number;
  emissionsFactorText: string;
  emissionsCalcMethodCode: CalculationMethodCode;
  comments: string;
  emissionsNumeratorUom: BaseCodeLookup;
  emissionsDenominatorUom: BaseCodeLookup;
  calculatedEmissionsTons: number;
}
