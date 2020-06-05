import {Pipe, PipeTransform} from '@angular/core';
import {orderBy, isEmpty} from 'lodash';

@Pipe({
   name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

   transform(list: any[], column: string = '', order : string = ''): any[] {

      // no array
      if (isEmpty(list)) {

         return list;
      }

      // sort array of discrete
      if (isEmpty(column)) {
         if (order === 'desc') {

            return list.sort().reverse();

         } else {

            return list.sort()
         }
      }

      // sort array of objects
      return orderBy(list, [column], [order]);
   }

}
