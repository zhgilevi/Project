import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent {
  table: {
    id: string;
    username: string;
    fname: string;
    lname: string;
    regDate: string;
  }[];
  searchString: string;
  permission: boolean;

  constructor(
    private userService: UserService,
    private cookieService: CookieService
  ) {
    this.table = [];
    this.searchString = '';
    this.permission = false;
  }

  ngOnInit() {
    this.permission = this.userService.validateToken();
    if (this.permission) {
      this.userService.getAllUsers().subscribe((res) => {
        console.log('All Users Response:', res);
        switch (res.code) {
          case 0:
            this.refillTable(res.data);
        }
      });
    }
  }

  handleSearch() {
    this.permission = this.userService.validateToken();
    if (this.permission) {
      if (this.searchString.length === 0) return;
      this.userService.searchUsers(this.searchString).subscribe((res) => {
        switch (res.code) {
          case 0:
            this.refillTable(res.data);
        }
      });
    }
  }

  refillTable(users: User[]) {
    console.log('Refill Table With:', users);
    this.table = [];
    for (const user of users) {
      if (user.username != this.cookieService.get('username')) {
        console.log('Pushing:', user);
        this.table.push(user);
      }
    }
    console.log('Table:', this.table);
  }

  handleEnterChat(id: string) {
    this.permission = this.userService.validateToken();
    if (this.permission) {
      // do something
    }
  }
}
