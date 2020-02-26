import { Process } from 'src/app/shared/models/process';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { UnitMeasureCode } from 'src/app/shared/models/unit-measure-code';

export class EmissionUnit {
  id: number;
  facilitySiteId: number;
  unitIdentifier: string;
  description: string;
  unitTypeCode: BaseCodeLookup;
  operatingStatusCode: BaseCodeLookup;
  programSystemCode: string;
  statusYear: number;
  unitOfMeasureCode: UnitMeasureCode;
  designCapacity: number;
  comments: string;
  processes: Process[];
}
