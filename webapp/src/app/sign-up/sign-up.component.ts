import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css'],
})
export class SignUpComponent {
  user: User;

  constructor(
    private router: Router,
    private userService: UserService,
    private cookieService: CookieService
  ) {
    this.user = new User();
  }

  onSubmit() {
    console.log('Attempting to Register User:', this.user);
    this.userService.register(this.user).subscribe((res) => {
      console.log('Response:', res);
      switch (res.code) {
        case 0:
          this.router.navigate(['/signin']);
          break;
      }
    });
  }
}