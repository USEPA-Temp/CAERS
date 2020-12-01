import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { BulkEntryEmission } from 'src/app/shared/models/bulk-entry-emission';

export class BulkEntryEmissionHolder {

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

  emissions: BulkEntryEmission[];

}
