import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../model/user';
import { CustomResponse } from '../model/custom.response';
import { Observable } from 'rxjs';

@Injectable()
export class UserService {

  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/api';
  }

  public findAll(): Observable<CustomResponse> {
    return this.http.get<CustomResponse>(`${this.url}/all`);
  }

  public register(user: User) {
    return this.http.post<CustomResponse>(`${this.url}/registr`, {
      username: user.username,
      fname: user.fname,
      lname: user.lname,
      password: user.password
    });
  }

  public login(user: User) {
    return this.http.post<CustomResponse>(`${this.url}/signup`, {
      username: user.username,
      password: user.password
    });
  }
}
