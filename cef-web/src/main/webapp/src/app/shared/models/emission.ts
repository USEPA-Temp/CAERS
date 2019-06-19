import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class Emission {
  id: number;
  reportingPeriodId: number;
  pollutantCode: string;
  pollutantName: string;
  pollutantCasId: string;
  totalEmissions: number;
  emissionsUomCode: BaseCodeLookup;
  emissionsFactor: number;
  emissionsFactorText: string;
  emissionsCalcMethodCode: BaseCodeLookup;
  comments: string;
}
