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

  table: {
    id: string;
    username: string;
    fname: string;
    lname: string;
    regDate: string;
    status: number;
  }[];
  searchString: string;

  constructor(private userService: UserService) {
    this.table = [];
    this.searchString = '';
  }

  ngOnInit() {
    // this.userService.findAll().subscribe((data: CustomResponse) => {
    //   this.users = data.responseList;
    // });
  }

  handleSearch() {
    if (this.searchString.length === 0) return;
    alert(this.searchString);
  }
}
