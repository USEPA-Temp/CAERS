import { Component, OnInit } from '@angular/core';
import { BaseSortableTable } from 'src/app/shared/components/sortable-table/base-sortable-table';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ExternalSccService } from 'src/app/core/services/external-scc.service';
import { FormControl, Validators } from '@angular/forms';
import { SccCode } from 'src/app/shared/models/scc-code';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-scc-search-modal',
  templateUrl: './scc-search-modal.component.html',
  styleUrls: ['./scc-search-modal.component.scss']
})
export class SccSearchModalComponent extends BaseSortableTable implements OnInit {
  tableData: SccCode[];
  searchControl = new FormControl('', Validators.required);
  hasSearched = false;

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
