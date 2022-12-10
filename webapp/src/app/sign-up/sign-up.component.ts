import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';
import { User } from '../model/user';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  user: User;

  constructor(private router: Router, private userService: UserService) {
    this.user = new User();
  }

  onSubmit() {
    console.log("Attempting to Register User:", this.user);
    this.userService.register(this.user).subscribe(_result => {
      console.log("Response:", _result);
      if (_result.code === 0) {
        this.router.navigate(['/home'])
      } else {
        alert(_result.message);
      }
    });
  }
}
