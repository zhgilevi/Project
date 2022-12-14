import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { AccountService } from './account-service/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(
    private cookieService: CookieService,
    private router: Router,
    public accountService: AccountService

  ) {
    if (JSON.stringify(this.cookieService.getAll()) === JSON.stringify({})) {
      this.cookieService.set('token', '');
      this.cookieService.set('username', '');
      this.cookieService.set('fname', '');
      this.cookieService.set('lname', '');
      this.cookieService.set('id', '');
      this.cookieService.set('regDate', '');
      this.cookieService.set('currentChatID', '');
      console.log('Filling in with Default Values:', this.cookieService.getAll());
      this.router.navigate(['/signin']);
    }
    this.accountService.updateUsername();
  }
}
