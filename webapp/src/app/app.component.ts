import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title: string;

  constructor(private cookieService: CookieService) {
    this.title = 'Our Awsome Messenger';
    this.cookieService.set('token', '');
    this.cookieService.set('username', '');
    this.cookieService.set('fname', '');
    this.cookieService.set('lname', '');
    this.cookieService.set('id', '');
    this.cookieService.set('regDate', '');
    this.cookieService.set('currentChatID', '');
  }
}
