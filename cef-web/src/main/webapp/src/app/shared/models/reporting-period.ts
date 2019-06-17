import { OperatingDetail } from './operating-detail';
import { Emission } from './emission';

export class ReportingPeriod {
  id: number;
  emissionsProcessId: number;
  reportingPeriodTypeCode: string;
  emissionsOperatingTypeCode: string;
  calculationParameterTypeCode: string;
  calculationParameterValue: number;
  calculationParameterUom: string;
  calculationMaterialCode: string;
  emissions: Emission[];
  operatingDetails: OperatingDetail[];
}