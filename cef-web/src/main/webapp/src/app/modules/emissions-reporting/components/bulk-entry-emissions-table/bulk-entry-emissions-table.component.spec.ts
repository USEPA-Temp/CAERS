import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BulkEntryEmissionsTableComponent } from './bulk-entry-emissions-table.component';

describe('BulkEntryEmissionsTableComponent', () => {
  let component: BulkEntryEmissionsTableComponent;
  let fixture: ComponentFixture<BulkEntryEmissionsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BulkEntryEmissionsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BulkEntryEmissionsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
