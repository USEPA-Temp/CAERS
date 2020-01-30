import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'stripPeriodEnding'
})
export class StripPeriodEndingPipe implements PipeTransform {

    transform(value: any, ...args: any[]): any {

        if (value) {

            return value.replace(/\.$/, "");
        }

        return null;
    }

}
