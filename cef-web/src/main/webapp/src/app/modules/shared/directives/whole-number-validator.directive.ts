import { Directive } from '@angular/core';
import { ValidatorFn, AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';

export function wholeNumberValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    if (!control.value) {
      return null;
    }
    const result = /^[0-9]+$/.test(control.value);
    return result ? null : {wholeNumber: {value: control.value}};
  };
}

@Directive({
  selector: '[appWholeNumberValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: WholeNumberValidatorDirective, multi: true}]
})
export class WholeNumberValidatorDirective implements Validator {

  validate(control: AbstractControl): {[key: string]: any} | null {
    return wholeNumberValidator()(control);
  }

}
