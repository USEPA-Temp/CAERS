import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from 'src/app/app.component';
import { HeaderComponent} from 'src/app/core/header/header.component';
import {FooterComponent} from 'src/app/core/footer/footer.component';
import {BreadcrumbNavComponent} from 'src/app/shared/components/breadcrumb-nav/breadcrumb-nav.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        HeaderComponent,
        FooterComponent,
        BreadcrumbNavComponent
      ],
      imports: [
         RouterTestingModule,
         HttpClientModule
      ]
    }).compileComponents();
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render header', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('app-header')).toBeDefined();
  });
  
  it('should render footer', () => {
      const fixture = TestBed.createComponent(AppComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('app-footer')).toBeDefined();
  });
  
  it('should render breadcrumb', () => {
      const fixture = TestBed.createComponent(AppComponent);
      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      expect(compiled.querySelector('app-breadcrumb-nav')).toBeDefined();
  });  
  
});
