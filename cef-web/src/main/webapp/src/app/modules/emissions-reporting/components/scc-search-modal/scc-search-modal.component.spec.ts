import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SccSearchModalComponent } from './scc-search-modal.component';

describe('SccSearchModalComponent', () => {
  let component: SccSearchModalComponent;
  let fixture: ComponentFixture<SccSearchModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SccSearchModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SccSearchModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
