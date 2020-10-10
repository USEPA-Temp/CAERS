import { Injectable } from '@angular/core';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { Pollutant } from 'src/app/shared/models/pollutant';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {
  emissionsMaxSignificantFigures = 6;

  constructor() { }

  compareCode(c1: BaseCodeLookup, c2: BaseCodeLookup) {
    return c1 && c2 ? c1.code === c2.code : c1 === c2;
  }

  comparePollutantCode(c1: Pollutant, c2: Pollutant) {
    return c1 && c2 ? c1.pollutantCode === c2.pollutantCode : c1 === c2;
  }


  /**
   * Return the number of significant figures contained in the value of the currentValue argument
   */
  getSignificantFigures(currentValue: number): number {
    currentValue = Math.abs(parseInt(String(currentValue).replace('.', ''), 10));
    if (currentValue === 0) {
        return 0;
    }

    // Remove trailing zeroes
    while (currentValue !== 0 && currentValue % 10 === 0) {
        currentValue /= 10;
    }

    return Math.floor(Math.log(currentValue) / Math.log(10)) + 1;
  }
}
