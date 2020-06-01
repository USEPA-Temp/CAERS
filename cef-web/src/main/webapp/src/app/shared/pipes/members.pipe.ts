import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
   name: 'members'
})
export class MembersPipe implements PipeTransform {

   transform(value, args: string[]): any {

      let members = [];

      for (let enumMember in value) {

         members.push({key: enumMember, value: value[enumMember]});
      }

      return members;
   }
}
