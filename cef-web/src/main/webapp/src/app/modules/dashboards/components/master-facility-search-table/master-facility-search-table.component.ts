import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { FormControl } from '@angular/forms';
import { SortEvent } from 'src/app/shared/directives/sortable.directive';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserFacilityAssociationService } from 'src/app/core/services/user-facility-association.service';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-master-facility-search-table',
  templateUrl: './master-facility-search-table.component.html',
  styleUrls: ['./master-facility-search-table.component.scss']
})
export class MasterFacilitySearchTableComponent extends BaseSortableTable implements OnInit, OnChanges {
  @Input() tableData: MasterFacilityRecord[];

  filteredItems: MasterFacilityRecord[] = [];
  filter = new FormControl('');

  selectedFacility: MasterFacilityRecord;

  page = 1;
  pageSize = 10;

  constructor(private modalService: NgbModal,
              private userFacilityAssociationService: UserFacilityAssociationService,
              private toastr: ToastrService) {
    super();

    this.filter.valueChanges.subscribe((text) => {
      this.filteredItems = this.search(text);
    });
  }

  ngOnInit(): void {
  }

  ngOnChanges() {

    this.filteredItems = this.search(this.filter.value);
  }

  selectFacility(facility: MasterFacilityRecord) {

    this.selectedFacility = facility;
  }

  onClearFilterClick() {
      this.filter.setValue('');
   }

   sortAndSearch(sortEvent: SortEvent) {

    this.onSort(sortEvent);
    this.filteredItems = this.search(this.filter.value);
   }

  search(text: string): MasterFacilityRecord[] {
    return this.tableData.filter(item => {
      const term = text.toLowerCase();
      return item.name?.toLowerCase().includes(term)
          || item.agencyFacilityId?.toLowerCase().includes(term)
          || `${item.streetAddress}, ${item.city}, ${item?.stateCode?.uspsCode} ${item.postalCode}`.toLowerCase().includes(term);
    });
  }

  openRequestAccessModal() {
    const modalMessage = `Are you sure you want to request access to ${this.selectedFacility.name}?`;
    const modalRef = this.modalService.open(ConfirmationDialogComponent);
    modalRef.componentInstance.message = modalMessage;
    modalRef.componentInstance.continue.subscribe(() => {
        this.requestAccess(this.selectedFacility);
    });
  }

  requestAccess(facility: MasterFacilityRecord) {
    this.userFacilityAssociationService.createAssociationRequest(facility)
    .subscribe(() => {
      this.toastr.success('', `Access requested for ${facility.name}`);
      this.tableData.splice(this.tableData.indexOf(facility), 1);
      this.filteredItems = this.search(this.filter.value);
      this.selectedFacility = null;
    });

  }

}
