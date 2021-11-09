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
import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ThresholdStatus } from 'src/app/shared/enums/threshold-status';

@Component({
  selector: 'app-threshold-screening-gadnr-modal',
  templateUrl: './threshold-screening-gadnr-modal.component.html',
  styleUrls: ['./threshold-screening-gadnr-modal.component.scss']
})
export class ThresholdScreeningGadnrModalComponent implements OnInit {
  @Input() year: number;
  selectedStatus: string;
  belowThreshold: any;

  constructor(public activeModal: NgbActiveModal) { }

  ngOnInit(): void {
  }



  onClose() {
    this.activeModal.dismiss();
  }

  onSubmit() {
    if (this.selectedStatus === 'PS') {
        this.activeModal.close(ThresholdStatus.PERM_SHUTDOWN);
    } else if (this.selectedStatus === 'TS') {
        this.activeModal.close(ThresholdStatus.TEMP_SHUTDOWN);
    } else if (this.selectedStatus === 'OP' && this.belowThreshold == 'true') {
        this.activeModal.close(ThresholdStatus.OPERATING_BELOW_THRESHOLD);
    } else if (this.selectedStatus === 'OP' && this.belowThreshold == 'false') {
        this.activeModal.close(ThresholdStatus.OPERATING_ABOVE_THRESHOLD);
    }
  }
  
  canSubmit() {
    return this.selectedStatus === 'TS' || this.selectedStatus === 'PS' || (this.selectedStatus === 'OP' && this.belowThreshold);
  }

}
