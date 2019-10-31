import { UserContextService } from 'src/app/core/services/user-context.service';
import { Component, OnInit, Input } from '@angular/core';

@Component( {
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
} )
export class HeaderComponent implements OnInit {

    constructor( public userContext: UserContextService) { }

    ngOnInit() { }
}
