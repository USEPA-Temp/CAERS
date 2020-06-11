import { ControlAssignment } from 'src/app/shared/models/control-assignment';
import { ControlPollutant } from 'src/app/shared/models/control-pollutant';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { InventoryYearCodeLookup } from 'src/app/shared/models/inventory-year-code-lookup';

export class Control {
  id: number;
  facilitySiteId: number;
  operatingStatusCode: BaseCodeLookup;
  identifier: string;
  description: string;
  percentCapture: number;
  percentControl: number;
  assignments: ControlAssignment[];
  pollutants: ControlPollutant[];
  comments: string;
  controlMeasureCode: InventoryYearCodeLookup;
}
