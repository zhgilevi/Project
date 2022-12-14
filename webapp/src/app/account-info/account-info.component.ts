import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AccountService } from '../account-service/account.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
})
export class AccountInfoComponent {
  user: {
    username: string;
    fname: string;
    lname: string;
    password: string;
  };

  placeholder: {
    username: string;
    fname: string;
    lname: string;
    password: string;
  };
  constructor(
    private userService: UserService,
    private accountService: AccountService,
    private cookieService: CookieService,
    private router: Router
  ) {
    this.user = {
      username: '',
      fname: '',
      lname: '',
      password: '',
    };
    this.placeholder = {
      username: this.cookieService.get('username'),
      fname: this.cookieService.get('fname'),
      lname: this.cookieService.get('lname'),
      password: 'Enter new Password',
    };
  }

  ngOnInit() {
    if (this.accountService.isDefault()) {
      this.router.navigate(['/signin']);
    }
  }

  handleChangeInfo() {
    let flag = false;
    for (const [key, val] of Object.entries(this.user)) {
      if (val !== '') {
        flag = true;
      }
    }
    if (!flag) return;
    this.userService.updateUserInfo(this.user).subscribe((res) => {
      switch (res.code) {
        case 0:
          if (res.data) {
            this.cookieService.set('username', res.data.username);
            this.cookieService.set('fname', res.data.fName);
            this.cookieService.set('lname', res.data.lName);
            this.cookieService.set('id', String(res.data.id));
            this.cookieService.set('regDate', res.data.regDate);
            this.accountService.updateUsername();
          }
          break;
      }
      this.placeholder = {
        username: this.cookieService.get('username'),
        fname: this.cookieService.get('fname'),
        lname: this.cookieService.get('lname'),
        password: 'Enter new Password',
      };
    });
    this.user.username = '';
    this.user.fname = '';
    this.user.lname = '';
    this.user.password = '';
  }
}
