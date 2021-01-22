import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingUfaTableComponent } from './pending-ufa-table.component';

describe('PendingUfaTableComponent', () => {
  let component: PendingUfaTableComponent;
  let fixture: ComponentFixture<PendingUfaTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingUfaTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingUfaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
