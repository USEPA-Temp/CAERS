import { Directive, Input } from '@angular/core';
import { AbstractControl, ValidatorFn, NG_VALIDATORS, Validator } from '@angular/forms';

export function legacyItemValidator(year: number, type: string, displayField: string): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    if (!control.value || !control.value.lastInventoryYear) {
      return null;
    }
    return control.value.lastInventoryYear < year ? {legacyItem: {value: control.value, type, displayField}} : null;
  };
}

@Directive({
  selector: '[appLegacyItemValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: LegacyItemValidatorDirective, multi: true}]
})
export class LegacyItemValidatorDirective implements Validator {
  @Input() year: number;
  @Input() type: string;
  @Input() displayField: string;

  validate(control: AbstractControl): {[key: string]: any} | null {
    return legacyItemValidator(this.year, this.type, this.displayField)(control);
  }

}
