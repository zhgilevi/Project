import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { User } from '../model/user';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';

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

  constructor(
    private userService: UserService,
    private cookieService: CookieService,
    private router: Router
  ) {
    this.table = [];
    this.searchString = '';
  }

  ngOnInit() {
    console.log('Your Info:', this.cookieService.getAll());
    this.userService.getAllUsers().subscribe((res) => {
      console.log('All Users Response:', res);
      switch (res.code) {
        case 0:
          this.refillTable(res.data);
          break;
        default:
          this.router.navigate(['/signin']);
          break;
      }
    });
  }

  handleSearch() {
    if (this.searchString.length === 0) return;
    console.log('Searching For:', this.searchString);
    this.userService.searchUsers(this.searchString).subscribe((res) => {
      console.log('Search Response:', res);
      switch (res.code) {
        case 0:
          this.refillTable(res.data);
          break;
        default:
          this.router.navigate(['/signin']);
          break;
      }
    });
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
    console.log('Creating Chat with User:', id);
    this.userService.addChatWithUser(id).subscribe(res => {
      switch (res.code) {
        case 0:
          console.log('Chat Creation Response:', res);
          this.cookieService.set('currentChatID', String(res.chatId));
          this.router.navigate(['/home']);
          break;
      }
    })
  }
}
