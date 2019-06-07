import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phoneNumber'
})
export class PhoneNumberPipe implements PipeTransform {

  transform(value: string): string {

    if (!value.match(/[0-9]{10}/)) {
      return value;
    }
    
    return `${value.slice(0,3)}-${value.slice(3,6)}-${value.slice(6)}`;
  }

}
