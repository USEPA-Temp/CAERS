import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-master-facility-table',
  templateUrl: './master-facility-table.component.html',
  styleUrls: ['./master-facility-table.component.scss']
})
export class MasterFacilityTableComponent extends BaseSortableTable implements OnInit {
  @Input() tableData: MasterFacilityRecord[];
  @Output() facilitySelected = new EventEmitter<MasterFacilityRecord>();

  filteredItems: MasterFacilityRecord[] = [];
  filter = new FormControl('');

  selectedFacility: MasterFacilityRecord;

  matchFunction: (item: any, searchTerm: any) => boolean = this.matches;

  constructor() {
    super();

    this.filter.valueChanges.subscribe((text) => {
      this.controller.searchTerm = text;
    });
  }

  ngOnInit(): void {
    this.controller.paginate = true;
  }

  selectFacility(facility: MasterFacilityRecord) {

    this.selectedFacility = facility;
    this.facilitySelected.emit(this.selectedFacility);
  }

  onClearFilterClick() {
      this.filter.setValue('');
   }

  matches(item: MasterFacilityRecord, searchTerm: string): boolean {
    const term = searchTerm.toLowerCase();
    return item.name?.toLowerCase().includes(term)
        || item.agencyFacilityId?.toLowerCase().includes(term);
  }

}
