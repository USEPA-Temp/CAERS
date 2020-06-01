import {Pipe, PipeTransform} from '@angular/core';
import {orderBy} from 'lodash';

@Pipe({
   name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

   transform(value: any[], column: string = '', order = ''): any[] {

      // no array
      if (!value) {

         return value;
      }

      // array with only one item
      if (value.length <= 1) {

         return value;
      }

      // sort 1d array
      if (!column || column === '') {
         if (order === 'desc') {

            return value.sort().reverse();

         } else {

            return value.sort()
         }
      }

      return orderBy(value, [column], [order]);
   }

}
