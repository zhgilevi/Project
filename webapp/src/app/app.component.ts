import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {

  constructor(private cookieService: CookieService, private router: Router) {
    if (this.cookieService.getAll() == null) {
      this.cookieService.set('token', '');
      this.cookieService.set('username', '');
      this.cookieService.set('fname', '');
      this.cookieService.set('lname', '');
      this.cookieService.set('id', '');
      this.cookieService.set('regDate', '');
      this.cookieService.set('currentChatID', '');
      this.router.navigate(['/signin']);
    }
  }
}
