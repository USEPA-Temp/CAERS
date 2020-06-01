import { Directive } from '@angular/core';
import { AbstractControl, ValidatorFn, NG_VALIDATORS, Validator } from '@angular/forms';

export function legacyItemValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    if (!control.value) {
      return null;
    }
    return control.value.legacy ? {legacyItem: {value: control.value}} : null;
  };
}

@Directive({
  selector: '[appLegacyItemValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: LegacyItemValidatorDirective, multi: true}]
})
export class LegacyItemValidatorDirective implements Validator {

  validate(control: AbstractControl): {[key: string]: any} | null {
    return legacyItemValidator()(control);
  }

}
