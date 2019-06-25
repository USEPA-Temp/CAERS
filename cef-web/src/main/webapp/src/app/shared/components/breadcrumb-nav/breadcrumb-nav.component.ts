import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { BreadCrumb } from "src/app/shared/models/breadcrumb";
import { PRIMARY_OUTLET } from "@angular/router";
import { Router } from "@angular/router";
import { filter } from "rxjs/operators";
import { NavigationEnd } from "@angular/router";
import { SharedService } from "src/app/core/services/shared.service";

@Component( {
    selector: 'app-breadcrumb-nav',
    templateUrl: './breadcrumb-nav.component.html',
    styleUrls: ['./breadcrumb-nav.component.scss']
} )
export class BreadcrumbNavComponent implements OnInit {

    public breadcrumbs: BreadCrumb[];
    year:number;

    constructor( private router: Router, private activeRoute: ActivatedRoute, private sharedService: SharedService ) { }
    
    ngOnInit() {
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
            //set breadcrumbs
            this.setNavBreadcrumbs();
        } );
    }

    private setNavBreadcrumbs(){
        
        let root: ActivatedRoute = this.activeRoute.root;
        
        let label='My Facilities';
        if(root.firstChild && root.firstChild.snapshot.data.title=='Submission Review Dashboard'){
            label='Submission Review Dashboard';
        }
        let breadcrumb: BreadCrumb = {
                label: label,
                url: ''
            };
        
        this.breadcrumbs = this.getBreadcrumbs( root );
        this.breadcrumbs = [breadcrumb, ...this.breadcrumbs]; 
    }
    
    private getBreadcrumbs( route: ActivatedRoute, url: string = "", breadcrumbs: BreadCrumb[] = [] ): BreadCrumb[] {
        const ROUTE_DATA_BREADCRUMB: string = "breadcrumb";
        //get the child routes
        let child = route;

        //iterate over each children
        while (true) {
            if (child.firstChild) {
                child = child.firstChild;
            }else{
                return breadcrumbs;
            }
            //verify primary route
            if ( child.outlet !== PRIMARY_OUTLET) {
                continue;
            }
            //get the route's URL segment
            let routeURL: string = child.snapshot.url.map( segment => segment.path ).join( "/" );

            //append route URL to URL
            url += `/${routeURL}`;

            //verify the custom data property "breadcrumb" is specified on the route
            if ( !child.snapshot.data.hasOwnProperty( ROUTE_DATA_BREADCRUMB ) ) {
                return this.getBreadcrumbs( child, url, breadcrumbs );
            }
            
            //add breadcrumb
            let label=child.snapshot.data[ROUTE_DATA_BREADCRUMB];
            let breadcrumb: BreadCrumb = {
                label: label.replace('&year', this.year),
                url: url
            };
            if(this.isNotAlreadyExist(breadcrumb, breadcrumbs)){
                breadcrumbs.push( breadcrumb );
            }
            //recursive
            return this.getBreadcrumbs( child, url, breadcrumbs );
        }
    }
    
    private isNotAlreadyExist(breacrum: BreadCrumb, breadcrumbs:BreadCrumb[]) : boolean {
        for (let bc of breadcrumbs) {
            if(breacrum.label==bc.label){
                return false;
            }
        }
        return true;
    }


}
