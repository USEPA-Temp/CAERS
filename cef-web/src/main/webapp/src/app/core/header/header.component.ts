/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
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
