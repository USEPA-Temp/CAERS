import { Pipe, PipeTransform } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';

@Pipe( {
    name: 'emissionsUnitToSideNav'
} )
export class EmissionsUnitToSideNavPipe implements PipeTransform {

    transform( value: any ): SideNavItem {
        let children: SideNavItem[]=[];
        let sideNavItem: SideNavItem;
        if(value.emissionsProcesses){
            for(let emissionProcess of value.emissionsProcesses){
                let processChildren: SideNavItem[]=[];
                children.push(new SideNavItem( emissionProcess.id, emissionProcess.emissionsProcessIdentifier, BaseReportUrl.EMISSIONS_PROCESS, processChildren));
            }
        }
        sideNavItem = new SideNavItem( value.id, value.unitIdentifier, BaseReportUrl.EMISSIONS_UNIT, children);
        return sideNavItem;
    }

}
