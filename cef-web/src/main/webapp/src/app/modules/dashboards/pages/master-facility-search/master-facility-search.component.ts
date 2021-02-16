import { Component, OnInit } from '@angular/core';
import { MasterFacilityRecordService } from 'src/app/core/services/master-facility-record.service';
import { FormBuilder, Validators } from '@angular/forms';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { LookupService } from 'src/app/core/services/lookup.service';
import { FipsStateCode } from 'src/app/shared/models/fips-state-code';
import { BaseCodeLookup } from 'src/app/shared/models/base-code-lookup';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { UserFacilityAssociation } from 'src/app/shared/models/user-facility-association';

@Component({
  selector: 'app-master-facility-search',
  templateUrl: './master-facility-search.component.html',
  styleUrls: ['./master-facility-search.component.scss']
})
export class MasterFacilitySearchComponent implements OnInit {

  searchForm = this.fb.group({
    name: [''],
    eisProgramId: [''],
    city: [''],
    stateCode: [null],
    postalCode: [''],
    programSystemCode: [null, Validators.required],
    agencyFacilityId: ['']
  });

  searchResults: MasterFacilityRecord[];

  stateValues: FipsStateCode[];
  programSystemCodeValues: BaseCodeLookup[];

  myFacilityIds: number[];

  constructor(private mfrService: MasterFacilityRecordService,
              private ufaService: UserFacilityAssociationService,
              private lookupService: LookupService,
              private fb: FormBuilder) { }

  ngOnInit(): void {

    this.ufaService.getMyAssociations()
    .subscribe(result => {
      this.myFacilityIds = result.map(a => a.masterFacilityRecord.id);
    });

    this.mfrService.getProgramSystemCodes()
    .subscribe(result => {
      this.programSystemCodeValues = result.sort((a, b) => (a.code > b.code) ? 1 : -1);
    });

    this.lookupService.retrieveFipsStateCode()
    .subscribe(result => {
      this.stateValues = result;
    });
  }

  onSubmit() {

    if (!this.searchForm.valid) {
      this.searchForm.markAllAsTouched();
    } else {

      const criteria = new MasterFacilityRecord();
      Object.assign(criteria, this.searchForm.value);

      this.mfrService.search(criteria)
      .subscribe(result => {
        // remove facilities already associated with
        this.searchResults = result.filter(f => !this.myFacilityIds.includes(f.id));
      });
    }
  }

}
