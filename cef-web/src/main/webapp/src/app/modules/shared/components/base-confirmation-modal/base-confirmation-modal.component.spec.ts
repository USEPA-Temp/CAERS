import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseConfirmationModalComponent } from './base-confirmation-modal.component';

describe('BaseConfirmationModalComponent', () => {
  let component: BaseConfirmationModalComponent;
  let fixture: ComponentFixture<BaseConfirmationModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaseConfirmationModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseConfirmationModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
