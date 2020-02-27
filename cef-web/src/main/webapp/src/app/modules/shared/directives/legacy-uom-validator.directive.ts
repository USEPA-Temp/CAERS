import { Directive } from '@angular/core';
import { ValidatorFn, AbstractControl, Validator, NG_VALIDATORS } from '@angular/forms';

export function legacyUomValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    if (!control.value) {
      return null;
    }
    return control.value.legacy ? {legacyUom: {value: control.value.code}} : null;
  };
}

@Directive({
  selector: '[appLegacyUomValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: LegacyUomValidatorDirective, multi: true}]
})
export class LegacyUomValidatorDirective implements Validator {

  validate(control: AbstractControl): {[key: string]: any} | null {
    return legacyUomValidator()(control);
  }

}
