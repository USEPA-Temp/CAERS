import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EisTransactionsComponent } from './eis-transactions.component';

describe('EisTransactionsComponent', () => {
  let component: EisTransactionsComponent;
  let fixture: ComponentFixture<EisTransactionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EisTransactionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EisTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
