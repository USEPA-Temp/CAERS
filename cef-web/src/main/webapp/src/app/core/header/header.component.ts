import { UserContextService } from 'src/app/core/services/user-context.service';
import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import { ConfigPropertyService } from 'src/app/core/services/config-property.service';

@Component( {
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
} )
export class HeaderComponent implements OnInit {

    announcementText: string;
    announcementEnabled = false;

    constructor(public userContext: UserContextService, private propertyService: ConfigPropertyService) { }

    ngOnInit() {
        this.propertyService.retrieveAnnouncementEnabled()
        .subscribe(result => {
            this.announcementEnabled = result;

            if (this.announcementEnabled) {
                this.propertyService.retrieveAnnouncementText()
                .subscribe(text => {
                    this.announcementText = text.value;
                });
            }
        });

    }

    logout() {
        this.userContext.logoutUser();
    }
}
