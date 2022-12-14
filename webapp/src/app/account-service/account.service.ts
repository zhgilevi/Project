import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AccountService {
  public username: string;

  constructor(private cookieService: CookieService) {
    this.username = this.cookieService.get('username');
  }

  updateUsername() {
    this.username = this.cookieService.get('username');
  }

  isDefault(): boolean {
    for (const [key, value] of Object.entries(this.cookieService.getAll())) {
      if (value !== '') return false;
    }
    return true;
  }
}
