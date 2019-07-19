import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

export class ReportSummary {
  id: number;
  casId: string;
  pollutantName: string;
  pollutantType: string;
  fugitiveTotal: number;
  stackTotal: number;
  uom: string;
  emissionsTonsTotal: number;
  previousYearTotal: number;
  reportYear: number;
  facilitySiteId: number;
}
