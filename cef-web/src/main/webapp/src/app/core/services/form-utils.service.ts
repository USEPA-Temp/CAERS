import { Injectable } from '@angular/core';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  compareCode(c1: BaseCodeLookup, c2: BaseCodeLookup) {
    return c1 && c2 ? c1.code === c2.code : c1 === c2;
  }

  comparePollutantCode(c1: Pollutant, c2: Pollutant) {
    return c1 && c2 ? c1.pollutantCode === c2.pollutantCode : c1 === c2;
  }
}
