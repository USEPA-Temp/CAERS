import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { MasterFacilityRecord } from 'src/app/shared/models/master-facility-record';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { SortEvent } from 'src/app/shared/directives/sortable.directive';

@Component({
  selector: 'app-master-facility-table',
  templateUrl: './master-facility-table.component.html',
  styleUrls: ['./master-facility-table.component.scss']
})
export class MasterFacilityTableComponent extends BaseSortableTable implements OnInit, OnChanges {
  @Input() tableData: MasterFacilityRecord[];
  @Output() facilitySelected = new EventEmitter<MasterFacilityRecord>();

  filteredItems: MasterFacilityRecord[] = [];
  filter = new FormControl('');

  selectedFacility: MasterFacilityRecord;

  page = 1;
  pageSize = 10;

  constructor() {
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
    this.facilitySelected.emit(this.selectedFacility);
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
      return item.name.toLowerCase().includes(term)
          || item.agencyFacilityId.toLowerCase().includes(term);
    });
  }

}
