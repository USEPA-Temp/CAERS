/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
import { Component, OnInit } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ExternalSccService } from 'src/app/core/services/external-scc.service';
import { FormControl, Validators } from '@angular/forms';
import { SccCode } from 'src/app/shared/models/scc-code';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-scc-search-modal',
  templateUrl: './scc-search-modal.component.html',
  styleUrls: ['./scc-search-modal.component.scss']
})
export class SccSearchModalComponent extends BaseSortableTable implements OnInit {
  tableData: SccCode[];
  searchControl = new FormControl('', Validators.required);
  hasSearched = false;
  searchError = false;

  sccSearchUrl = environment.sccSearchUrl;

  constructor(private sccService: ExternalSccService, public activeModal: NgbActiveModal) {
    super();
  }

  ngOnInit() {
  }

  onSearch() {
     if (!this.searchControl.valid) {
      this.searchControl.markAsTouched();
    } else {
      this.sccService.basicSearch(this.searchControl.value)
      .pipe(
        map((items: SccCode[]) => items.map(item => new SccCode(item)))
      )
      .subscribe(result => {

        this.tableData = result;
        this.hasSearched = true;
        this.searchError = false;
      }, error => {
        console.log(error);
        this.hasSearched = true;
        this.searchError = true;
      });
    }
  }

  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit(selectedCode: SccCode) {
    this.activeModal.close(selectedCode);
  }

}
