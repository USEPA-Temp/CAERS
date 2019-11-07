import { UserContextService } from 'src/app/core/services/user-context.service';
import {Component, OnInit, Input, ViewChild, ElementRef} from '@angular/core';
import {HttpXsrfTokenExtractor} from '@angular/common/http';
import {FormControl} from "@angular/forms";

@Component( {
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
} )
export class HeaderComponent implements OnInit {

    @ViewChild('logoutForm', { static: true }) private logoutFormEl: ElementRef;

    xsrfToken = new FormControl("not-valid");

    constructor( public userContext: UserContextService,
                 public xsrfCookieExtractor: HttpXsrfTokenExtractor) {
    }

    ngOnInit() { }

    logout() {

        this.xsrfToken.setValue(this.xsrfCookieExtractor.getToken());
        //console.log("token", this.xsrfToken);

        this.logoutFormEl.nativeElement.submit();
    }
}
