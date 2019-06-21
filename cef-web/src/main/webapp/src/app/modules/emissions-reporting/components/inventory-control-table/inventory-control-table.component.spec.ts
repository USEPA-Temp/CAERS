import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryControlTableComponent } from './inventory-control-table.component';

describe('InventoryControlTableComponent', () => {
  let component: InventoryControlTableComponent;
  let fixture: ComponentFixture<InventoryControlTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryControlTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryControlTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
