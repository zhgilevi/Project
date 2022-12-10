import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  user: User;

  constructor(private router: Router, private userService: UserService) {
    this.user = new User();
  }

  onSubmit() {
    console.log("Attempting to Login User:", this.user);
    this.userService.login(this.user).subscribe(_result => {
      console.log("Response:", _result);
      if (_result.code === 0) {
        this.router.navigate(['/home'])
      } else {
        alert(_result.message);
      }
    });
  }
}
