import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataBulkEntryComponent } from './data-bulk-entry.component';

describe('DataBulkEntryComponent', () => {
  let component: DataBulkEntryComponent;
  let fixture: ComponentFixture<DataBulkEntryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataBulkEntryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataBulkEntryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
