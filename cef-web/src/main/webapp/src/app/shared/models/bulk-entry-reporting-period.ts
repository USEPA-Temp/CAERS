import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class BulkEntryReportingPeriod {

  emissionsUnitId: number;
  unitIdentifier: string;
  unitDescription: string;
  unitStatus: BaseCodeLookup;

  emissionsProcessId: number;
  emissionsProcessIdentifier: string;
  emissionsProcessDescription: string;
  operatingStatusCode: BaseCodeLookup;

  reportingPeriodId: number;
  reportingPeriodTypeCode: BaseCodeLookup;
  calculationParameterValue: number;
  calculationParameterUom: UnitMeasureCode;
  calculationMaterialCode: BaseCodeLookup;
  previousCalculationParameterValue: number;
  previousCalculationParameterUomCode: string;
}
