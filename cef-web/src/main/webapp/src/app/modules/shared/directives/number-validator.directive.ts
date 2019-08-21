import { Directive } from '@angular/core';
import { ValidatorFn, NG_VALIDATORS, Validator, AbstractControl } from '@angular/forms';

export function numberValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    if (!control.value) {
      return null;
    }
    const result = /^[0-9]*\.?[0-9]+$/.test(control.value);
    return result ? null : {number: {value: control.value}};
  };
}

@Directive({
  selector: '[appNumberValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: NumberValidatorDirective, multi: true}]
})
export class NumberValidatorDirective implements Validator {

  validate(control: AbstractControl): {[key: string]: any} | null {
    return numberValidator()(control);
  }

}
