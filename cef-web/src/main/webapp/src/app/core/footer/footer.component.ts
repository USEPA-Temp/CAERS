import {ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserContextService} from "src/app/core/services/user-context.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

    @ViewChild('cdxHandoffForm', { static: true }) private cdxHandoffFormEl: ElementRef;

    ssoToken:string ='';
    returnUrl:string ='';
    cdxHandoffLink:string ='';

    constructor(public userContext: UserContextService, private cd: ChangeDetectorRef) { }

    ngOnInit() {
    }

    handoffToCdx(whereTo) {
        this.userContext.handoffToCdx(whereTo).subscribe(cdxHandoffInfo =>{
            this.cdxHandoffLink = cdxHandoffInfo.split('?')[0];
            const params = cdxHandoffInfo.split('?')[1].split('&');
            for (let param of params) {
                const paramArray=param.split(/=(.+)/);
                if(paramArray[0]=='ssoToken'){
                    this.ssoToken=paramArray[1].trim();
                }else if(paramArray[0]=='returnUrl'){
                    this.returnUrl=paramArray[1].trim();
                }
            }
            this.cd.detectChanges();
            this.cdxHandoffFormEl.nativeElement.submit();
        });
    }
}
