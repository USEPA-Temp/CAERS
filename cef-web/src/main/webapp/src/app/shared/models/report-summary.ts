export class ReportSummary {
  id: number;
  casId: string;
  pollutantCode: string;
  pollutantName: string;
  pollutantType: string;
  fugitiveTotal: number;
  stackTotal: number;
  fugitiveTonsTotal: number;
  stackTonsTotal: number;
  uom: string;
  emissionsTonsTotal: number;
  emissionsTotal: number;
  previousYearTotal: number;
  previousYearTonsTotal: number;
  reportYear: number;
  facilitySiteId: number;
  previousYear: number;
}
