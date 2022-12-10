import { Component, OnInit } from '@angular/core';
import { CustomResponse } from '../model/custom.response';
import { User } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent {

  users: User[];

  constructor(private userService: UserService) {
    this.users = [];
  }

  ngOnInit() {
    this.userService.findAll().subscribe((data: CustomResponse) => {
      this.users = data.responseList;
    });
  }
}
