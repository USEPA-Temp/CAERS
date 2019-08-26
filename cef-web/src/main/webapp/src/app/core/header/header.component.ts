import { UserContextService } from 'src/app/core/services/user-context.service';
import { Component, OnInit, Input } from '@angular/core';
import { ViewChild } from "@angular/core";
import { NgForm } from "@angular/forms";
import { ElementRef } from "@angular/core";
import { ChangeDetectorRef } from "@angular/core";

@Component( {
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
} )
export class HeaderComponent implements OnInit {

    @ViewChild('logoutForm', { static: true }) private logoutFormEl: ElementRef;

    ssoToken:string ='';
    returnUrl:string ='';
    logoutLink:string ='';
    
    constructor( public userContext: UserContextService, private cd: ChangeDetectorRef) { }

    ngOnInit() { }

    logout() {
        this.userContext.logoutUser().subscribe(logoutInfo =>{
            this.logoutLink=logoutInfo.split('?')[0];
            const params=logoutInfo.split('?')[1].split('&');
            for (let param of params) {
                const paramArray=param.split(/=(.+)/);
                if(paramArray[0]=='ssoToken'){
                    this.ssoToken=paramArray[1].trim();
                }else if(paramArray[0]=='returnUrl'){
                    this.returnUrl=paramArray[1].trim();
                }
            }
            this.cd.detectChanges();
            this.logoutFormEl.nativeElement.submit();
        });
    }
}
