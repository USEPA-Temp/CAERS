import { OperatingDetail } from './operating-detail';
import { Emission } from './emission';
import { BaseCodeLookup } from './base-code-lookup';

export class ReportingPeriod {
  id: number;
  emissionsProcessId: number;
  reportingPeriodTypeCode: BaseCodeLookup;
  emissionsOperatingTypeCode: string;
  calculationParameterTypeCode: BaseCodeLookup;
  calculationParameterValue: number;
  calculationParameterUom: BaseCodeLookup;
  calculationMaterialCode: BaseCodeLookup;
  emissions: Emission[];
  operatingDetails: OperatingDetail[];
}
