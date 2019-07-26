import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';

export class Emission {
  id: number;
  reportingPeriodId: number;
  pollutant: Pollutant;
  totalEmissions: number;
  emissionsUomCode: BaseCodeLookup;
  emissionsFactor: number;
  emissionsFactorText: string;
  emissionsCalcMethodCode: BaseCodeLookup;
  comments: string;
  emissionsNumeratorUom: BaseCodeLookup;
  emissionsDenominatorUom: BaseCodeLookup;
  calculatedEmissionsTons: number;
}
