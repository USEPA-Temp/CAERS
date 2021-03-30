import { OperatingDetail } from './operating-detail';
import { Emission } from './emission';
import { BaseCodeLookup } from './base-code-lookup';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

export class ReportingPeriod {
  id: number;
  emissionsProcessId: number;
  reportingPeriodTypeCode: BaseCodeLookup;
  emissionsOperatingTypeCode: BaseCodeLookup;
  calculationParameterTypeCode: BaseCodeLookup;
  calculationParameterValue: string;
  calculationParameterUom: UnitMeasureCode;
  calculationMaterialCode: BaseCodeLookup;
  fuelUseValue: string;
  fuelUseUom: UnitMeasureCode;
  fuelUseMaterialCode: BaseCodeLookup;
  heatContentValue: string;
  heatContentUom: UnitMeasureCode;
  comments: string;
  emissions: Emission[];
  operatingDetails: OperatingDetail[];
}
