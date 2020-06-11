import {Pipe, PipeTransform} from '@angular/core';
import {orderBy} from 'lodash';

@Pipe({
   name: 'sortBy'
})
export class SortByPipe implements PipeTransform {

   transform(list: any[], column: string = '', order : string = ''): any[] {

      if (list) {

         // sort array of discrete
         if (column.length === 0) {

            if (order === 'desc') {

               return list.sort().reverse();

            } else {

               return list.sort()
            }
         }

         // sort array of objects
         return orderBy(list, [column], [order]);
      }

      return [];
   }

}
