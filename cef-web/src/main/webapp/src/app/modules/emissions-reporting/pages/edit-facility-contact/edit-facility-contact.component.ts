import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FacilitySiteContact } from 'src/app/shared/models/facility-site-contact';
import { FacilitySite } from 'src/app/shared/models/facility-site';
import { FacilitySiteContactService } from 'src/app/core/services/facility-site-contact.service';
import { ReportStatus } from 'src/app/shared/enums/report-status';
import { SharedService } from 'src/app/core/services/shared.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseReportUrl } from 'src/app/shared/enums/base-report-url';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';

@Component({
  selector: 'app-edit-facility-contact',
  templateUrl: './edit-facility-contact.component.html',
  styleUrls: ['./edit-facility-contact.component.scss']
})
export class EditFacilityContactComponent implements OnInit {
  @Input() facilityContact: FacilitySiteContact;
  @Input() facilitySite: FacilitySite;
  @Input() editable = false;
  @Input() createMode = false;

  readOnlyMode = true;

  facilityUrl: string;

  contactForm = this.fb.group({
    type: [null, Validators.required],
    phone: ['', [
      Validators.required,
      Validators.pattern('[0-9]{10,15}')]],
    phoneExt: [''],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    email: ['', [
      Validators.required,
      Validators.email]],
    streetAddress: ['', Validators.required],
    city: ['', Validators.required],
    stateCode: ['', Validators.required],
    postalCode: ['', Validators.required],
    countryCode: ['', Validators.required],
    mailingStreetAddress: ['', Validators.required],
    mailingCity: ['', Validators.required],
    mailingStateCode: ['', Validators.required],
    mailingPostalCode: ['', Validators.required],
    mailingCountryCode: ['', Validators.required],
    county: ['', Validators.required]
  });

  facilityContactType: BaseCodeLookup[];

  constructor(
    private contactService: FacilitySiteContactService,
    private lookupService: LookupService,
    private sharedService: SharedService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder) { }

  ngOnInit() {

    this.lookupService.retrieveFacilityContactType()
    .subscribe(result => {
      this.facilityContactType = result;
    });

    this.route.data
    .subscribe(data => {
      this.facilitySite = data.facilitySite;

      this.createMode = data.create === 'true';

      this.readOnlyMode = ReportStatus.IN_PROGRESS !== data.facilitySite.emissionsReport.status;

      this.sharedService.emitChange(data.facilitySite);
    });

    this.route.paramMap
    .subscribe(params => {

      if (!this.createMode) {

      } else {
        this.contactForm.enable();
      }

      this.facilityUrl = `/facility/${params.get('facilityId')}/report/${params.get('reportId')}/${BaseReportUrl.FACILITY_INFO}`;
    });
  }

  onCancelEdit() {
    this.contactForm.enable();
    if (!this.createMode) {
      this.contactForm.reset(this.contactForm);
    }
  }

  onSubmit() {
    if (!this.contactForm.valid) {
      this.contactForm.markAllAsTouched();
    } else {

      const saveContact = new FacilitySiteContact();
      Object.assign(saveContact, this.contactForm.value);
      if (this.createMode) {

        saveContact.facilitySiteId = this.facilitySite.id;

        this.contactService.create(saveContact)
        .subscribe(result => {

          this.router.navigate([this.facilityUrl]);
        });
      } else {

        saveContact.id = this.facilityContact.id;

        this.contactService.update(saveContact)
        .subscribe(result => {

          Object.assign(this.facilityContact, result);
          this.router.navigate([this.facilityUrl]);
        });
      }
    }
  }
}
