import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteContactService } from 'src/app/core/services/facility-site-contact.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SharedService } from 'src/app/core/services/shared.service';
import { FacilityNaicsCode } from 'src/app/shared/models/facility-naics-code';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteDialogComponent } from 'src/app/shared/components/delete-dialog/delete-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-facility-information',
  templateUrl: './facility-information.component.html',
  styleUrls: ['./facility-information.component.scss']
})
export class FacilityInformationComponent implements OnInit {
  facilitySite: FacilitySite;
  naicsCodes: FacilityNaicsCode[];

  constructor(
    private modalService: NgbModal,
    private contactService: FacilitySiteContactService,
    private sharedService: SharedService,
    private toastr: ToastrService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .subscribe((data: { facilitySite: FacilitySite }) => {

      this.facilitySite = data.facilitySite;
      this.sharedService.emitChange(data.facilitySite);

      this.contactService.retrieveForFacility(this.facilitySite.id)
      .subscribe(contacts => {
        this.facilitySite.contacts = contacts;
      });

      this.naicsCodes = this.facilitySite.facilityNAICS;
      this.naicsCodes.sort((a, b) => a.primaryFlag && !b.primaryFlag ? -1 : !a.primaryFlag && b.primaryFlag ? 1 : 0);
    });
  }

  openDeleteModal(contactFirstName: string, contactLastName: string, contactId: number, facilitySiteId: number) {
    if (this.facilitySite.contacts.length > 1) {
      const modalRef = this.modalService.open(DeleteDialogComponent, { size: 'sm' });
      const modalMessage = `Are you sure you want to delete ${contactFirstName} ${contactLastName} from this facility's contact information?`;
      modalRef.componentInstance.message = modalMessage;
      modalRef.componentInstance.continue.subscribe(() => {
        this.deleteContact(contactId, facilitySiteId);
      });
    } else {
      this.toastr.error('You must have at least one contact for Facility Contact Information.',
      'Cannot delete facility contact.',
      {positionClass: 'toast-top-right'});
    }
  }

  // delete an emission unit from the database
  deleteContact(contactId: number, facilitySiteId: number) {
    this.contactService.delete(contactId).subscribe(() => {

      // update the UI table with the current list of emission units
      this.contactService.retrieveForFacility(facilitySiteId)
        .subscribe(contactServiceResponse => {
          this.facilitySite.contacts = contactServiceResponse;
        });
    });
  }
}
