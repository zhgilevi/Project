import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent {
  user: User;

  constructor(
    private router: Router,
    private userService: UserService,
    private cookieService: CookieService
  ) {
    this.user = new User();
  }

  onSubmit() {
    console.log('Attempting to Login User:', this.user);
    this.userService.login(this.user).subscribe((res) => {
      console.log('Response:', res);
      switch (res.code) {
        case 0:
          this.cookieService.set('token', res.token);
          this.cookieService.set('username', res.userInfo.username);
          this.cookieService.set('fname', res.userInfo.fname);
          this.cookieService.set('lname', res.userInfo.lname);
          this.cookieService.set('id', String(res.userInfo.id));
          this.cookieService.set('regDate', res.userInfo.regDate);
          this.router.navigate(['/home']);
          break;
      }
    });
  }
}
