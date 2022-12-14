import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AccountService } from '../account/account.service';
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
  }
  constructor(
    private userService: UserService,
    private accountService: AccountService,
    private coockieService: CookieService,
    private router: Router
  ) {
    this.user = {
      username: '',
      fname: '',
      lname: '',
      password: ''
    }
    this.placeholder = {
      username: this.coockieService.get('username'),
      fname: this.coockieService.get('fname'),
      lname: this.coockieService.get('lname'),
      password: 'Enter new Password'
    }
  }

  ngOnInit() {
    if (this.accountService.isDefault()) {
      this.router.navigate(['/signin']);
    }
  }

  handleChangeInfo() {
    // do something
  }

}
