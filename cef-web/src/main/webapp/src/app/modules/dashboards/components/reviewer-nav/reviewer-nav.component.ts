import {Component, OnInit} from '@angular/core';
import {UserContextService} from "src/app/core/services/user-context.service";
import {User} from "src/app/shared/models/user";
import {ActivatedRoute} from "@angular/router";
import {map} from "rxjs/operators";

@Component({
   selector: 'app-reviewer-nav',
   templateUrl: './reviewer-nav.component.html',
   styleUrls: ['./reviewer-nav.component.scss']
})
export class ReviewerNavComponent implements OnInit {

   user: User;

   eisTransmissionActive: boolean;

   constructor(private userContext: UserContextService,
               private route: ActivatedRoute) { }

   ngOnInit() {

      this.route.url.pipe(map(segments => segments.join('')))
         .subscribe(result => {

            this.eisTransmissionActive = result.indexOf("eis") == 0;
         });

      this.userContext.getUser()
         .subscribe(result => {
            this.user = result;
         });
   }

}