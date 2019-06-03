import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionInventoryComponent } from './emission-inventory.component';

describe('EmissionInventoryComponent', () => {
  let component: EmissionInventoryComponent;
  let fixture: ComponentFixture<EmissionInventoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmissionInventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmissionInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
