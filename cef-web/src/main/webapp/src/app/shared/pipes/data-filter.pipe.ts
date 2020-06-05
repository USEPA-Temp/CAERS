import {Pipe, PipeTransform} from '@angular/core';

import {split, filterBy} from "lodash";

@Pipe({
   name: 'dataFilter'
})
export class DataFilterPipe implements PipeTransform {

   transform(list: any[], value: string, csvKeys: string = ''): any[] {

      let result = list;

      if (list && csvKeys && value) {

         let keys = csvKeys
            .replace(/\\w/g, '')
            .replace(/,,/g, ',')
            .split(",");

         if (keys) {

            result = list.filter(item => {

               let match = false;
               for (let i = 0; i < keys.length; ++i) {

                  let str = item[keys[i]];

                  if (str && str.indexOf(value) > -1) {

                     match = true;
                     break;
                  }
               }

               return match;
            });
         }
      }

      return result;
   }
}
