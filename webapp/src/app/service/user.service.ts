import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { User } from '../model/user';
import { CustomResponse } from '../model/custom.response';
import { Observable } from 'rxjs';
import { LoginResponse } from '../model/login.response';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class UserService {
  private url: string;

  constructor(private http: HttpClient, private cookieServics: CookieService) {
    this.url = 'http://localhost:8080/api';
  }

  public findAll(): Observable<CustomResponse> {
    return this.http.get<CustomResponse>(`${this.url}/all`);
  }

  public register(user: User) {
    return this.http.post<{
      success: boolean;
      code: number;
    }>(`${this.url}/signup`, {
      username: user.username,
      fname: user.fname,
      lname: user.lname,
      password: user.password,
    });
  }

  public login(user: User) {
    return this.http.post<LoginResponse>(`${this.url}/signip`, {
      username: user.username,
      password: user.password,
    });
  }

  public validateToken(): Observable<{
    success: boolean;
    code: number;
  }> {
    return this.http.post<{
      success: boolean;
      code: number;
    }>(`${this.url}/...`, {
      token: this.cookieServics.get('token')
    });
  }
}
