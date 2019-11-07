export interface BaseCodeLookup {
  code: string;
  description: string;
  // currently shortName is only being used for the reporting period type code and emissions operating type code
  shortName: string;
}
