import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SegurancaService } from './modules/seguranca/seguranca.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  constructor(
    private router: Router,
    private segurancaService: SegurancaService
  ) { }

  ngOnInit() {
    (
      !this.shouldDisplayNavBar
        && this.segurancaService.isTokenExpired
    ) && (() => {
      this.router.navigateByUrl('login');
    })()
  }

  get shouldDisplayNavBar(): boolean {
    return !(this.atLoginPage || this.atSignUpPage);
  }

  get atLoginPage() {
    return this.router.url === '/login';
  }

  get atSignUpPage() {
    return this.router.url === '/sign-up';
  }

}
