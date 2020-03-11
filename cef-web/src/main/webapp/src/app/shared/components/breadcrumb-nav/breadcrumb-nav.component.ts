import { Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BreadCrumb } from 'src/app/shared/models/breadcrumb';
import { PRIMARY_OUTLET } from '@angular/router';
import { Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { NavigationEnd } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { UserService } from 'src/app/core/services/user.service';
import { User } from "src/app/shared/models/user";
import { UserContextService } from "src/app/core/services/user-context.service";
import { Location } from '@angular/common';

@Component( {
    selector: 'app-breadcrumb-nav',
    templateUrl: './breadcrumb-nav.component.html',
    styleUrls: ['./breadcrumb-nav.component.scss']
} )
export class BreadcrumbNavComponent implements OnInit {

    public breadcrumbs: BreadCrumb[];
    year: number;
    baseLabel: string;
    user: User;
    url: string;

    constructor( private router: Router, private activeRoute: ActivatedRoute,
                 private sharedService: SharedService, private userContext: UserContextService,
                 private location: Location ) { }

    ngOnInit() {
        this.router.events.subscribe((val) => {
            this.url = this.location.path();
        });
        this.userContext.getUser()
        .subscribe(currentUser => {
            this.user=currentUser;
            if (currentUser.role === 'Reviewer') {
                this.baseLabel = 'Submission Review Dashboard';
            } else {
                this.baseLabel = 'My Facilities';
            }
            this.sharedService.changeEmitted$
            .subscribe( facilitySite => {
                if ( facilitySite != null ) {
                    this.year = facilitySite.emissionsReport.year;
                    this.setNavBreadcrumbs();
                } else {
                    this.year = null;
                }
            } );

            this.router.events.pipe( filter( event => event instanceof NavigationEnd ) ).subscribe( event => {
                // set breadcrumbs
                this.setNavBreadcrumbs();
            } );
        });
    }

    private setNavBreadcrumbs() {

        const root: ActivatedRoute = this.activeRoute.root;

        const label = this.baseLabel;

        /*
        if (root.firstChild && root.firstChild.snapshot.data.title === 'Submission Review Dashboard') {
            label = 'Submission Review Dashboard';
        }
        */
        const breadcrumb: BreadCrumb = {
                label,
                url: '#'
            };

        this.breadcrumbs = this.getBreadcrumbs( root );
        if(this.user.role=='Reviewer'){
            if(this.breadcrumbs.length>1){
                this.breadcrumbs.splice(0, 1);
            }
        }
        this.breadcrumbs = [breadcrumb, ...this.breadcrumbs];
    }

    private getBreadcrumbs( route: ActivatedRoute, url: string = '#', breadcrumbs: BreadCrumb[] = [] ): BreadCrumb[] {
        const ROUTE_DATA_BREADCRUMB = 'breadcrumb';
        // get the child routes
        let child = route;

        // iterate over each children
        while (true) {
            if (child.firstChild) {
                child = child.firstChild;
            } else {
                return breadcrumbs;
            }
            // verify primary route
            if ( child.outlet !== PRIMARY_OUTLET) {
                continue;
            }
            // get the route's URL segment
            const routeURL: string = child.snapshot.url.map( segment => segment.path ).join( '/' );

            // append route URL to URL
            url += `/${routeURL}`;

            // verify the custom data property "breadcrumb" is specified on the route
            if ( !child.snapshot.data.hasOwnProperty( ROUTE_DATA_BREADCRUMB ) ) {
                return this.getBreadcrumbs( child, url, breadcrumbs );
            }

            // add breadcrumb
            const label = child.snapshot.data[ROUTE_DATA_BREADCRUMB];
            const breadcrumb: BreadCrumb = {
                label: label.replace('&year', this.year),
                url
            };
            if (this.isNotAlreadyExist(breadcrumb, breadcrumbs)) {
                breadcrumbs.push( breadcrumb );
            }
            // recursive
            return this.getBreadcrumbs( child, url, breadcrumbs );
        }
    }

    private isNotAlreadyExist(breadcrumb: BreadCrumb, breadcrumbs: BreadCrumb[]): boolean {
        for (const bc of breadcrumbs) {
            if (breadcrumb.label === bc.label) {
                return false;
            }
        }
        return true;
    }

    goToHelpPage() {
        this.router.navigateByUrl('/helpPage');
    }

    goToMyFacilities() {
        this.router.navigateByUrl('/facility');
    }


}
