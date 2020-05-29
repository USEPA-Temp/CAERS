import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisDataListComponent } from './eis-data-list.component';

describe('EisDataListComponent', () => {
  let component: EisDataListComponent;
  let fixture: ComponentFixture<EisDataListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisDataListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisDataListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
