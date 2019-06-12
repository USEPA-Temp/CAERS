import { Pipe, PipeTransform } from '@angular/core';
import { SideNavItem } from 'src/app/shared/models/side-nav-item';

enum BaseUrls{
    EMISSIONS_UNIT = 'emissionUnit',
    EMISSIONS_PROCESS = 'process',
    RELEASE_POINT = 'release'
}

@Pipe( {
    name: 'emissionsUnitToSideNav'
} )
export class EmissionsUnitToSideNavPipe implements PipeTransform {
    
    BaseUrls : typeof BaseUrls = BaseUrls;

    transform( value: any ): SideNavItem {
        let children: SideNavItem[]=[];
        let sideNavItem: SideNavItem;
        if(value.emissionsProcesses){
            for(let emissionProcess of value.emissionsProcesses){
                let processChildren: SideNavItem[]=[];
                if(emissionProcess.releasePointAppts){
                    for(let releasePoint of emissionProcess.releasePointAppts){
                        processChildren.push(new SideNavItem( releasePoint.id, releasePoint.releasePointDescription, BaseUrls.RELEASE_POINT, []));
                    }
                }
                children.push(new SideNavItem( emissionProcess.id, emissionProcess.description, BaseUrls.EMISSIONS_PROCESS, processChildren))
            }
        }
        sideNavItem = new SideNavItem( value.id, value.description, BaseUrls.EMISSIONS_UNIT, children);
        return sideNavItem;
    }

}
